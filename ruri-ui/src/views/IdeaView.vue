<template>
  <div class="idea__view">
    <div class="idea-page">
      <div class="banner-box">
        <div class="item item-1">
          <div>
            <div class="banner-title">
              记录下梦中零碎的灵感！
            </div>
          </div>
          <el-button type="default" class="button" :icon="EditPen" @click="createIdea">
            创建灵感
          </el-button>
        </div>
        <div class="item item-2"></div>
        <div class="item item-3"></div>
      </div>
      <div class="title">
        <div class="tip-title">
          <span class="title-text">
            灵感列表
          </span>
        </div>
      </div>
      <div class="idea-table">
        <el-table :data="ideaList" style="width: 100%" @row-click="editIdea">
          <el-table-column width="55" v-if="ideaList.length <= 0"/>
          <el-table-column type="selection" width="55" v-else />
          <el-table-column prop="title" label="标题" width="100" />
          <el-table-column prop="content" label="内容" width="542" />
          <el-table-column prop="count" label="总字数" width="200" />
          <el-table-column prop="createAt" label="最近修改时间" width="180" />

          <template #empty>
            <el-empty description="空空如也" :image-size="50" />
          </template>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import {EditPen} from "@element-plus/icons-vue";
import axios from "axios";

export default {
  computed: {
    EditPen() {
      return EditPen
    }
  },

  data() {
    return {
      token: '',

      ideaList: [ ],
    }
  },

  created() {
    const token = localStorage.getItem('token') || ''

    if (token !== '') {
      this.token = token
      this.getUserInfo()
      this.getIdeaList()
    } else {
      this.showAuthPage()
    }
  },

  methods: {
    showAuthPage() {
      this.$router.push('/auth')
    },

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

    getIdeaList() {
      const token = this.token
      const response = axios.get('http://localhost:8080/api/idea/searchShelf', {params: {token: token}})
      response.then(response => {
        const data = response.data
        const result = data.result

        result.forEach((item) => {
          item.count = item.content.length
        })

        this.ideaList = result
      })
    },

    editIdea(row) {
      this.$router.push('/create?id=' + row.ideaID)
    },

    createIdea() {
      this.$router.push('/create')
    }
  }
}
</script>

<style scoped>
.idea__view {
  flex: 1;
  position: relative;
}

.idea-page {
  margin-top: 80px;
  padding-top: 16px;

  .banner-box {
    display: flex;
    flex-direction: row;
    margin: 0 40px 33px 40px;

    .item {
      flex: 1;
      height: 212px;
    }

    .item-1 {
      background-color: rgba(245, 150, 170, .1);
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

      .button {
        width: 147px;
        height: 50px;
        border-radius: 10px;

        background-color: rgba(245, 150, 170, .8);
        color: white;
        border: none;
      }
    }

    .item-2 {
      background-color: rgba(245, 150, 170, .2);
    }

    .item-3 {
      background-color: rgba(245, 150, 170, .3);
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
      background: linear-gradient(90deg, #F596AAFF 0%, rgba(245, 150, 170, 0) 100%);
    }
  }

  .idea-table {
    margin: 0 40px 0 40px;
  }
}

</style>