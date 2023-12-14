<template>
  <div class="idea__view">
    <div class="page-header">
      <div class="user-profile">
        <span class="user-name">{{ username }}</span>
        <el-avatar class="user-avatar" :size="'small'" :src="avatar"/>
      </div>
    </div>
    <div class="idea-page">
      <div class="banner-box">
        <div class="item item-1">
          <div>
            <div class="banner-title">
              记录下梦中零碎的灵感！
            </div>
          </div>
          <el-button type="default" class="button" :icon="EditPen">
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
    </div>
  </div>
</template>

<script>
import {EditPen} from "@element-plus/icons-vue";

export default {
  computed: {
    EditPen() {
      return EditPen
    }
  },
  data() {
    return {
      token: '',
      username: '你好',
      avatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
    }
  },

  methods: {

  }
}
</script>

<style scoped>
.idea__view {
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

.idea-page {
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
}

</style>