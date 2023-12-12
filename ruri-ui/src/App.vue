<template>
  <div class="index-wrapper">
    <div class="index-slider navigation-wrapper">
      <div class="navigation-inner-wrapper">
        <div class="navigation-title">
          清风写作
        </div>
        <div class="navigation-bars">
          <div class="slider-url-list">
            <el-tag class="slider-btn btn-component" :class="showPage === 0 ? 'active' : ''" @click="showNovelPage">
              <el-icon class="slider-btn-icon"> <Compass /> </el-icon>
              <span class="slider-btn-text">我的小说</span>
            </el-tag>
            <el-tag class="slider-btn btn-component" :class="showPage === 1 ? 'active' : ''" @click="showIdeaPage">
              <el-icon class="slider-btn-icon"> <Notebook /> </el-icon>
              <span class="slider-btn-text">我的设定集</span>
            </el-tag>
          </div>
          <div class="slider-line"></div>
          <div class="slider-url-list">
            <el-tag class="slider-btn btn-component" :class="showPage === 2 ? 'active' : ''" @click="showSquarePage">
              <el-icon class="slider-btn-icon"> <Management /> </el-icon>
              <span class="slider-btn-text">广场</span>
            </el-tag>
          </div>
          <div class="slider-line"></div>
          <div class="slider-url-list">
            <el-tag class="slider-btn btn-component" :class="showPage === 3 ? 'active' : ''" @click="showAboutPage">
              <el-icon class="slider-btn-icon"> <Warning /> </el-icon>
              <span class="slider-btn-text">关于</span>
            </el-tag>
          </div>
        </div>
      </div>
    </div>

    <router-view />
  </div>
</template>

<script>
import {Compass, Management, Notebook, Warning} from "@element-plus/icons-vue";

export default {
  components: {Warning, Management, Notebook, Compass},

  data() {
    return {
      showPage: 0
    }
  },

  watch: {
    $route(to, from) {
      switch (to.path) {
        case '/':
          this.showPage = 0
          break
        case '/idea':
          this.showPage = 1
          break
        case '/square':
          this.showPage = 2
          break
        case '/about':
          this.showPage = 3
          break
      }
    }
  },

  methods: {
    showNovelPage() {
      this.showPage = 0
      this.$router.push('/')
    },

    showIdeaPage() {
      this.showPage = 1
      this.$router.push('/idea')
    },

    showSquarePage() {
      this.showPage = 2
      this.$router.push('/square')
    },

    showAboutPage() {
      this.showPage = 3
      this.$router.push('/about')
    }
  }
}
</script>

<style lang="stylus" scoped>
.index-wrapper {
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: row;

  .index-slider {
    flex: 0 0 240px;
  }

  .navigation-wrapper {
    position: relative;
    box-sizing: border-box;

    .navigation-inner-wrapper {
      padding: 48px 16px 66px 16px;
      height: 100%;
      box-sizing: border-box;
    }

    .navigation-title {
      text-align: center;
      font-size: 24px;
      font-weight: 600;
    }

    .navigation-bars {
      padding-top: 20px;
      overflow-y: auto;
    }

    .slider-url-list {
      width: 100%;

      .btn-component {
        border: none;
        background-color: transparent;
        color: #898a95;
        width: 100%;
        height: 44px;
      }

      .slider-btn {
        padding-left: 22px;
        justify-content: start;
        cursor: pointer;
        margin-bottom: 16px;
      }

      .slider-btn:last-child {
        margin-bottom: 0;
      }

      .slider-btn-icon {
        margin-right: 12px;
      }

      .slider-btn-text {
        font-size: 14px;
        font-weight: 500;
      }

      .btn-component.active {
        background-color: #ec70701a;

        .slider-btn-icon {
          color: #ec7070;
        }
      }

      .btn-component:hover {
        color: #ec7070;
        border: none;
      }
    }

    .slider-line {
      margin: 16px 10px;
      height: 1px;
      background: #ececec;
      transition: all .4s;
    }
  }
}
</style>
