import AuthService from '../services/auth-service'
import UserService from '../services/user-service'
import jwtDecode from 'jwt-decode'
import Router from '../router'
const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null }

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    getAdmin ({ commit }) {
      UserService.getAdminBoard()
    },
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
    async checkAccessTokenExpiry ({ commit, state, getters, dispatch }) {
      if (state.status.loggedIn) {
        const access = jwtDecode(state.user.accessToken)
        const nowInSecs = Date.now() / 1000
        console.log((access.exp - nowInSecs))
        const isExpiring = (access.exp - nowInSecs) < 30
        if (isExpiring) {
          console.log('Token expirying')
          const newTokens = await AuthService.refresh(state.user.refreshToken)
          if (newTokens.refreshToken && newTokens.accessToken) {
            console.log('Updating tokens')
            user.refreshToken = newTokens.refreshToken
            user.accessToken = newTokens.accessToken
            localStorage.setItem('user', JSON.stringify(user))
            commit('loginSuccess', user)
          } else {
            console.log('Failed to refresh token')
            AuthService.logout()
            commit('logout')
            Router.push('/login')
          }
        } else {
          console.log('Not expiryed')
        }
      }
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
      const refresh = localStorage.getItem('user')
      if (refresh) {
        if (getters.isLoggedIn) {
          dispatch('checkAccessTokenExpiry')
        } else {
          // dispatch('refreshAccessToken')
          return false
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
