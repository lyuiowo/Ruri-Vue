<script>
import axios from 'axios'

export default {
  data() {
    return {
      email: '',
      password: ''
    }
  },

  methods: {
    login(event) {
      event.preventDefault()

      console.log(this.email)
      console.log(this.password)
      // return;

      const newUrl = window.location.pathname
      history.pushState(null, '', newUrl)

      axios
        .post('http://localhost:8080/api/login', null, {
          params: {
            email: this.email,
            password: this.password
          }
        })
        .then((response) => {
          console.log(response.data)
          if (response.data.code === 200) {
            let token = response.data.result[0].token
            localStorage.setItem('token', token)
            console.log(localStorage.getItem('token'))
            this.$router.push('/')
          } else {
            throw new Error(response.data.msg)
          }
        })
        .catch((error) => {
          console.error(error)
        })
    }
  }
}
</script>

<template>
  <main class="main">
    <div class="inner-column compressed_column">
      <div class="user-login-page">
        <div class="header-widget">
          <h2>欢迎回来！</h2>
        </div>
        <div class="padded">
          <div class="login-widget">
            <form class="form" @submit="login">
              <div class="input-row">
                <label for="email">
                  <div class="label">邮箱</div>
                  <input
                    v-model="email"
                    type="text"
                    name="email"
                    id="email"
                    placeholder="必填项"
                    required="required"
                    autofocus
                  />
                </label>
              </div>
              <div class="input-row">
                <label for="email">
                  <div class="label">密码</div>
                  <input
                    v-model="password"
                    type="text"
                    name="email"
                    id="email"
                    placeholder="必填项"
                    required="required"
                    autofocus
                  />
                </label>
              </div>
              <div class="buttons">
                <button class="button login">登录</button>
                <button class="button register">
                  <RouterLink to="/register">注册</RouterLink>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
.inner-column.compressed_column {
  max-width: 500px;
}

.inner-column {
  margin: 20px auto;
  border: 1px solid #d0d0d0;
}

.user-login-page .header-widget {
  padding: 0 40px;
  min-height: 80px;
  border-bottom: 1px solid #dadada;
  display: flex;
  align-items: center;
}

.user-login-page .header-widget h2 {
  padding: 20px 0;
  font-weight: bold;
}

.padded {
  padding: 30px 40px;
}

.form {
  display: block;
  margin-top: 0;
}

.form .input-row {
  margin-bottom: 20px;
}

.input-row label {
  cursor: default;
}

.form .label {
  font-weight: bold;
  color: #434343;
}

.form input[type='text'] {
  padding: 8px;
  border: 2px solid #cdcdcd;
  border-radius: 2px;
  background-color: white;
  box-sizing: border-box;
  color: #222222;
  width: 100%;
  margin: 6px 0;
}

.form .buttons {
  margin-top: 20px;
  color: #858585;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.button {
  flex: 1;
  margin: 0 20px;
  cursor: pointer;
  border-radius: 3px;
  text-align: center;
  box-sizing: border-box;
  font-size: 14px;
  text-decoration: none;
  padding: 0 10px;
  box-shadow: inset 0 0 1px rgba(255, 255, 255, 0.21);
  height: 35px;
  font-weight: bold;
  line-height: 30px;
}

.button.login {
  border: 1px solid rgba(0, 0, 0, 0.1);
  background-color: #ff2449;
  color: white;
}

.button.register {
  border: 1px solid rgba(255, 36, 73, 0.6);
  background-color: transparent;
}

.button.register a {
  color: #ff2449;
}
</style>
