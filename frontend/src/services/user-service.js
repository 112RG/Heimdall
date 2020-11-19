import axios from '../utils/apiAxios'
import authHeader from './auth-header'

// const API_URL = 'http://localhost:8080/api/test/'

class UserService {
  getPublicContent () {
    return axios.get('all')
  }

  getUserBoard () {
    return axios.get('user', { headers: authHeader() })
  }

  getModeratorBoard () {
    return axios.get('mod', { headers: authHeader() })
  }

  getAdminBoard () {
    console.log(authHeader())

    return axios.get('test/admin', { headers: authHeader() })
  }
}

export default new UserService()
