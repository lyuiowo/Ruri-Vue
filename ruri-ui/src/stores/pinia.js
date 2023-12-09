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
        updateBookList(list) {
            this.bookList = list;
        },
        updateUserInfo(user) {
            this.userInfo = {
                userID: user.userID,
                username: user.username,
                email: user.email
            }
        }
    },
})