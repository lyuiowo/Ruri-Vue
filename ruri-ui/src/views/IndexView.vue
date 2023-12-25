<template>
  <div class="index__view">
    <div class="index-page">
      <div class="index-position">
        <el-button class="create-btn" :icon="EditPen" @click="showAddDialog =! showAddDialog">
          创建小说
        </el-button>
      </div>
      <div class="banner-box">
        <div class="item item-1">
          <div>
            <div class="banner-title">
              开始描绘你梦想中的世界吧！
            </div>
          </div>
          <el-button type="default" class="button" :icon="EditPen" @click="showAddDialog =! showAddDialog">
            创建小说
          </el-button>
        </div>
        <div class="item item-2"></div>
        <div class="item item-3"></div>
      </div>
      <div class="title">
        <div class="tip-title">
          <span class="title-text">
            小说列表
          </span>
        </div>
      </div>
      <div class="novel-table">
        <el-table :data="novelList" :border="false" style="width: 100%" @row-click="editNovel">
          <el-table-column width="55" v-if="novelList.length <= 0" />
          <el-table-column type="selection" width="55" v-else />
          <el-table-column prop="name" label="小说名" width="180" />
          <el-table-column prop="description" label="简介" />
          <el-table-column prop="totalChapterNum" label="总章节" width="130" />
          <el-table-column prop="updateAt" label="最近修改时间" width="180" />

          <template #empty>
            <el-empty description="空空如也" :image-size="50"/>
          </template>
        </el-table>
      </div>
    </div>
  </div>

  <el-dialog v-model="showAddDialog">
    <template #header>
      <div class="add-title">
        <h3>新增小说</h3>
      </div>
    </template>
    <div class="input-box">
      <el-form label-position="right" label-width="55px" :model="addForm">
        <el-form-item label="小说名">
          <el-input v-model="addForm.name" placeholder="请输入小说书名" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="addForm.desc" type="textarea" resize="none" :autosize="{ minRows: 5 }" placeholder="请输入简介" />
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <div class="btn">
        <el-button type="default">取 消</el-button>
        <el-button type="primary">保存</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import {EditPen} from "@element-plus/icons-vue";
import axios from "axios";
import moment from "moment";

export default {
  computed: {
    EditPen() {
      return EditPen
    },
  },

  data() {
    return {
      token: '',
      showAddDialog: false,

      addForm: {
        name: '',
        desc: ''
      },

      novelList: [],
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

    getNovelList() {
      const token = this.token
      const response = axios.get('http://localhost:8080/api/novel/searchShelf', {params: {token: token}})
      response.then(response => {
        const data = response.data
        const result = data.result
        result.forEach((item) => {
          item.createAt = moment(item.createAt).format('YYYY-MM-DD h:mm:ss');
          item.updateAt = moment(item.updateAt).format('YYYY-MM-DD h:mm:ss');
        })
        this.novelList = result
      })
    },

    editNovel(row) {
      const nid = row.novelID
      this.$router.push('/writer?nid=' + nid)
    },

    createNovel() {
      const novelForm = this.addForm
      novelForm.token = this.token
      const response = axios.post('http://localhost:8080/api/novel/create', null, {params: novelForm})
      response.then(response => {
        const data = response.data
        const result = data.result
      })
    }
  }
}
</script>

<style>
.index__view {
  flex: 1;
  position: relative;
}

.index-page {
  margin-top: 80px;
  padding-top: 16px;

  .create-btn {
    width: 130px;
    height: 36px;
    border: none;
    position: absolute;
    top: 24px;
    left: 40px;

    background-color: rgba(143, 119, 181, .8);
    color: white;
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
      background-color: rgba(143, 119, 181, .1);
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

        background-color: rgba(143, 119, 181, .8);
        color: white;
        border: none;
      }
    }

    .item-2 {
      background-color: rgba(143, 119, 181, .2);
    }

    .item-3 {
      background-color: rgba(143, 119, 181, .3);
      border-radius: 0 10px 10px 0;
    }
  }

  .title {
    margin: 0 40px 13px 40px;

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
      background: linear-gradient(90deg, #8F77B5FF 0%, rgba(143, 119, 181, 0) 100%);
    }
  }

  .novel-table {
    margin: 0 40px 0 40px;

    .el-table .cell {
      white-space: nowrap;
    }
  }
}

</style>