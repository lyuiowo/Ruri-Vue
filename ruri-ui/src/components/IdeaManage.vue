<template>

</template>

<style scoped lang="stylus">

</style>

<script>
import { useStore } from '@/stores/pinia'

export default {
  name: 'IdeaManage',
  data() {
    return {

    }
  },

  setup() {
    const store = useStore()
    return {
      store: store
    }
  },

  methods: {

  }
}
</script>