import api from './http'

const search = (data = null) => {
  return api.promise('GET', '/api/driving', data)
}

const get = (seq) => {
  return api.promise('GET', '/api/driving/' + seq)
}

const add = (data = null) => {
  return api.promise('POST', '/api/driving', data, { isLoading: true })
}

const cancel = (seq) => {
  return api.promise('DELETE', '/api/driving/' + seq)
}

const dispatchAcceptance = (data = null) => {
  return api.promise('POST', '/api/driving/acceptance', data, { isLoading: true })
}

export default {
  search,
  get,
  add,
  cancel,
  dispatchAcceptance
}
