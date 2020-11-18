import AuthService from '../services/auth-service'
import jwtDecode from 'jwt-decode'
const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null }

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login ({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          commit('loginSuccess', user)
          return Promise.resolve(user)
        },
        error => {
          commit('loginFailure')
          console.log('Login failed')
          return Promise.reject(error)
        }
      )
    },
    logout ({ commit }) {
      AuthService.logout()
      commit('logout')
    },
    register ({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess')
          return Promise.resolve(response.data)
        },
        error => {
          commit('registerFailure')
          return Promise.reject(error)
        }
      )
    },
    checkAccessTokenExpiry ({ commit, state, getters, dispatch }) {
      if (state.status.loggedIn) {
        console.log('User is logged in')
        const access = jwtDecode(state.user.accessToken)
        const nowInSecs = Date.now() / 1000
        console.log((access.exp - nowInSecs))
        const isExpiring = (access.exp - nowInSecs) < 30
        if (isExpiring) {
          console.log('Token expirying')
          AuthService.logout()
          commit('logout')
          return false
        } else {
          console.log('Not expiryed')
          return true
        }
      }
      return false

      // inspect the store access token's expiration\
      /*       if (getters.isLoggedIn) {
        const access = jwtDecode(state.jwt.access)
        const nowInSecs = Date.now() / 1000
        const isExpiring = (access.exp - nowInSecs) < 30
        // if the access token is about to expire
        if (isExpiring) {
          // dispatch('refreshTokens')
        }
      } */
    },
    isLoggedIn ({ getters, dispatch }) {
      console.log('Checking loggined int')
      const refresh = localStorage.getItem('user')
      if (refresh) {
        if (getters.isLoggedIn) {
          dispatch('checkAccessTokenExpiry')
        } else {
          // dispatch('refreshAccessToken')
        }
        return getters.isLoggedIn
      }
      return false
    }
  },
  mutations: {
    loginSuccess (state, user) {
      state.status.loggedIn = true
      state.user = user
    },
    loginFailure (state) {
      state.status.loggedIn = false
      state.user = null
    },
    logout (state) {
      state.status.loggedIn = false
      state.user = null
    },
    registerSuccess (state) {
      state.status.loggedIn = false
    },
    registerFailure (state) {
      state.status.loggedIn = false
    }
  },
  getters: {
    isLoggedIn: state => state.status.loggedIn
  }
}
