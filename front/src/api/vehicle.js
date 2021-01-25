import api from './http'

const search = (data = null) => {
  return api.promise('GET', '/api/vehicle', data)
}

const get = (seq) => {
  return api.promise('GET', '/api/vehicle/' + seq)
}

const add = (data = null) => {
  return api.promise('POST', '/api/vehicle', data, { isLoading: true })
}

const update = (seq, data = null) => {
  return api.promise('PUT', '/api/vehicle/' + seq, data, { isLoading: true })
}

export default {
  search,
  get,
  add,
  update
}
