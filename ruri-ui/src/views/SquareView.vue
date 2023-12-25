<template>
  <div class="square__view">
    <div class="square-page">
      <div class="square-position">
        <el-form class="search">
          <el-form-item>
            <el-input class="search-input" :prefix-icon="Search" placeholder="请输入小说名称" v-model="search" @keydown.enter></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="banner-box">
        <div class="item item-1">
          <div>
            <div class="banner-title">
              梦境广场
            </div>
          </div>
          <div>
            <div class="banner-sub">
              自由探索～
            </div>
          </div>
        </div>
        <div class="item item-2"></div>
        <div class="item item-3"></div>
      </div>
      <div class="title">
        <div class="tip-title">
          <span class="title-text">
            图书列表
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Search} from "@element-plus/icons-vue";
import axios from "axios";

export default {
  computed: {
    Search() {
      return Search
    }
  },

  components: {Search},

  data() {
    return {
      token: '',

      search: '',
      searchResult: [],

      novelList: []
    }
  },

  created() {
    const token = localStorage.getItem('token') || ''

    if (token !== '') {
      this.token = token
      this.getUserInfo()
      this.getAllNovel()
      this.getAllIdea()
    } else {
      this.showAuthPage()
    }
  },

  methods: {
    getUserInfo() {
      const token = this.token
      const response = axios.get('http://localhost:8080/api/user/info', {params: {token: token}})
      response.then(response => {
        const data = response.data
        if (data.code === 200) {
          const avatar = data.result[0].avatar
          if (avatar !== '') {
            this.avatar = avatar
          }
          this.username = data.result[0].username
        } else {
          localStorage.clear('token')
          this.showAuthPage()
        }
      })
    },

    getAllNovel() {
      const response = axios.get('http://localhost:8080/api/novel/all')
      response.then(response => {
        const data = response.data
        this.novelList = data.result
      })
    },

    getAllIdea() {
      const response = axios.get('http://localhost:8080/api/idea/all')
      response.then(response => {
        const data = response.data
        console.log(data.result)
      })
    }
  }
}
</script>

<style>
.square__view {
  flex: 1;
  position: relative;
}

.square-page {
  margin-top: 80px;
  padding-top: 16px;

  .square-position {
    position: absolute;
    top: 24px;
    left: 40px;
  }

  .search-input {
    width: 520px;
    height: 30px;
    border: none;

    > .el-input__wrapper {
      box-shadow: 0 0 1px rgba(122, 128, 135, 0.6);
    }

    > .el-input__wrapper:hover {
      box-shadow: 0 0 1px rgba(122, 128, 135, 1);
    }
  }

  .banner-box {
    display: flex;
    flex-direction: row;
    margin: 0 40px 33px 40px;

    .item {
      flex: 1;
      height: 212px;
    }

    .item-1 {
      background-color: rgba(249, 191, 69, .1);
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      padding: 30px 0 30px 36px;
      border-radius: 10px 0 0 10px;

      .banner-title {
        color: #3e5870;
        font-size: 22px;
        font-weight: 700;
        margin-bottom: 7px;
        white-space: nowrap;
        position: absolute;
      }

      .banner-sub {
        color: #3e5870;
        font-size: 16px;
        white-space: nowrap;
      }
    }

    .item-2 {
      background-color: rgba(249, 191, 69, .2);
    }

    .item-3 {
      background-color: rgba(249, 191, 69, .3);
      border-radius: 0 10px 10px 0;
    }
  }

  .title {
    margin: 0 0 13px 40px;

    .tip-title {
      position: relative;
      padding-left: 5px;

      .title-text {
        position: relative;
        font-size: 18px;
        color: #262626;
        font-weight: 600;
      }
    }

    .tip-title::before {
      position: absolute;
      content: "";
      width: 79px;
      height: 13px;
      bottom: 5px;
      left: 0;
      z-index: 0;
      opacity: .6;
      background: linear-gradient(90deg, #F9BF45FF 0%, rgba(249, 191, 69, 0) 100%);
    }
  }
}

</style>