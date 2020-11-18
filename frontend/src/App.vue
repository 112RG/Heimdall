<template>
  <div id="app">
    <b-navbar v-if="currentUser" toggleable="md" type="dark" variant="info">
      <b-nav>
        <b-nav-item to="/user/home">Home</b-nav-item>
        <b-nav-item to="/user/upload">Gallery</b-nav-item>
        <b-nav-item to="/user/upload">Settings</b-nav-item>
      </b-nav>
      <b-nav class="ml-auto">
        <b-nav-item v-if="showAdminBoard" to="/admin">Admin</b-nav-item>
        <b-nav-item to="/user/upload">Info</b-nav-item>
        <b-nav-item href @click.prevent="logOut">Logout</b-nav-item>
      </b-nav>
    </b-navbar>
    <router-view/>
  </div>
</template>

<script>
export default {
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    showAdminBoard () {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_ADMIN')
      }

      return false
    },
    showModeratorBoard () {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_MODERATOR')
      }

      return false
    }
  },
  methods: {
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/login')
    }
  }
}
</script>
<style>

#app {
  font-family: "Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  font-size: 14px !important;
}

#app .nav-item a {
  color: rgba(255,255,255,.5);
  padding: 0.1rem 0.5rem;

}
#app .nav-item a:hover {
  color: rgba(255, 255, 255, 0.212);
}

</style>
