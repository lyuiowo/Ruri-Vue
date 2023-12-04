<script>
import { RouterLink, RouterView } from 'vue-router'
import { Search, User } from '@element-plus/icons-vue'
import axios from 'axios'

export default {
  components: { Search, User, RouterView, RouterLink },

  data() {
    return {
      token: '',
      user: {
        name: ''
      },
      keyValue: ''
    }
  },

  mounted() {
    this.token = localStorage.getItem('token') || ''

    console.log('App.vue', this.token)
    this.searchUserInfo()
  },

  methods: {
    searchUserInfo() {
      if (this.token == null || this.token === '') {
        return
      }

      axios
        .get('http://localhost:8080/api/user/searchUser', {
          params: {
            token: this.token
          }
        })
        .then((response) => {
          console.log(response.data)
          if (response.data.code === 200) {
            this.user.name = response.data.result[0].username // 保存用户名
          } else {
            this.token = '' // 重置 token
            throw new Error(response.data.msg)
          }
        })
        .catch((error) => {
          console.error(error)
        })
    },

    searchNovel(event) {
      event.preventDefault()

      console.log(this.keyValue)

      this.$router.push('/search?q=' + this.keyValue)
    }
  }
}
</script>

<template>
  <header class="header-spacer">
    <div class="header-widget">
      <div class="primary-header">
        <h1 class="header-logo">
          <RouterLink to="/">Ruri Novel</RouterLink>
        </h1>
        <nav class="header-nav">
          <RouterLink to="/">首页</RouterLink>
          <RouterLink to="/">灵感库</RouterLink>
          <RouterLink to="/">创作中心</RouterLink>
          <RouterLink to="/about">关于</RouterLink>
        </nav>
        <form class="search" @submit="searchNovel">
          <input
            type="text"
            placeholder="搜索轻小说或作者"
            required="required"
            name="search"
            v-model="keyValue"
          />
          <button type="submit">
            <el-icon>
              <Search />
            </el-icon>
          </button>
        </form>
        <div class="user-widget">
          <div class="user-buttons" v-show="this.token == null || this.token === ''">
            <RouterLink to="/login" class="login">登录</RouterLink>
            <RouterLink to="/login" class="register">注册</RouterLink>
          </div>
          <div class="user-panel" v-show="this.token !== ''">
            <el-icon>
              <User />
            </el-icon>
            <RouterLink to="/my">
              {{ user.name }}
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
.header-spacer {
  height: 50px;
}

.header-widget {
  height: 50px;
}

.primary-header {
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0 20px 0 22px;
  box-shadow: rgba(0, 0, 0, 0.1) 0 1px 2px;
}

.header-logo {
  font-size: 18px;
  text-indent: 15px;
  font-weight: bold;
  position: relative;
  background-color: transparent;
}

.header-logo::after {
  content: '';
  display: block;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: rgba(169, 183, 222, 0.9);
  z-index: -2;
  position: absolute;
  top: -2.5px;
  left: -7.5px;
}

.header-logo::before {
  content: '';
  display: block;
  width: 15px;
  height: 15px;
  z-index: -1;
  border-radius: 50%;
  background-color: var(--color-background);
  position: absolute;
  top: -3px;
  left: -8px;
}

.header-logo a {
  color: #f5c9c3;
  position: relative;
  text-shadow: 1px 1px rgba(238, 203, 196, 0.8);
}

.header-nav {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-left: 10px;
}

.header-nav a {
  margin: 0 10px;
}

.search {
  flex: 2;
  min-width: 240px;
  max-width: 520px;
  margin: 0 10px 0 20px;
  border-radius: 3px;
  background-color: #f4f4f4;
  display: flex;
}

.search input {
  border: none;
  background-color: transparent;
  height: 30px;
  padding: 4px 0 4px 10px;
  flex: 1;
}

.search button {
  border: none;
  background-color: transparent;
  padding: 0 10px;
  cursor: pointer;
}

.user-widget {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.user-panel {
  display: flex;
  align-items: center;
}
</style>
