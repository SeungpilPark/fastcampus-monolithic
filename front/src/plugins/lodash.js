import Vue from 'vue'
import _ from 'lodash'

if (!Vue.prototype._) {
  Object.defineProperty(Vue.prototype, '_', { value: _ })
}
