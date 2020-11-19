import axios from 'axios'
import store from '../store'
import authHeader from '../services/auth-header'
const apiAxios = axios.create({
  baseURL: 'http://localhost:8080/api/',
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' }
})

// before any API call make sure that the access token is good
apiAxios.interceptors.request.use(async config => {
  await store.dispatch('auth/checkAccessTokenExpiry')
  config.headers = authHeader()
  return config
})
export default apiAxios
