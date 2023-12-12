<template>
    <div class="writer-wrapper">
      <div class="navigation-wrapper">
        <div class="navigation-inner-wrapper">
          <div class="navigation-title">
            小说创作平台
          </div>
          <div class="navigation-sub-title">
            清风不识字，何故乱翻书
          </div>
          <div class="search-wrapper">
            <el-input size="small" placeholder="搜索章节" class="search-input" v-model="search"
                      @keyup.enter.native="searchChapter()">
              <template #prefix>
                <el-icon class="el-input__icon">
                  <Search/>
                </el-icon>
              </template>
            </el-input>
          </div>
          <div class="drafts-wrapper">
            <div class="draft-chapter" v-for="item in searchResult">

            </div>
          </div>

        </div>
      </div>
      <div class="chapter-wrapper">
        <div class="tools-bar">
          <el-button @click="back">
            <el-icon>
              <back />
            </el-icon>
            返回
          </el-button>
          <el-button @click="save">
            保存
          </el-button>
        </div>
        <div class="title">
          <el-input v-model="chapter.title" label="请输入章节号和章节名。示例：第三章 十里桃花"></el-input>
        </div>
      </div>
    </div>
</template>

<script>
import {get, post} from "@/plugins/axios";
import {Back, Search} from "@element-plus/icons-vue";

export default {
  name: 'Writer',
  components: {Back, Search},

  data() {
    return {
      chapter: {
        title: "",
        content: "",
        cid: 1,
        status: 0
      },

      chapterList: {
        0: [ ], // 保存
        1: [ ], // 发布
        2: [ ], // 删除
      },

      novelID: 0,
      search: '',
      searchResult: [ ]
    }
  },

  mounted() {
    this.novelID = this.$route.params.novelID
    this.searchAllChapter()
  },

  methods: {
    back() {
      this.$router.push('/')
    },

    save() {
      const chapterApi = '/chapter'
      let params = { token: localStorage.getItem("token"), title: this.chapter.title, content: this.chapter.content }
      if (this.chapter.cid === 0) {
        params.id = parseInt(this.novelID, 10)
        post(chapterApi + '/create', params).then(response => {
          console.log("创建章节: ", response)
        })
      } else {
        params.id = this.chapter.cid
        post(chapterApi + '/update', params).then(response => {
          console.log("保存章节: ", response)
        })
      }
    },

    searchAllChapter() {
      const chapterApi = '/chapter/search'
      const params = { nid: this.novelID }
      get(chapterApi, params).then(response => {
        console.log(response.result)
        response.result.forEach((item) => {
          switch (item.status) {
            case 0:
              this.chapterList["0"].push(item)
              break;
            case 1:
              this.chapterList["1"].push(item)
              break;
            case 2:
              this.chapterList["2"].push(item)
          }
        })
        console.log(this.chapterList)
      })
    },

    searchChapter() {
      const chapterApi = '/chapter/search'
      const params = { nid: this.novelID, title: this.search }
      get(chapterApi, params).then(response => {
        console.log(response.result)
        this.searchResult = response.result
      })
    }
  }
}
</script>

<style lang="stylus" scoped>
@import "@/assets/main.css";

.writer-wrapper {
  .chapter-wrapper {
    padding: 48px 48px;
    height: 100%;
    max-height: 100%;
    width: 100%;
    background-color: #fff;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;

    .tools-bar {
      margin-bottom: 12px;
    }
  }
}

</style>