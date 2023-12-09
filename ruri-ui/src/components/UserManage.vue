<template>
  <el-dialog>
    <template #header>
      <div class="custom-dialog-title">
        <span class="el-dialog__title">
          <el-icon>
            <Operation />
          </el-icon>
          作者信息
        </span>
      </div>
    </template>

    <div class="source-container table-container">
      <el-form :model="userInfo" label-position="top">
        <el-form-item label="用户ID">
          <el-input v-model="store.userInfo.userID" disabled />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="store.userInfo.username" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="store.userInfo.email" autocomplete="on"/>
        </el-form-item>
      </el-form>
    </div>

    <template #footer>
      <span class="dialog-footer">
        <el-button size="default" @click="cancel">取 消</el-button>
        <el-button size="default" type="primary" @click="saveUserInfo">保 存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped lang="stylus">

</style>

<script>
import { useStore } from '@/stores/pinia'
import {Operation} from "@element-plus/icons-vue";
import axios from "axios";
import {ElMessage, ElNotification} from "element-plus";
import mitt from "mitt";
import eventBus from "@/plugins/eventBus";

export default {
  name: 'UserManage',
  components: { Operation },
  data() {
    return {
      chapterList: [ ],
      userInfo: { }
    }
  },

  setup() {
    const store = useStore()
    return {
      store: store
    }
  },

  methods: {
    isBookSelectable() {
      return true;
    },

    cancel() {
      eventBus.emit('closeUserManage')
    },

    saveUserInfo() {
      axios.post(
          "http://localhost:8080/api/user/update",
          null,
          {
            params: {
              userID: this.userInfo.userID,
              username: this.userInfo.username,
              email: this.userInfo.email
            }
          }
      ).then((response) => {
        // console.log(response.data)
        if (response.data.code === 200) {

          this.store.updateUserInfo(this.userInfo)
          ElMessage.success("修改成功")

        } else {
          throw new Error(response.data.msg)
        }
      }).catch((error) => {
        // console.log(error)
        ElMessage.error("修改失败")
      })
    },
  }
}
</script>