<template>
  <div class="app">
    <keep-alive>
      <router-view> </router-view>
    </keep-alive>
  </div>
</template>

<script>
// import axios from 'axios'

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      isLogin: true,
      showLoginDialog: false
    }
  },
  beforeMount: {},
  computed: {}
}
</script>

<style></style>
