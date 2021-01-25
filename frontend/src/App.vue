<template>
  <div id="appRoot">
    <router-view />

    <v-snackbar
      v-model="snackbar.show"
      top
      center
      :color="snackbar.color"
      :timeout="3000"
    >
      <span>{{ snackbar.text }} </span>
      <v-icon
        dark
        aria-label="Close"
        @click="snackbar.show = false"
      >
        mdi-close
      </v-icon>
    </v-snackbar>
    <fc-alert
      v-model="alert.dialog"
      :title="alert.title"
      :content="alert.content"
      @callback="alert.callback">
    </fc-alert>
    <fc-confirm
      v-model="confirm.dialog"
      :title="confirm.title"
      :content="confirm.content"
      @confirm="confirm.confirm"
      @cancel="confirm.cancel">
    </fc-confirm>
    <fc-loading
      v-model="loading.dialog"
    />
  </div>
</template>

<script>
  export default {
    name: 'App',
    components: {},
    created () {
      window.app.$on('INFO', (msg) => {
        this.snackbar = {
          show: true,
          color: 'info',
          text: msg
        }
      })
      window.app.$on('FAIL', (msg) => {
        this.snackbar = {
          show: true,
          color: 'error',
          text: msg
        }
      })
      window.app.$on('ALERT', (title, content, callback = () => {
      }) => {
        this.alert = {
          dialog: true,
          title: title,
          content: content,
          callback: callback
        }
      })
      window.app.$on('CONFIRM', (title, content, confirm, cancel) => {
        this.confirm = {
          dialog: true,
          title: title,
          content: content,
          confirm: confirm,
          cancel: cancel
        }
      })
      window.app.$on('LOADING_START', () => {
        this.loading.dialog = true
      })
      window.app.$on('LOADING_END', () => {
        this.loading.dialog = false
      })
    },
    data () {
      return {
        snackbar: {
          show: false,
          text: '',
          color: ''
        },
        alert: {
          dialog: false,
          title: '제목',
          content: '내용',
          callback: () => {
          }
        },
        confirm: {
          dialog: false,
          title: '제목',
          content: '내용',
          confirm: () => {
          },
          cancel: () => {
          }
        },
        loading: {
          dialog: false,
        }
      }
    },
    methods: {},
  }
</script>
