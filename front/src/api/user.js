import api from './http'

const search = (data = null) => {
  return api.promise('GET', '/api/user', data)
}

const get = (seq) => {
  return api.promise('GET', '/api/user/' + seq)
}

const add = (data = null) => {
  return api.promise('POST', '/api/user', data, { isLoading: true })
}

const update = (seq, data = null) => {
  return api.promise('PUT', '/api/user/' + seq, data, { isLoading: true })
}

export default {
  search,
  get,
  add,
  update
}
