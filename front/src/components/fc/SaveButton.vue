<template>
  <v-btn
    v-if="hasPermission"
    depressed
    color="primary"
    :disabled="disabled"
    @click="onClick"
  >
    {{ title }}
    <base-confirm
      v-model="confirmDialog.isShow"
      :title="confirm.title"
      :content="confirm.content"
      confirm-text="확인"
      cancel-text="취소"
      @confirm="confirmDialog.onClickConfirm"
      @cancel="confirmDialog.onClickCancel"
    />
  </v-btn>
</template>

<script>
  import { mapState } from 'vuex'

  export default {
    name: 'SaveButton',
    props: {
      title: {
        type: String,
        default: '저장'
      },
      isEnabledConfirm: {
        type: Boolean
      },
      disabled: {
        type: Boolean,
        default: false
      },
      confirm: {
        type: Object,
        default () {
          return {
            title: '저장 확인',
            content: '저장하시겠습니까?'
          }
        }
      }
    },
    data () {
      return {
        confirmDialog: {
          isShow: false,
          onClickConfirm: this.onConfirm,
          onClickCancel: this.onToggleConfirmDialog,
        },
        hasPermission: false
      }
    },
    mounted () {
      this.hasPermission = this.permission === 'WRITE'
    },
    computed: {
      ...mapState('menu', ['permission']),
      hasConfirm () {
        return this.$listeners.onConfirm
      },
    },
    methods: {
      onToggleConfirmDialog () {
        this.confirmDialog.isShow = !this.confirmDialog.isShow
      },
      onConfirm () {
        this.$emit('onConfirm')
      },
      onClick () {
        if (this.hasConfirm) {
          this.isEnabledConfirm ? this.showConfirmDialog() : this.onConfirm()
          return
        }
        this.$emit('onClick')
      },
      showConfirmDialog () {
        if (this.$listeners.onBeforeShowConfirmDialog) {
          this.$emit('onBeforeShowConfirmDialog')
        }

        this.$nextTick(() => {
          if (this.isEnabledConfirm) {
            this.onToggleConfirmDialog()
          }
        })
      }
    }
  }
</script>
