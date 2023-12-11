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
          <el-input size="small" placeholder="搜索书籍" class="search-input" v-model="search"
                    @keyup.enter.native="searchNovel()">
            <template #prefix>
              <el-icon class="el-input__icon">
                <Search/>
              </el-icon>
            </template>
          </el-input>
        </div>
        <div class="user-wrapper">
          <div class="user-title">
            {{ isAuth ? "用户空间" : '请登录后操作' }}
            <span class="right-text" v-if="isAuth" @click="logout">
              注销
            </span>
            <span class="right-text" v-else @click="showLoginManageDialog = !showLoginManageDialog">
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
            <el-tag effect="dark" class="tag-btn" type="info" size="large" @click="showNovelManageDialog = !showNovelManageDialog">
              小说管理
            </el-tag>
          </div>
        </div>
      </div>
    </div>
    <div class="shelf-wrapper">
      <div class="shelf-title">
        {{ isSearchResult ? "搜索" : "书架" }}
        ({{ isSearchResult ? searchResult.length : novelList.length }})
        <div class="title-btn" v-if="isSearchResult" @click="backToShelf">
          书架
        </div>
      </div>
      <div class="novels-wrapper" ref="novelList">
        <div class="wrapper">
          <el-empty v-if="!novelList" description="创建我的小说">
            <el-button type="primary">创建小说</el-button>
          </el-empty>
          <div class="novel" v-for="novel in novelsToDisplay" :key="novel.novelID">
            <div class="info">
              <div class="name">
                {{ novel.name }}
              </div>
              <div class="sub">
                共 {{ book.totalChapterNum }} 章
              </div>
              <div class="last-chapter" v-if="book.latestChapterTitle">
                {{ book.lastCheckTime ? dateFormat(book.lastCheckTime) : "最新" }} ：{{ book.latestChapterTitle }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <el-dialog v-model="showLoginManageDialog" width="35%">
    <template #header>
      <div class="custom-dialog-title">
        <span class="el-dialog__title">
          {{ isLogin ? "登录" : "注册" }}
          <span class="float-right span-btn" @click="isLogin = !isLogin">
            {{ isLogin ? "注册" : "登录" }}
          </span>
        </span>
      </div>
    </template>
    <el-form :model="loginForm" label-position="top">
      <el-form-item label="用户名" v-if="!isLogin">
        <el-input v-model="loginForm.username" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="loginForm.email" autocomplete="on"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input
            v-model="loginForm.password"
            autocomplete="on"
            show-password
            @keyup.enter.native="switchLoginAndRegister"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="default" @click="cancel">取 消</el-button>
        <el-button size="default" type="primary" @click="switchLoginAndRegister">确 定</el-button>
      </span>
    </template>
  </el-dialog>

  <NovelManage v-model="showNovelManageDialog" />
  <UserManage v-model="showUserManageDialog" :userData="userData"/>

</template>

<script>
import { Search, Menu } from '@element-plus/icons-vue';
import { ElNotification, ElMessage } from "element-plus";
import eventBus from '@/plugins/eventBus';
import { get, post } from "@/plugins/axios";
import NovelManage from "@/components/NovelManage.vue";
import UserManage from "@/components/UserManage.vue";

export default {
  name: "Index",

  components: { Search, Menu, NovelManage, UserManage },

  computed: {
    novelsToDisplay() {
      return this.isSearchResult ? this.searchResult : this.novelList
    }
  },

  created() {
    if (this.token) {
      this.showLoginManageDialog = false
      this.isAuth = true
      this.searchMyShelf()
    }
  },

  data() {
    return {
      token: localStorage.getItem("token"),
      search: "",

      loginForm: {
        username: '',
        email: '',
        password: ''
      },

      isAuth: false,
      isLogin: true,
      isSearchResult: false,

      searchResult: [ ],
      novelList: [ ],
      userData: { },

      showLoginManageDialog: true,
      showNovelManageDialog: false,
      showUserManageDialog: false
    }
  },

  methods: {
    switchLoginAndRegister() {
      if (this.isLogin) {
        this.login()
      } else {
        this.register()
      }
    },

    login() {
      const loginApi = '/login'
      const params = this.loginForm
      post(loginApi, null, params).then(response => {
        if (response.code === 200) {
          ElNotification.success({ title: "登录成功", message: response.msg })
          const token = response.result[0].token
          localStorage.setItem("token", token)
          this.isAuth = true
          this.showLoginManageDialog = false
          this.searchMyShelf()
        } else {
          throw response.msg
        }
      }).catch(error => {
        ElNotification.error({ title: "登录失败", message: error })
      })
    },

    register() {
      const registerApi = '/register'
      const params = this.loginForm
      post(registerApi, null, params).then(response => {
        if (response.code === 200) {
          ElMessage.success({ title: "注册成功" })
        } else {
          throw response.msg
        }
      }).catch(error => {
        ElMessage.error({ title: "注册失败", message: error })
      })
    },

    cancel() {
      this.showLoginDialog = false
      this.loginForm = {
        username: '',
        email: '',
        password: ''
      }
    },

    logout() {
      this.loginForm = {
        username: '',
        email: '',
        password: ''
      }
      this.userInfo = { }
      this.isAuth = false
      localStorage.clear('token')
      ElNotification.info({
        title: "退出成功",
        message: "期待下次再来"
      })
    },

    showUserManage() {
      const token = localStorage.getItem('token')
      const userApi = '/user/searchUser'
      const params = { token: token }
      if (!token.length) {
        ElMessage.error("请登录后使用")
        this.showLoginDialog = true
      } else {
        get(userApi, params).then(response => {
          if (response.code === 200) {
            ElMessage.success("查询成功")
            this.userData = response.result[0]
            this.showUserManageDialog = true
          } else {
            throw response.msg
          }
        }).catch(error => {
          ElMessage.error(error)
        })
      }
    },

    searchNovel() {
      if (sessionStorage.getItem("token") === '') {
        ElMessage.error('请登录后使用')
      }
      const novelApi = '/novel/search'
      const params = { q: this.search }
      get(novelApi, params).then(response => {
        this.searchResult = response.result
        this.isSearchResult = true
      })
    },

    backToShelf() {
      this.isSearchResult = false;
      this.searchResult = [];
    },

    searchMyShelf() {
      const novelApi = "/novel/searchMyShelf"
      const params = { token: this.token }
      get(novelApi, params).then(response => {
        if (response.code === 200) {
          this.novelList = response.result
        } else {
          throw response.msg
        }
      }).catch(error => {
        console.log(error)
      })
    },

    searchChapter() {
      const chapterApi = "/chapter/search"
    }
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

    .novels-wrapper {
      flex: 1;
      overflow-x: hidden;
      overflow-y: scroll;

      .wrapper {
        display: grid;
        grid-template-columns: repeat(auto-fill, 380px);
        justify-content: space-around;
        grid-gap: 10px;

        .novel {
          user-select: none;
          display: flex;
          cursor: pointer;
          margin-bottom: 18px;
          padding: 24px 24px;
          width: 360px;
          flex-direction: row;
          justify-content: space-around;

          .info {
            position: relative;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-items: start;
            height: 112px;
            margin-left: 20px;
            flex: 1;
          }
        }
      }
    }
  }
}

</style>
