import axios from '../utils/apiAxios'
// import axios from 'axios'
class AuthService {
  login (user) {
    console.log('LOGIN')
    return axios.post(
      'auth/signin', {
        email: user.email,
        password: user.password
      }
    ).then(response => {
      if (response.data.accessToken) {
        localStorage.setItem('user', JSON.stringify(response.data))
      }
      return response.data
    })
  }

  logout () {
    localStorage.removeItem('user')
  }

  register (user) {
    return axios.post('auth/signup', {
      email: user.email,
      pasword: user.password
    })
  }
}

export default new AuthService()
