<template>
  <div class="square__view">
    <div class="page-header">
      <div class="user-profile">
        <span class="user-name">{{ username }}</span>
        <el-avatar class="user-avatar" :size="'small'" :src="avatar"/>
      </div>
    </div>
    <div class="square-page">
      <div class="square-position">
        <el-input class="search-input" :prefix-icon="Search" placeholder="请输入小说名称" v-model="search"></el-input>
      </div>
      <div class="banner-box">
        <div class="item item-1">
          <div>
            <div class="banner-title">
              梦境广场
            </div>
          </div>
          <div class="item"></div>
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

export default {
  computed: {
    Search() {
      return Search
    }
  },

  components: {Search},

  data() {
    return {
      search: ''
    }
  }
}
</script>

<style>
.square__view {
  flex: 1;
  display: flex;
  flex-direction: column;
  position: relative;
}

.page-header {
  height: 80px;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  padding-right: 40px;

  .user-profile {
    display: flex;
    align-items: center;

    .user-name {
      font-size: 14px;
      color: #7A8087;
    }

    .user-avatar {
      margin-left: 5px;
    }
  }
}

.square-page {
  padding-top: 16px;

  .search-input {
    width: 640px;
    height: 36px;
    position: absolute;
    top: 24px;
    left: 40px;
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