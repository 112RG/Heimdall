<template>
  <div class="d-flex justify-content-center" style="padding-top:50px;">
  <b-jumbotron class="col-md-10">
      <div class="row justify-content-center">
        <div class="col-auto mb-3">
            <b-card header="Upload" class="text-left" border-variant="secondary" style="width: 30rem;">
                <b-card-body>
                        <small>{{currentUser}}</small>
                </b-card-body>
            </b-card>
        </div>
      </div>
  </b-jumbotron>
  </div>
</template>

<script>
export default {
  name: 'Admin',
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
  }
}
</script>
