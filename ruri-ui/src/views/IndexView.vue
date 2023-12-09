<template>
  <div class="index-wrapper">
    <div class="navigation-wrapper">
      <div class="navigation-inner-wrapper">
        <div class="navigation-title">
          小说创作平台
        </div>
        <div class="navigation-sub-title">
          清风不识字，何故乱翻书
        </div>
        <div class="search-wrapper">
          <el-input size="small" placeholder="搜索书籍" class="search-input" v-model="search" @keyup.enter.native="searchBook()">
            <template #prefix>
              <el-icon class="el-input__icon">
                <Search/>
              </el-icon>
            </template>
          </el-input>
        </div>
        <div class="user-wrapper">
          <div class="user-title">
            {{ store.isAuth ? "用户空间" : '请登录后操作' }}
            <span class="right-text" v-if="store.userInfo.username" @click="logout">
              注销
            </span>
            <span class="right-text" v-else @click="login">
              登录
            </span>
          </div>
          <div class="user-item">
            <el-tag type="info" class="tag-btn" effect="dark" size="large" @click="showUserManage">
              作者信息
            </el-tag>
          </div>
        </div>
        <div class="writing-wrapper">
          <div class="writing-title">
            创作中心
          </div>
          <div class="writing-item">
            <el-tag effect="dark" class="tag-btn" type="info" size="large" @click="showBookManage">
              小说管理
            </el-tag>
          </div>
        </div>
      </div>
    </div>
    <div class="shelf-wrapper">
      <div class="shelf-title">
        {{ isSearchResult ? "搜索" : "书架" }}
        ({{ isSearchResult ? searchResult.length : bookList.length }})
        <div class="title-btn" v-if="isSearchResult" @click="backToShelf">
          书架
        </div>
      </div>
      <div class="books-wrapper" ref="bookList">
        <div class="wrapper">
          <el-empty v-if="!store.bookList.length" description="创建我的小说">
            <el-button type="primary">创建小说</el-button>
          </el-empty>
          <div class="book" v-for="book in bookList" :key="book.novelID">
            <div class="book-info">
              <div class="name">
                {{ book.name }}
              </div>
              <div class="sub">
                {{ book.totalChapterNum }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Search, Menu} from '@element-plus/icons-vue';
import {useStore} from '@/stores/pinia';
import {ElNotification, ElMessage} from "element-plus";
import axios from "axios";
import eventBus from '@/plugins/eventBus';
import moment from "moment/moment";

export default {
  name: "Index",

  components: { Search, Menu },

  data() {
    return {
      username: "",
      search: "",

      isSearchResult: false,
      isExploreResult: false,

      searchResult: [ ],
    }
  },

  computed: {
    bookList() {
      return this.isSearchResult ? this.searchResult : this.showShelfBooks;
    },

    showShelfBooks() {
        return this.store.bookList;
    }
  },

  setup() {
    const store = useStore()
    return {
      store: store,
    }
  },

  methods: {
    showBookManage() {
      this.searchMyBook()
      eventBus.emit('showBookManage')
    },

    showUserManage() {
      eventBus.emit('showUserManage')
    },

    login() {
      eventBus.emit('showLogin')
    },

    logout() {
      this.store.userInfo = { }
      this.store.token = ''
      ElNotification.info({
        title: "退出成功",
        message: "期待下次再来"
      })
    },

    searchMyBook() {
      axios.get(
          'http://localhost:8080/api/novel/searchMyShelf',
          {
            params: {
              token: this.store.token
            }
          }
      ).then((response) => {
        // console.log(response.data)

        response.data.result.forEach((item) => {
          const updateAt = moment(item.updateAt).format('YYYY/MM/DD H:mm:ss')
          const createAt = moment(item.createAt).format('YYYY/MM/DD H:mm:ss')

          item.updateAt = updateAt
          item.createAt = createAt
        })

        this.store.updateBookList(response.data.result)

      }).catch((error) => {

      })
    },

    searchBook() {
      if (this.store.token === '') {
        ElMessage.error('请登录后使用')
      }

      axios.get(
          "http://localhost:8080/api/novel/search",
          {
            params: {
              q: this.search
            }
          }
      ).then((response) => {
        console.log(response.data)
        this.searchResult = response.data.result

        this.isSearchResult = true

      }).catch((error) => {
        console.log(error)
      })
    },

    backToShelf() {
      this.isSearchResult = false;
      this.isExploreResult = false;
      this.searchResult = [];
    },

  }
}
</script>

<style lang="stylus" scoped>
.index-wrapper {
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: row;

  .navigation-wrapper {
    width: 260px;
    min-width: 260px;
    height: 100%;
    box-sizing: border-box;
    background-color: #F7F7F7;
    position: relative;

    .navigation-inner-wrapper {
      padding: 48px 36px 66px 36px;
      height: 100%;
      overflow-y: auto;
      box-sizing: border-box;
    }

    .navigation-title {
      font-size: 22px;
      font-weight: 600;
    }

    .navigation-sub-title {
      font-size: 16px;
      font-weight: 500;
      margin-top: 16px;
      color: #b1b1b1;
    }

    .search-wrapper {
      .search-input {
        margin-top: 18px;
      }

      :deep( .el-input__wrapper ) {
        border-radius: 50px;
        border-color: #E3E3E3;
      }
    }

    .user-wrapper {
      margin-top: 36px;

      .user-title {
        font-size: 14px;
        color: #b1b1b1;
      }

      .right-text {
        float: right;
        display: inline-block;
        height: 22px;
        line-height: 22px;
        cursor: pointer;
        user-select: none;
      }

      .user-item {
        padding-top: 16px;
      }
    }

    .writing-wrapper {
      margin-top: 36px;

      .writing-title {
        font-size: 14px;
        color: #b1b1b1;
      }

      .writing-item {
        padding-top: 16px;
      }
    }

    .tag-btn {
      width: 100%;
      margin-right: 15px;
      margin-bottom: 15px;
      cursor: pointer;
    }
  }

  .shelf-wrapper {
    padding: 48px 48px;
    height: 100%;
    max-height: 100%;
    width: 100%;
    background-color: #fff;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;

    .shelf-title {
      font-size: 20px;
      font-weight: 600;
      margin-bottom: 5px;
      min-width: 320px;
      box-sizing: border-box;

      .title-btn {
        font-size: 14px;
        line-height: 28px;
        font-weight: 600;
        float: right;
        cursor: pointer;
        user-select: none;
        margin-left: 10px;
      }
    }

    .books-wrapper {
      flex: 1;
      overflow-x: hidden;
      overflow-y: scroll;
    }
  }
}

</style>
