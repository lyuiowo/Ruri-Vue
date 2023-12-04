<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component"/>
    </keep-alive>
  </router-view>

  <el-dialog v-model="showLoginDialog" width="35%">
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

  <BookManage v-model="showBookManageDialog"/>
</template>

<script>
import axios from 'axios';
import { ElMessage, ElNotification } from "element-plus";
import { useStore } from './stores/pinia';
import eventBus from './plugins/eventBus';
import BookManage from "@/components/BookManage.vue";

export default {
  name: "App",
  components: { BookManage },

  data() {
    return {
      loginForm: {
        username: "",
        email: "",
        password: ""
      },

      showLoginDialog: true,
      isLogin: true,

      showBookManageDialog: false,
      showTagManageDialog: false,
    }
  },

  setup() {
    const store = useStore()
    return {
      store: store,
    }
  },

  beforeMount() {
    eventBus.on('showBookManage', () => {
      this.showBookManageDialog = true
    });
    eventBus.on('showTagManage', () => {
      this.showTagManageDialog = true
    });
    eventBus.on('showLogin', () => {
      this.showLoginDialog = true
    })
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
      axios.post(
        "http://localhost:8080/api/login",
        null,
        {
          params: {
            email: this.loginForm.email,
            password: this.loginForm.password
          }
        }
      ).then((response) => {
        console.log(response.data)
        if (response.data.code === 200) {
          let token = response.data.result[0].token

          this.store.updateToken(token)
          localStorage.setItem('token', token)

          ElNotification({
            title: '欢迎回来',
            message: response.data.msg,
            type: 'success',
          })

          this.showLoginDialog = false

          this.searchUserInfo()

        } else {
          throw new Error(response.data.msg)
        }
      }).catch((error) => {
        console.log(error)
        ElNotification({
          title: '登陆失败',
          message: error.message,
          type: 'error'
        })
      })
    },

    register() {
      axios.post(
          "http://localhost:8080/api/register",
          null,
          {
            params: {
              username: this.loginForm.username,
              email: this.loginForm.email,
              password: this.loginForm.password
            }
          }
      ).then((response) => {
        console.log(response.data)
        if (response.data.code === 200) {
          let token = response.data.result.token

          this.store.updateToken(token)
          localStorage.setItem('token', token)

          ElNotification({
            title: '注册成功',
            message: response.data.msg,
            type: 'success',
          })

          this.showLoginDialog = false

          this.searchUserInfo()

        } else {
          throw new Error(response.data.msg)
        }
      }).catch((error) => {
        console.log(error)
        ElNotification({
          title: '注册失败',
          message: error.message,
          type: 'error'
        })
      })
    },

    cancel() {
      this.showLoginDialog = false;
      this.loginForm = {
        username: "",
        password: "",
        email: ""
      };
    },

    searchUserInfo() {
      if (this.store.token !== '') {
        axios.get(
            "http://localhost:8080/api/user/searchUser",
            {
              params: {
                token: this.store.token
              }
            }
        ).then((response) => {
          console.log(response.data)

          this.store.userInfo = response.data.result[0]
          console.log(this.store.userInfo)

        }).catch((error) => {
          ElMessage.error('请登录后使用')
          this.showLoginDialog = true
        })
      }
    }
  }
}
</script>

<style>
.el-dialog .el-dialog__header {
  padding: 20px 40px 10px 20px;
}

.float-right {
  float: right;
}

.custom-dialog-title {
  .span-btn {
    display: inline-block;
    cursor: pointer;
    font-size: 15px;
    margin-right: 10px;
  }
}
</style>
