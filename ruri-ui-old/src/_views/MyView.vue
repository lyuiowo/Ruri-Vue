<script>
import { RouterLink, RouterView } from 'vue-router'
import axios from 'axios'

export default {
  components: { RouterView, RouterLink },

  data() {
    return {
      token: ''
    }
  },

  mounted() {
    this.token = localStorage.getItem('token') || ''
    console.log('MyView.vue', this.token)
  },

  methods: {}
}
</script>

<template>
  <main></main>
</template>

<style scoped></style>
