<template>
<div class="login-page">
  <div class="form">
    <div>
      <img
        style="padding-bottom:25px;width: 100%;"
        class="eye"
        src="@/assets/ra.png"
      />
    </div>
    <div class="basic-info">
    <form name="form" @submit.prevent="handleLogin">
      <input id="_csrf" type="hidden" name="_csrf" value="token" />
      <input
        v-model="user.email"
        v-validate="'required'"
        id="email"
        type="email"
        required
        class="form-control"
        name="email"
        placeholder="Email"
      />
      <input
        v-model="user.password"
        v-validate="'required'"
        id="password"
        type="password"
        required
        class="form-control"
        name="password"
        placeholder="Password"
      />
      <input type="submit" value="Submit"/>
      <div class="form-group">
          <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
      </div>
    </form>
    </div>
  </div>
  </div>
</template>

<script>
import User from '../models/User'

export default {
  name: 'Login',
  data () {
    return {
      user: new User('', ''),
      loading: false,
      message: ''
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    }
  },
  created () {
    if (this.loggedIn) {
      this.$router.push('/user/home')
    }
  },
  methods: {
    handleLogin () {
      this.loading = true
      this.$validator.validateAll().then(isValid => {
        if (!isValid) {
          this.loading = false
          return
        }
        if (this.user.email && this.user.password) {
          this.$store.dispatch('auth/login', this.user).then(
            () => {
              this.$router.push('/user/home')
            },
            error => {
              this.loading = false
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString()
            }
          )
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
html,
body {
    background-color: #1a1a1a;
    color: #fff;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
    overflow: hidden;
    font-family: "Roboto", sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
root {
  --loginBorderRadus: 0.25rem;
}
.login-page {
  width: 360px;
  padding: 8% 0 0;
  margin: auto;
}

.form {
    position: relative;
    z-index: 50;
    max-width: 360px;
    margin: 0 auto 100px;
    padding-left: 29px;
    padding-right: 29px;
}
.form-control::placeholder{
    color: white !important;

}
.basic-info input {
    outline: 0;
    background: rgb(88, 88, 88);
    width: 100%;
    border: 0;
    height: auto;
    margin: 0 0 15px;
    padding: 13px;
    box-sizing: border-box;
    font-size: 14px;
    border-radius: 0.25rem;
}
.basic-info input[type='submit'] {
  text-transform: uppercase;
  outline: 0;
  background: rgb(50, 50, 50);
  width: 100%;
  border: 0;
  color: #e6e6e6;
  font-size: 14px;
  -webkit-transition: all 0.3 ease;
  transition: all 0.3 ease;
  cursor: pointer;
}
.basic-info input:focus {
  background: rgb(83, 83, 83);
}
.form button:hover,
.form button:active,
.form button:focus,
.form input[type='submit']:hover,
.form input[type='submit']:focus {
  background: rgb(83, 83, 83);
}
.form .message {
  margin: 15px 0 0;
  color: #8a8a8a;
  font-size: 12px;
}
.form .message a {
  color: #4caf50;
  text-decoration: none;
}

</style>
