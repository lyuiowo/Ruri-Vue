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
<script setup>
import { Operation } from "@element-plus/icons-vue";
</script>