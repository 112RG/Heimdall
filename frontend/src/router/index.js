import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import UserHome from '../views/UserHome.vue'

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
router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('user')

  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    next('/login')
  } else {
    next()
  }
})
export default router
