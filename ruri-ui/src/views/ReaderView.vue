<template>
  <div class="chapter-wrapper">
    <div class="chapter">
      <div class="content">
        <div class="content-inner">
          <div class="content-title">
            {{ store.currentChapter.title }}
          </div>
        </div>
      </div>
    </div>
  </div><template>
  <div class="chapter-wrapper">
    <div class="chapter">
      <div class="content">
        <div class="content-inner">
          <div class="content-title">
            {{ store.currentChapter.title }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

  <script>
    import { useStore } from "@/stores/pinia";
    import axios from "axios";

    export default {
      name: "Reader",

      data() {
        return {
          bookInfo: { }
        }
      },

      setup() {
        const store = useStore()
        return {
          store: store,
        }
      },

      mounted() {
        if (this.showBookManageDialog) {
          this.searchBookChapter()
        }
      },

      methods: {
        searchBookChapter(chapterID) {
          axios.get(
              'http://localhost:8080/api/chapter/search',
              {
                params: {
                  chapterID: chapterID
                }
              }
          ).then((response) => {
            console.log(response.data)
          }).catch((error) => {

          })
        }
      }
    }

  </script>
</template>

<script>
import { useStore } from "@/stores/pinia";
import axios from "axios";

export default {
  name: "Reader",

  data() {
    return {
      bookInfo: { }
    }
  },

  setup() {
    const store = useStore()
    return {
      store: store,
    }
  },

  mounted() {
    if (this.showBookManageDialog) {
      this.searchBookChapter()
    }
  },

  methods: {
    searchBookChapter(chapterID) {
      axios.get(
          'http://localhost:8080/api/chapter/search',
          {
            params: {
              chapterID: chapterID
            }
          }
      ).then((response) => {
        console.log(response.data)
      }).catch((error) => {

      })
    }
  }
}

</script>