<template>
  <div class="d-flex justify-content-center" style="padding-top:50px;">
  <b-jumbotron class="col-md-10">
      <div class="row justify-content-center">
        <div class="col-auto mb-3">
            <b-card header="Upload" class="text-left" border-variant="secondary" style="width: 30rem;">
                <b-card-body>
                    <b-form id="uf">
                        <b-form-file plain multiple/>
                        <small>{{user}}</small>
                        <b-button v-on:click="decodeToken()">DECODE TOKENS</b-button>
                        <b-button v-on:click="getAdmin()">AXIOS</b-button>
                    </b-form>
                </b-card-body>
            </b-card>
        </div>
      </div>
  </b-jumbotron>
  </div>
</template>

<script>
export default {
  name: 'UserHome',
  data: function () {
    return {
      user: this.$store.state.auth.user
    }
  },
  methods: {
    decodeToken () {
      this.$store.dispatch('auth/checkAccessTokenExpiry')
    },
    getAdmin () {
      this.$store.dispatch('auth/getAdmin')
    }
  },

  watch: {
    user: function (newUser, oldUser) {
      this.$data.user = this.$store.state.auth.user
    }
  },
  computed: {

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
  }
}
</script>
