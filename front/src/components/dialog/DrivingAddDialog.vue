<template>
  <v-dialog
    v-model="visible"
    persistent
    max-width="800"
  >
    <v-card>
      <v-card-title class="headline">
        운행 등록
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
            v-model="driving.boardingCoordinates"
            :rules="[rules.required]"
            dense
            outlined
            label="출발지"
          />
          <v-text-field
            v-model="driving.destinationCoordinates"
            :rules="[rules.required]"
            dense
            outlined
            label="목적지"
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
                v-if="driving.boardingCoordinates"
                :lng="boardingLng"
                :lat="boardingLat" />
              <naver-marker
                v-if="driving.destinationCoordinates"
                :lng="destinationLng"
                :lat="destinationLat" />
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
              @onClick="save"
            />
            <fc-button-cancel
              @onClick="visible = false"
            />
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
    <DrivingAddSelector
      ref="DrivingAddSelector"
      @onClickBoarding="driving.boardingCoordinates = selectedCoordinates"
      @onClickDestination="driving.destinationCoordinates = selectedCoordinates"
    />
  </v-dialog>
</template>
<script>
  import drivingAPI from '@/api/driving'
  import { formValidator } from '@/mixins/formValidator'
  import { errorHandling } from '@/mixins/errorHandling'

  export default {
    name: 'DrivingDialog',
    components: {
      DrivingAddSelector: () => import('@/components/dialog/DrivingAddSelector'),
    },
    mixins: [errorHandling, formValidator],
    props: {},
    data () {
      return {
        visible: false,
        valid: false,
        driving: this.emptyDriving(),
        mapOptions: {
          lng: 127.02134,
          lat: 37.51097,
          zoom: 15
        },
        map: null,
        selectedCoordinates: null
      }
    },
    computed: {
      isUpdate () {
        return this.id
      },
      boardingLng () {
        return Number(this.driving.boardingCoordinates.split(',')[0])
      },
      boardingLat () {
        return Number(this.driving.boardingCoordinates.split(',')[1])
      },
      destinationLng () {
        return Number(this.driving.destinationCoordinates.split(',')[0])
      },
      destinationLat () {
        return Number(this.driving.destinationCoordinates.split(',')[1])
      }
    },
    methods: {
      emptyDriving () {
        return {
          boardingCoordinates: null,
          destinationCoordinates: null,
        }
      },
      onMapLoaded (map) {
        const me = this
        this.map = map
        this.map.map.addListener('click', function (e) {
          me.selectedCoordinates = e.coord.x + ',' + e.coord.y
          me.$refs.DrivingAddSelector.open()
          console.log('selectedCoordinates', me.selectedCoordinates)
        })
      },
      open () {
        this.visible = true
        this.vehicle = this.emptyDriving()
        this.$nextTick(() => {
          this.resetFormValidation()
        })
      },
      async save () {
        try {
          await this.validateForm()
          if (!this.valid) {
            return
          }
          await drivingAPI.add(this.driving)
          this.$info('차량 정보가 등록되었습니다.')
          this.$emit('updated')
          this.visible = false
        } catch (error) {
          this.errorHandle(error)
        }
      },
    }
  }
</script>
