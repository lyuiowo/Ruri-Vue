<template>
  <div class="auth-wrapper">
    <div class="auth-inner-wrapper" :class="isLogin ? 'login' : 'register'">
      <div class="auth-header">
        {{ isLogin ? "登录" : "注册" }}
        <span class="right" @click="isLogin =! isLogin">{{ isLogin ? "注册" : "登录" }}</span>
      </div>
      <div class="auth-box">
        <div class="input-box">
          <el-form :model="authForm" :rules="authRules" label-position="top" ref="authForm" status-icon>
            <el-form-item label="用户名" v-if="!isLogin" prop="username">
              <el-input v-model="authForm.username" type="text" placeholder="请输入邮箱"/>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="authForm.email" type="text" placeholder="请输入邮箱"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="authForm.password" type="password" show-password placeholder="请输入密码" @keydown.enter="auth"/>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div class="auth-footer">
        <div class="button">
          <el-button type="default" @click="isLogin =! isLogin">{{ isLogin ? "注 册" : "登 录" }}</el-button>
          <el-button type="primary" @click="auth">{{ isLogin ? "登 录" : "注 册" }}</el-button>
        </div>
      </div>
    </div>
    <div class="banner">
      <div class="item item-3"/>
      <div class="item item-2"/>
      <div class="item item-1"/>
      <div class="item item-2"/>
      <div class="item item-3"/>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {ElMessage} from "element-plus";

export default {
  data() {
    return {
      isLogin: true,
      authForm: {
        username: '',
        email: '',
        password: ''
      },
      authRules: {
        username: [
          { required: !this.isLogin, message: '请输入用户名', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
    }
  },

  methods: {
    back() {
      document.querySelector('.navigation-wrapper').style.display = 'block'
      this.$router.push('/')
    },

    async auth() {
      const form = await this.$refs.authForm.validate()
      if (form) {
        const authType = this.isLogin ? 'login' : 'register'
        try {
          const response = await axios.post(`http://localhost:8080/api/${authType}`, null, {params: this.authForm})
          const data = response.data
          if (data.code === 200) {
            const token = data.result.token
            localStorage.setItem('token', token)
            ElMessage.success(data.msg)

            document.querySelector('.navigation-wrapper').style.display = 'block'
            this.$router.push('/')
          } else {
            throw data.msg
          }
        } catch (error) {
          ElMessage.error(error)
        }
      }
    }
  }
}
</script>

<style scoped>
.auth-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 999;
  background: rgba(0, 0, 0, .2);
}

.banner {
  display: flex;
  flex-direction: row;
  height: 100%;

  .item {
    flex: 1;
    height: 100%;
  }

  .item-1 {
    background-color: rgba(143, 119, 181, .1);
  }

  .item-2 {
    background-color: rgba(249, 191, 69, .2);
  }

  .item-3 {
    background-color: rgba(245, 150, 170, .3);
  }
}

.auth-inner-wrapper.login {
  height: auto;
}

.auth-inner-wrapper.register {
  height: auto;
}

.auth-inner-wrapper {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 560px;
  z-index: 12500;
  background-color: white;

  .auth-header {
    padding-left: 50px;
    font-size: 24px;
    font-weight: 800;
    min-height: 50px;
    line-height: 50px;
    box-shadow: 0 0 1px #8e8e8e8e;

    .right {
      float: right;
      font-size: 16px;
      cursor: pointer;
      margin-right: 50px;
    }
  }

  .auth-box {
    padding: 20px 50px 10px;
  }

  .auth-footer {
    padding: 10px 50px 20px;

    .button {
      display: flex;
      flex-direction: row;
    }
  }
}
</style>