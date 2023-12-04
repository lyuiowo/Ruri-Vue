import { defineStore } from 'pinia'

export const useStore = defineStore('store', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        userInfo: {},
    }),
    actions: {
        updateToken(token) {
            this.token = token;
        },
    },
})