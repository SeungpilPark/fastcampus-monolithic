const state = {
  session: {},
  beforeRoutePath: '/'
}

const mutations = {
  UPDATE_LOGIN_SESSION (pState, payload) {
    pState.session = payload
  },
  UPDATE_BEFORE_ROUTE_PATH (pState, payload) {
    pState.beforeRoutePath = payload
  }
}
const getters = {
  getBeforeLocation: (pState) => {
    return window.origin + pState.beforeRoutePath
  }
}

export default {
  namespaced: true,
  state,
  getters,
  mutations
}
