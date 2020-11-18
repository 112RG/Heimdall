import axios from 'axios'
import store from '../store'

const apiAxios = axios.create({
  baseURL: 'http://localhost:8080/api/',
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' }
})

// before any API call make sure that the access token is good
apiAxios.interceptors.request.use(async config => {
  store.dispatch('auth/isLoggedIn')
  /*   const response = await store.dispatch('getUserSession')
  if (response && response.accessToken && response.accessToken.jwtToken) {
    config.headers.AccessToken = response.accessToken.jwtToken
  } */
  return config
})

export default apiAxios
