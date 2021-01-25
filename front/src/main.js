import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store'
import './plugins/lodash'
import './plugins/moment'
import './plugins/eventbus'
import './plugins/message'
import './plugins/fc'
import './plugins/naver-maps'
import vuetify from './plugins/vuetify'
import '@/sass/default.sass'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App),
}).$mount('#app')
