import { defineStore } from 'pinia'

export const useStore = defineStore('store', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        isAuth: false,
        userInfo: { },
        bookInfo: { },
        bookList: [ ],
        currentChapter: { },
        userNS: 'default',
    }),
    actions: {
        updateToken(token) {
            this.token = token;
        },
        updateUserNS(val) {
            this.userNS = val;
        }
    },
})