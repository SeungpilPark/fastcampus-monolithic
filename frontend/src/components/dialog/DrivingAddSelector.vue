<template>
  <v-dialog
    v-model="visible"
    max-width="400"
    persistent
  >
    <v-card>
      <v-card-title class="pl-4">
        경로 설정
        <v-spacer />
        <v-icon
          aria-label="Close"
          @click="visible = false"
        >
          mdi-close
        </v-icon>
      </v-card-title>

      <v-card-text class="body-1 text-center">
        <v-row>
          <v-col cols="12" class="d-flex justify-center content">
            출발지 또는 도착지로 설정하세요.
          </v-col>
        </v-row>

        <fc-button-add
          :title="'출발지'"
          class="mr-3"
          @onClick="onClickBoarding"
        />
        <fc-button-add
          :title="'도착지'"
          class="mr-3"
          @onClick="onClickDestination"
        />
        <fc-button-cancel
          @onClick="visible = false"
        />
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>
  export default {
    name: 'DrivingAddSelector',
    data () {
      return {
        visible: false,
      }
    },
    methods: {
      open () {
        this.visible = true
      },
      onClickBoarding () {
        this.visible = false
        this.$emit('onClickBoarding', this)
      },
      onClickDestination () {
        this.visible = false
        this.$emit('onClickDestination', this)
      }
    }
  }
</script>
<style lang="sass" scoped>
  .content
    white-space: pre-wrap
    font-weight: normal
</style>
