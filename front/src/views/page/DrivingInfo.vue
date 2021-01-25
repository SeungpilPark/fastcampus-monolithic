<template>
  <v-container
    v-if="driving"
    id="DrivingInfo"
    tag="section"
    fluid
    class="py-0"
  >
    <fc-title :text="'운행 상세'" />
    <v-card outlined class="mt-0">
      <v-card-text class="pa-0">
        <v-row no-gutters class="mt-6 mb-3 pl-7 pb-0 pr-7">
          <v-col cols="12" class="d-flex">
            <FcButtonCancel
              class="mr-0 ml-2"
              @onClick="moveToList"
              :title="'목록으로'"
            />
            <v-spacer />
            <fc-search-reset-btn
              @onClickReset="load"
              :title="'새로고침'"
            />
            <FcButtonCancel
              class="mr-0 ml-2"
              @onClick="confirmCancel"
              :title="'운행중단'"
            />
          </v-col>
        </v-row>
        <v-row no-gutters class="mb-0 pl-7 pb-3 pr-7">
          <v-col cols="12">
            <v-simple-table class="fc fill-width">
              <thead>
                <tr>
                  <th class="text-center">운행ID</th>
                  <th class="text-center">운행상태</th>
                  <th class="text-center">탑승여부</th>
                  <th class="text-center">라이선스</th>
                  <th class="text-center">배차요청회차</th>
                  <th class="text-center">배차요청차량수</th>
                  <th class="text-center">등록일</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="py-0 text-center">{{ driving.id }}</td>
                  <td class="py-0 text-center">{{ driving.status }}</td>
                  <td class="py-0 text-center">{{ driving.boardingYn ? '탑승' : '대기' }}</td>
                  <td class="py-0 text-center">{{ vehicle ? vehicle.license : '' }}</td>
                  <td class="py-0 text-center">{{ driving.dispatchAttempts }}</td>
                  <td class="py-0 text-center">{{ driving.dispatchVehicleCount }}</td>
                  <td class="py-0 text-center">{{ $moment(driving.createDate).format('YYYY-MM-DD HH:mm:ss') }}</td>
                </tr>
              </tbody>
            </v-simple-table>
          </v-col>
          <v-col cols="12">
            <v-col cols="12" class="d-flex justify-center">
              <naver-maps
                :width="1000"
                :height="600"
                :mapOptions="mapOptions"
                :initLayers="[]"
                @load="onMapLoaded">
                <naver-marker
                  v-if="map"
                  :lng="Number(driving.drivingRoute.boardingCoordinates.split(',')[0])"
                  :lat="Number(driving.drivingRoute.boardingCoordinates.split(',')[1])"
                  @load="onBoardingMarkerLoaded"
                />
                <naver-marker
                  v-if="map"
                  :lng="Number(driving.drivingRoute.destinationCoordinates.split(',')[0])"
                  :lat="Number(driving.drivingRoute.destinationCoordinates.split(',')[1])"
                  @load="onDestinationMarkerLoaded"
                />
                <div v-if="map && driving.status === '배차중'">
                  <naver-marker
                    v-for="(item) in driving.drivingRequests"
                    :key="item.id"
                    :lng="Number(item.coordinates.split(',')[0])"
                    :lat="Number(item.coordinates.split(',')[1])"
                    @load="onDrivingRequestMarkerLoaded"
                  />
                </div>
                <naver-polyline
                  v-if="map && paths.length"
                  :path="paths"
                />
                <naver-marker
                  v-if="map && vehicle && driving.status === '운행중'"
                  :lng="Number(vehicle.vehicleCoordinates.coordinates.split(',')[0])"
                  :lat="Number(vehicle.vehicleCoordinates.coordinates.split(',')[1])"
                  @load="onVehicleMarkerLoaded"
                />
              </naver-maps>
            </v-col>
          </v-col>
          <v-col cols="12">
            배차요청 차량 리스트
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
  import drivingAPI from '@/api/driving'
  import vehicleAPI from '@/api/vehicle'
  import { pageable } from '@/mixins/pageable'
  import { errorHandling } from '@/mixins/errorHandling'
  import { formValidator } from '@/mixins/formValidator'

  export default {
    name: 'DrivingInfo',
    components: {},
    mixins: [pageable, errorHandling, formValidator],
    props: {
      id: {
        type: [String],
        default: '0'
      }
    },
    data () {
      return {
        driving: null,
        vehicle: null,
        mapOptions: {
          lng: 127.02134,
          lat: 37.51097,
          zoom: 15
        },
        map: null,
        interval: null
      }
    },
    computed: {
      paths () {
        if (!this.driving || this.driving.drivingRoute.paths.length < 1) {
          return []
        }
        const paths = JSON.parse(this.driving.drivingRoute.paths)
        return paths.map(path => {
          return {
            lng: path[0],
            lat: path[1],
          }
        })
      }
    },
    watch: {
      $route: {
        handler () {
          if (!this._inactive) {
            this.load()
          }
        },
        deep: true
      },
    },
    mounted () {
      this.load()
      this.interval = setInterval(this.loadVehicle, 3000)
    },
    beforeDestroy () {
      clearInterval(this.interval)
    },
    methods: {
      onBoardingMarkerLoaded (vue) {
        vue.marker.setIcon(
          {
            content: '<div class="title-maker boarding">승객</div>',
            size: new window.naver.maps.Size(38, 58),
            anchor: new window.naver.maps.Point(0, 0),
          }
        )
      },
      onDestinationMarkerLoaded (vue) {
        vue.marker.setIcon(
          {
            content: '<div class="title-maker destination">도착</div>',
            size: new window.naver.maps.Size(38, 58),
            anchor: new window.naver.maps.Point(0, 0),
          }
        )
      },
      onVehicleMarkerLoaded (vue) {
        vue.marker.setIcon(
          {
            content: '<div class="title-maker request">차량</div>',
            size: new window.naver.maps.Size(38, 58),
            anchor: new window.naver.maps.Point(0, 0),
          }
        )
      },
      onDrivingRequestMarkerLoaded (vue) {
        const me = this
        const drivingRequest = this.driving.drivingRequests.filter(item => {
          return item.coordinates === vue.lng + ',' + vue.lat
        })[0]
        vue.marker.setIcon(
          {
            content: '<div class="title-maker request">요청</div>',
            size: new window.naver.maps.Size(38, 58),
            anchor: new window.naver.maps.Point(0, 0),
          }
        )
        window.naver.maps.Event.addListener(vue.marker, 'click', () => {
          me.$confirm('배차', '해당 차량에 배차하시겠습니까?',
            (obj) => {
              me.dispatchAcceptance(drivingRequest.vehicleId)
            },
            (obj) => {
            }
          )
        })
      },
      onMapLoaded (vue) {
        const me = this
        this.map = vue.map
        this.map.addListener('click', function (e) {
          me.selectedCoordinates = e.coord.x + ',' + e.coord.y
        })
      },
      async load () {
        try {
          this.driving = await drivingAPI.get(this.id)
          this.mapOptions.lng = this.driving.drivingRoute.boardingCoordinates.split(',')[0]
          this.mapOptions.lat = this.driving.drivingRoute.boardingCoordinates.split(',')[1]
        } catch (error) {
          this.errorHandle(error)
        }
      },
      async loadVehicle () {
        try {
          if (!this.driving || this.driving.status !== '운행중') {
            this.vehicle = null
            return
          }
          this.vehicle = await vehicleAPI.get(this.driving.vehicleId)
        } catch (error) {
          this.errorHandle(error)
        }
      },
      async dispatchAcceptance (vehicleId) {
        try {
          await drivingAPI.dispatchAcceptance({
            vehicleId: vehicleId,
            drivingId: this.id
          })
          this.$info('차량이 배차되었습니다.')
          this.load()
        } catch (error) {
          this.errorHandle(error)
        }
      },
      confirmCancel () {
        const me = this
        this.$confirm('', '운행을 중단하시겠습니까?',
          (obj) => {
            drivingAPI.cancel(me.id)
            .then(() => {
                me.moveToList()
              }
            ).catch(error => {
              me.errorHandle(error)
            })
          },
          (obj) => {
            me.close()
          },
        )
      },
      moveToList () {
        this.$router.push({ name: 'drivingList' })
      },
    },
  }
</script>
<style lang="sass">
  .title-maker
    border-radius: 20px
    border: 1px solid black
    color: white
    padding: 8px

    &.boarding
      background: #0f49b4

    &.destination
      background: #0f49b4

    &.request
      background: #f8891b

</style>
