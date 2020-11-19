import axios from 'axios'
// import axios from 'axios'
const API_URL = 'http://localhost:8080/api/auth/'
class AuthService {
  login (user) {
    console.log('LOGIN')
    return axios.post(
      API_URL + 'signin', {
        email: user.email,
        password: user.password
      }
    ).then(response => {
      if (response.data.accessToken && response.data.refreshToken) {
        localStorage.setItem('user', JSON.stringify(response.data))
      }
      return response.data
    })
  }

  refresh (refreshToken) {
    return axios.get(
      API_URL + 'refreshToken', {
        params: {
          token: refreshToken
        }
      }
    ).then(response => {
      if (response.data.accessToken && response.data.refreshToken) {
        return response.data
        /*         const user = JSON.parse(localStorage.getItem('user'))
        user.accessToken = response.data.accessToken
        user.refreshToken = response.data.refreshToken
        localStorage.setItem('user', JSON.stringify(user))
        return true */
      }
      return 'false'
    }).catch(() => {
      return ''
    })
  }

  logout () {
    localStorage.removeItem('user')
  }

  register (user) {
    return axios.post(API_URL + 'signup', {
      email: user.email,
      pasword: user.password
    })
  }
}

export default new AuthService()
