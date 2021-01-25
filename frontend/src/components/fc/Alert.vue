<template>
  <v-dialog
    v-model="dialog"
    max-width="400"
    persistent
  >
    <v-card>
      <v-card-title class="pl-4">
        {{ title }}
        <v-spacer />
        <v-icon
          aria-label="Close"
          @click="dialog = false"
        >
          mdi-close
        </v-icon>
      </v-card-title>

      <v-card-text class="body-1 text-center">
        <v-row>
          <v-col cols="12" class="d-flex justify-center content">
            {{ content }}
          </v-col>
        </v-row>

        <v-btn
          class="mx-0 mt-5"
          color="primary"
          depressed
          default
          @click="onClickButton"
        >
          {{ confirmText }}
        </v-btn>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>
  export default {
    name: 'Alert',
    props: {
      value: {
        type: Boolean,
        default: false,
      },
      title: {
        type: String,
        default: '',
      },
      content: {
        type: String,
        default: '',
      },
      confirmText: {
        type: String,
        default: '확인',
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
    methods: {
      onClickButton () {
        this.dialog = false
        this.$emit('callback', this)
      }
    }
  }
</script>
<style lang="sass" scoped>
  .content
    white-space: pre-wrap
    font-weight: normal
</style>
