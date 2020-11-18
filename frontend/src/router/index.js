import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Admin from '../views/Admin.vue'
import UserHome from '../views/UserHome.vue'
import store from '../store'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/user/home',
    name: 'UserHome',
    component: UserHome
  },
  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
    beforeEnter: checkAdmin
  },
  {
    path: '*',
    component: Home
  }

/*   {
    path: '/about',
    name: 'About',
     route level code-splitting
     this generates a separate chunk (about.[hash].js) for this route
     which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" '../views/About.vue')
  }
  */
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
function checkAdmin (to, from, next) {
  const user = JSON.parse(localStorage.getItem('user'))
  // check if the user is admin
  if (user.roles.includes('ROLE_ADMIN')) {
    console.log('To admin')
    next()
  } else {
    next({ path: '/user/home' })
  }
}
router.beforeEach(async (to, from, next) => {
  const publicPages = ['/login', '/register']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = await store.dispatch('auth/isLoggedIn')
  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn
  ) {
    next('/login')
  } else {
    next()
  }
})
export default router
