<template>
  <v-dialog
    v-model="visible"
    persistent
    max-width="800"
  >
    <v-card>
      <v-card-title class="headline">
        차량 등록
        <v-spacer />
        <v-icon
          aria-label="Close"
          @click="visible = false"
        >
          mdi-close
        </v-icon>
      </v-card-title>
      <v-card-text>
        <v-form
          ref="form"
          v-model="valid"
          :lazy-validation="true"
        >
          <v-text-field
            v-model="vehicle.license"
            :rules="[rules.required, v => rules.maxLength(v, 20), v => rules.minLength(v, 5)]"
            dense
            outlined
            label="라이선스"
          />
          <v-text-field
            v-model="vehicle.coordinates"
            :rules="[rules.required]"
            dense
            outlined
            label="좌표"
          />
        </v-form>

        <v-row class="mt-3">
          <v-col cols="12" class="d-flex justify-center">
            <naver-maps
              :width="700"
              :height="400"
              :mapOptions="mapOptions"
              :initLayers="[]"
              @load="onMapLoaded">
              <naver-marker
                v-if="vehicle.coordinates"
                :lng="vehicleLng"
                :lat="vehicleLat" />
            </naver-maps>
          </v-col>
        </v-row>

        <v-row class="mt-3">
          <v-col
            cols="12"
            class="d-flex justify-end pt-0 pb-0">
            <fc-button-add
              :title="'저장'"
              class="mr-3"
              @onClick="update"
            />
            <fc-button-cancel
              @onClick="visible = false"
            />
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>
  import vehicleAPI from '@/api/vehicle'
  import { formValidator } from '@/mixins/formValidator'
  import { errorHandling } from '@/mixins/errorHandling'

  export default {
    name: 'VehicleDialog',
    components: {},
    mixins: [errorHandling, formValidator],
    props: {},
    data () {
      return {
        visible: false,
        valid: false,
        vehicle: this.emptyVehicle(),
        id: null,
        mapOptions: {
          lng: 127.02134,
          lat: 37.51097,
          zoom: 15
        },
        map: null,
      }
    },
    computed: {
      isUpdate () {
        return this.id
      },
      vehicleLng () {
        return Number(this.vehicle.coordinates.split(',')[0])
      },
      vehicleLat () {
        return Number(this.vehicle.coordinates.split(',')[1])
      }
    },
    methods: {
      emptyVehicle () {
        return {
          license: null,
          coordinates: null,
        }
      },
      onMapLoaded (map) {
        const me = this
        this.map = map
        this.map.map.addListener('click', function (e) {
          me.vehicle.coordinates = e.coord.x + ',' + e.coord.y
        })
      },
      open (id) {
        this.id = id
        this.load()
        this.visible = true
      },
      async load () {
        if (!this.isUpdate) {
          this.vehicle = this.emptyVehicle()
          this.$nextTick(() => {
            this.resetFormValidation()
          })
        } else {
          try {
            const vehicle = await vehicleAPI.get(this.id)
            this.vehicle = {
              license: vehicle.license,
              coordinates: vehicle.vehicleCoordinates.coordinates
            }
            if (this.map) {
              this.map.setCenter({
                lng: this.vehicleLng,
                lat: this.vehicleLat
              })
            } else {
              this.mapOptions.lng = this.vehicleLng
              this.mapOptions.lat = this.vehicleLat
            }
            await this.validateForm()
          } catch (error) {
            this.errorHandle(error)
          }
        }
      },
      async update () {
        try {
          await this.validateForm()
          if (!this.valid) {
            return
          }
          if (this.isUpdate) {
            await vehicleAPI.update(this.id, this.vehicle)
            this.$info('차량 정보가 수정되었습니다.')
            this.$emit('updated')
            this.visible = false
          } else {
            await vehicleAPI.add(this.vehicle)
            this.$info('차량 정보가 등록되었습니다.')
            this.$emit('updated')
            this.visible = false
          }
        } catch (error) {
          this.errorHandle(error)
        }
      },
    }
  }
</script>
