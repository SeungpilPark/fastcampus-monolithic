import Vue from 'vue'

const info = (message) => {
  window.app.$emit(
    'INFO',
    message
  )
}

const fail = (message) => {
  window.app.$emit(
    'FAIL',
    message
  )
}

const alert = (title, message, callback) => {
  window.app.$emit(
    'ALERT',
    title,
    message,
    callback
  )
}

const confirm = (title, message, confirmHandler, cancel) => {
  window.app.$emit(
    'CONFIRM',
    title,
    message,
    confirmHandler,
    cancel
  )
}

Vue.prototype.$info = info
Vue.prototype.$fail = fail
Vue.prototype.$alert = alert
Vue.prototype.$confirm = confirm
