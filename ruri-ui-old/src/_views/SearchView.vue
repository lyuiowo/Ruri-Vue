<script>
import axios from 'axios'

export default {
  data() {
    return {
      q: ''
    }
  },

  mounted() {
    this.q = this.$router.query.q || ''
    console.log(this.q)
  },

  methods: {}
}
</script>

<template>
  <main>搜索页</main>
</template>

<style scoped></style>
