<template>
  <div class="chapter-wrapper">
    <div class="chapter">
      <div class="content">
        <div class="content-inner">
          <div class="content-title">
            {{ currentChapter.title }}
          </div>
        </div>
      </div>
    </div>
  </div>
  <template>
    <div class="chapter-wrapper">
      <div class="chapter">
        <div class="content">
          <div class="content-inner">
            <div class="content-title">
              {{ currentChapter.title }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
</template>

<script>
import { get, post } from '@/plugins/axios'

export default {
  name: "Reader",

  data() {
    return {
      bookInfo: { }
    }
  },

  mounted() {
    if (this.showBookManageDialog) {
      this.searchBookChapter()
    }
  },

  methods: {
    searchBookChapter(chapterID) {
      // axios.get(
      //     'http://localhost:8080/api/chapter/search',
      //     {
      //       params: {
      //         chapterID: chapterID
      //       }
      //     }
      // ).then((response) => {
      //   console.log(response.data)
      // }).catch((error) => {
      //
      // })
      const chapterApi = '/chapter/search'
      const params = { chapterID: chapterID }
      const chapterData = get(chapterApi, params).then(chapterData => {
        console.log(chapterData)
      }).catch(error => {

      })
    }
  }
}

</script>