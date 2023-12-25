<template>
  <div class="about__view">
    <div class="about-page">
      <div class="about-inner-wrapper">
        <div class="about-sidebar">
          <div class="avatar">
            <img :src="avatar" alt="">
          </div>
          <div class="profile">
            <div class="username">
              {{ profile.username }}
            </div>
            <div class="email">
              {{ profile.email }}
            </div>
          </div>
          <div class="edit-btn">
            <el-button size="default" type="default" @click="showProfileEdit =! showProfileEdit">修改个人资料</el-button>
          </div>
        </div>
        <div class="about-main">
          <div class="title">
            我的书架
          </div>
          <div class="card-list">
            <div class="card-item" v-for="(item) in novelList" :key="item.novelID">
              <div class="novel-title">
                <span>
                  {{ item.name }}
                </span>

                <div class="btn-ground">
                  <el-button type="primary" link :icon="EditPen" @click="editNovel(item)">编辑</el-button>
                  <el-button type="danger" link :icon="Delete" @click="deleteNovel(item.novelID)">删除</el-button>
                </div>
              </div>
              <div class="novel-desc">
                {{ item. description }}
              </div>
              <div class="novel-chapter">
                总章节：{{ item. totalChapterNum }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <ProfileManage v-model="showProfileEdit" :profile="profile"/>
</template>

<script>
import ActiveGraph from '@/components/ActiveGraph.vue'
import axios from "axios";
import {Delete, EditPen} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";
import ProfileManage from "@/components/ProfileManage.vue";

export default {
  computed: {
    Delete() {
      return Delete
    },
    EditPen() {
      return EditPen
    }
  },
  components: {
    ProfileManage,
    EditPen,
    ActiveGraph
  },

  data() {
    return {
      token: '',
      avatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",

      novelList: [ ],

      showProfileEdit: false,
      profile: {
        userID: '',
        username: '',
        email: ''
      },

      showNovelEdit: true,
      novelForm: {
        name: '',
        desc: ''
      }
    }
  },

  created() {
    const token = localStorage.getItem('token') || ''
    if (token !== '') {
      this.token = token
      this.getUserInfo()
      this.getNovelList()
    } else {
      this.showAuthPage()
    }
  },

  methods: {
    showAuthPage() {
      this.$router.push('/auth')
    },

    showEditProfileDialog() {

    },

    showEditNovelDialog() {

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
          this.profile.userID = data.result[0].userID
          this.profile.username = data.result[0].username
          this.profile.email = data.result[0].email
        } else {
          localStorage.clear('token')
          this.showAuthPage()
        }
      })
    },

    getNovelList() {
      const token = this.token
      const response = axios.get('http://localhost:8080/api/novel/searchShelf', {params: {token: token}})
      response.then(response => {
        const data = response.data
        console.log(data)
        this.novelList = data.result
      })
    },

    editNovel(novel) {
      console.log(novel)
      const token = this.token
      const novelForm = this.novelForm
      novelForm.id = novel.novelID
      const response = axios.post('http://localhost:8080/api/novel/update', null, { params: novelForm })
    },

    deleteNovel(nid) {
      console.log(nid)
      const token = this.token
      ElMessageBox.confirm(
          '该操作会导致未保存的内容丢失，请确认保存后再进行操作',
          '警告',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).then(() => {
        ElMessage.success("成功")
        const response = axios.post('http://localhost:8080/api/novel/delete', null, {params: {id: nid, token: token }})
        response.then(
            this.getNovelList
        )
      }).catch(() => {
        ElMessage.info("取消")
      })
    }
  }
}
</script>

<style scoped>
.about__view {
  flex: 1;
  position: relative;
}

.about-page {
  margin-top: 80px;
  margin-left: 72px;
  margin-right: 72px;

  padding-top: 16px;
  padding-left: 32px;
  padding-right: 32px;
}

.about-inner-wrapper {
  display: flex;
  flex-direction: row;
}

.about-sidebar {
  flex: 0 0 118px;
}

.avatar {
  width: 118px;
  height: 118px;
  margin-bottom: 16px;
}

.avatar > img {
  width: 100%;
  height: 100%;
}

.profile {
  margin: 16px 0;
}

.username {
  font-size: 24px;
  font-weight: 600;
}

.edit-btn {
  width: 100%;
}

.about-main {
  flex: 2;
  margin-left: 24px;
}

.title {
  font-size: 18px;
}

.card-list {
  margin-top: 16px;

  display: flex;
  flex-direction: row;
  flex-wrap: wrap;

  .card-item {
    flex: 0 0 48%;
    margin-bottom: 16px;
    margin-right: 16px;
    height: 160px;
    box-sizing: border-box;
    word-wrap:break-word;
    overflow: hidden;
    border-radius: 8px;
    padding: 16px;
    border: #d0d7de 1px solid;
    position: relative;
  }

  .novel-title {
    font-size: 18px;
    color: #0969da;
    cursor: pointer;

    display: flex;
    justify-content: space-between;


    .btn-ground {
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
    }
  }

  .novel-chapter {
    position: absolute;
    bottom: 15px;
    font-size: 14px;
  }
}
</style>