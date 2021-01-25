import axios from 'axios'

const headers = { 'X-Requested-With': 'XMLHttpRequest' }

axios.interceptors.request.use(
  function (config) {
    if (config.isLoading && window.app) {
      window.app.$emit('LOADING_START')
    }
    return config
  },
  function (error) {
    return Promise.reject(error)
  })

axios.interceptors.response.use(
  function (response) {
    if (window.app) {
      window.app.$emit('LOADING_END')
    }
    return response
  },
  function (error) {
    if (window.app) {
      window.app.$emit('LOADING_END')
    }
    return Promise.reject(error)
  })

const promise = (method, uri, data = null, options = null) => {
  const config = {
    method: method,
    url: uri,
    ...options,
    headers: {
      ...headers,
      ...(options && options.headers)
    }
  }

  if (method.toUpperCase() === 'GET') {
    let index = 0
    for (const key in data) {
      const encodedParam = encodeURIComponent(data[key])
      if (index === 0) {
        config.url = config.url + '?' + key + '=' + encodedParam
      } else {
        config.url = config.url + '&' + key + '=' + encodedParam
      }
      index++
    }
    config.params = {}
  } else {
    config.data = data
  }
  return axios(config).then(response => {
    return Promise.resolve(response.data)
  }).catch(error => {
    return Promise.reject(error)
  })
}

export default {
  promise,
}
