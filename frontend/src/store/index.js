import Vue from 'vue'
import Vuex from 'vuex'
import state from './modules/state'
import user from './modules/user'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    state,
    user
  },
  strict: debug,
  plugins: []
})
