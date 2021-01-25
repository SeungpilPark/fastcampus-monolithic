<template>
  <v-dialog
    v-model="dialog"
    overlay-opacity="0"
    persistent
    width="300"
  >
    <v-card
      color="primary"
      dark
    >
      <v-card-text class="white--text pt-3">
        <span>처리 중 입니다.</span>
        <v-progress-linear
          indeterminate
          color="white"
          class="mb-0"
        />
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>
  export default {
    name: 'Loading',
    props: {
      value: {
        type: Boolean,
        default: false,
      },
    },
    data () {
      return {
        dialog: false,
      }
    },
    watch: {
      value (val) {
        this.dialog = val
      },
      dialog (val) {
        if (!val) {
          this.$emit('input', false)
        }
      }
    },
  }
</script>
