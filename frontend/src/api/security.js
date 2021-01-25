import api from './http'

const login = (data) => {
  return api.promise('POST', '/api/security/login', data, { headers: { 'Content-Type': 'multipart/form-data' } })
}

const logout = () => {
  return api.promise('POST', '/api/security/logout', null)
}

const getSession = () => {
  return api.promise('GET', '/api/security/session')
}

export default {
  login,
  logout,
  getSession
}
