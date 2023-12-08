<template>
  <el-dialog>
    <template #header>
      <div class="custom-dialog-title">
        <span class="el-dialog__title">
          <el-icon>
            <Operation />
          </el-icon>
          小说管理
        </span>
      </div>
    </template>

    <div class="source-container table-container">
      <el-table :data="bookList" style="width: 100%">
        <el-table-column type="selection" width="30px" :selectable="isBookSelectable" />
        <el-table-column property="name" label="书名" min-width="100" sortable />
        <el-table-column property="updateAt" label="更新时间" sortable>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="default" @click="" type="default">编辑</el-button>
            <el-button size="default" @click="" type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-dialog>
</template>

<style scoped lang="stylus">
.source-container {
  padding: 0 10px;
}

.custom-dialog-title {
  display: flex;
  align-items: center;

  :deep(.el-dialog__title) {
    display: flex;
    align-items: center;
  }
}
</style>

<script>
import { Operation } from "@element-plus/icons-vue";
import { useStore } from '@/stores/pinia';
import axios from "axios";
import moment from "moment";

export default {
  name: "BookManage",

  components: { Operation },

  data() {
    return {
      showBookManage: false,
      bookList: [ ]
    }
  },

  setup() {
    const store = useStore()
    return {
      store: store,
    }
  },

  created() {
    this.searchMyBook(this.store.token)
  },

  methods: {
    isBookSelectable() {
      return true;
    },

    searchMyBook() {
      axios.get(
          'http://localhost:8080/api/novel/searchMyShelf',
          {
            params: {
              token: this.store.token
            }
          }
      ).then((response) => {
        // console.log(response.data)

        response.data.result.forEach((item) => {
          const updateAt = moment(item.updateAt).format('YYYY/MM/DD H:mm:ss')
          const createAt = moment(item.createAt).format('YYYY/MM/DD H:mm:ss')

          item.updateAt = updateAt
          item.createAt = createAt
        })

        this.bookList = response.data.result
      }).catch((error) => {

      })
    }
  }
}
</script>