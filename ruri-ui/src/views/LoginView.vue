<template>
    <div>
        <div>
            <label for="email">邮箱</label>
            <input type="text" name="email" id="email" v-model="email" />
        </div>
        <div>
            <label for="password">密码</label>
            <input type="text" name="password" id="password" v-model="password" />
        </div>
        <div>
            <button type="submit" @click="login">登录</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            email: "",
            password: "",
        }
    },

    methods: {
        login() {
            console.log(this.email);
            console.log(this.password);
            // return;

            axios.post(
                'http://localhost:8080/api/login',
                null,
                {
                    params: {
                        email: this.email,
                        password: this.password
                    }
                }
            ).then(response => {
                console.log(response.data);
                if (response.data.code === 200) {
                    let token = response.data.result[0].token;
                    localStorage.setItem("token", token);
                    console.log(localStorage.getItem("token"));
                } else {
                    throw new Error(response.data.msg);
                }

            }).catch(error => {
                console.error(error);
            })
        }
    }
}
</script>

<style lang="">
    
</style>