<template>
  <el-dialog>
    <template #header>
      <div class="custom-title">
        编辑小说
      </div>
    </template>
    <el-form :rules="rules" :model="novelData" ref="profile" label-position="top">
      <el-form-item label="书 名" prop="username">
        <el-input type="text" placeholder="请输入小说标题" v-model="novelData.name"/>
      </el-form-item>
      <el-form-item label="简 介" prop="email">
        <el-input type="textarea" :autosize="{ minRows: 7, maxRows: 7 }" resize="none" placeholder="请输入小说简介" v-model="novelData.description"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="btn-ground">
        <el-button type="default" @click="">取消</el-button>
        <el-button type="primary" @click="saveNovel">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import axios from "axios";
import {ElMessage} from "element-plus";

export default {
  props: {
    novelData: {
      type: Object
    },
  },

  data() {
    return {
      rules: {

      }
    }
  },

  methods: {
    saveNovel() {
      const token = localStorage.getItem('token')
      const novelForm = this.novelData
      novelForm.id = novelForm.novelID
      const response = axios.post('http://localhost:8080/api/novel/update', null, { params: novelForm })
      response.then(response => {
        const data = response.data
        if (data === 200) {
          ElMessage.success(data.msg)
        } else {
          ElMessage.error(data.msg)
        }
      })
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
}
</style>