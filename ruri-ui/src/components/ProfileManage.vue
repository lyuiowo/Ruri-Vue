<template>
  <el-dialog>
    <template #header>
      <div class="custom-title">
        {{ isChangePassword ? '修改密码' : '修改个人资料' }}
        <span class="right" @click="isChangePassword =! isChangePassword">
        {{ isChangePassword ? '修改资料' : '修改密码' }}
      </span>
      </div>
    </template>
    <el-form :rules="rules.profile" :model="profile" ref="profile" label-position="top" v-if="!isChangePassword">
      <el-form-item label="用 户 名" prop="username">
        <el-input type="text" placeholder="请输入用户名" v-model="profile.username"/>
      </el-form-item>
      <el-form-item label="登录邮箱" prop="email">
        <el-input type="text" placeholder="请输入用户名" v-model="profile.email"/>
      </el-form-item>
    </el-form>
    <el-form :rules="rules.password" :model="password" ref="password" label-position="top" v-else>
      <el-form-item label="旧 密 码" prop="old">
        <el-input type="password" placeholder="请输入旧密码"  v-model="password.old" show-password />
      </el-form-item>
      <el-form-item label="新 密 码" prop="new">
        <el-input type="password" placeholder="请输入新密码" v-model="password.new" show-password />
      </el-form-item>
      <el-form-item label="确认密码" prop="confirm">
        <el-input type="password" placeholder="再次输入新密码" v-model="password.confirm" show-password />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="btn-ground">
        <el-button type="default">取消</el-button>
        <el-button type="primary" @click="handleChange">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import axios from "axios";

export default {

  props: {
    profile: {
      type: Object
    }
  },

  data() {
    return {
      isChangePassword: false,

      password: {
        old: '',
        new: '',
        confirm: ''
      },

      rules: {
        profile: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ],
        },

        password: {
          old: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
          new: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
          confirm: [
            { required: true, message: '请再次输入新密码', trigger: 'blur' },
            { validator: this.checkConfirmPassword, trigger: 'blur' }
          ]
        }
      }
    }
  },

  methods: {
    checkConfirmPassword(rule, value, callback) {
      if (value !== this.password.new) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    },

    async editProfile() {
      const form = await this.$refs.profile.validate()
      if (form) {
        const token = localStorage.getItem('token')
        const profile = this.profile
        profile.token = token
        const response = axios.post('http://localhost:8080/api/user/update', null, profile)
      }
    },

    async changePassword() {
      const form = await this.$refs.password.validate()
      if (form) {
        const token = localStorage.getItem('token')
        const password = this.password
        password.token = token

        // const response = axios.post('http://localhost:8080/api/change', null, password)

        console.log(password)
      }
    },

    handleChange() {
      if (this.isChangePassword) {
        this.changePassword()
      } else {
        this.editProfile()
      }
    }
  }
}
</script>

<style scoped>
.custom-title {
  font-size: 20px;
  font-weight: 800;
  display: flex;
  justify-content: space-between;

  .right {
    text-align: right;
    padding-right: 12px;
    cursor: pointer;
    font-size: 14px;
    font-weight: normal;
  }
}
</style>