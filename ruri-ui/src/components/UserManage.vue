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
          <el-input v-model="userInfo.userID" disabled />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="userInfo.username" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userInfo.email" autocomplete="on"/>
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
import {Operation} from "@element-plus/icons-vue";
import {ElMessage, ElNotification} from "element-plus";
import mitt from "mitt";
import eventBus from "@/plugins/eventBus";

export default {
  name: 'UserManage',
  components: { Operation },

  props: {
    userData: {
      type: Object,
      default: () => ({
        userID: "",
        username: "",
        email: "",
      })
    }
  },

  data() {
    return {
      chapterList: [ ],
      userInfo: {
        userID: "",
        username: "",
        email: "",
      }
    }
  },

  watch: {
    userData: {
      immediate: true,
      handler(newValue) {
        this.userInfo = { ...newValue }
      }
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

    },
  }
}
</script>