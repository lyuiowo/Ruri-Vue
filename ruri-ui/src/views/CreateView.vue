<template>
  <div class="create__view">
    <div class="create-page">
      <div class="create-header">
        <div class="header-box">
          <el-breadcrumb class="header__left" separator="/">
            <el-breadcrumb-item :to="{ path: '/idea' }">
              <span class="title-fir">
                我的灵感集
              </span>
            </el-breadcrumb-item>
            <el-breadcrumb-item>
              <span class="title-sub">
                {{ createForm.title ? createForm.title : '未命名灵感' }}
              </span>
            </el-breadcrumb-item>
          </el-breadcrumb>
          <div class="header__right">
            <div class="idea-btn">
              <el-button type="default" class="btn" round @click="back">返回</el-button>
              <el-button type="primary" class="btn" round @click="saveIdea">保存</el-button>
            </div>
          </div>
        </div>
      </div>

      <div class="novel-title-input-box">
        <div class="novel-title">
          <div class="title" v-if="!canInputTitle" :class="createForm.title ? '' : 'null'" @click="changeCanInputTitle">
            {{ createForm.title ? createForm.title : '未命名灵感' }}
          </div>
          <div class="title input-box" v-else>
            <el-input class="input" placeholder="例如：玄幻世界" :autofocus="true" @blur="changeCanInputTitle"
                      @change="changeCanInputTitle" v-model="createForm.title"/>
          </div>
        </div>
      </div>

      <div class="novel-desc-box">
        <div class="novel-desc-input-box">
          <div class="desc-title">
            <span class="">
              灵感设定
            </span>
            <span class="right" v-if="canInputContent">
              <el-button :icon="EditPen" type="text" @click="changeCanInputContent">保存</el-button>
            </span>
          </div>
          <div class="desc-show" v-if="!canInputContent">
            <div class="world-textarea" :class="createForm.content.length <= 0 ? 'null' : ''">
              <el-button type="text" @click="changeCanInputContent" v-if="createForm.content.length <= 0">
                <span class="text">
                  点击输入灵感设定
                </span>
              </el-button>

              <div class="desc-text" v-else @click="changeCanInputContent">
                {{ createForm.content }}
              </div>
            </div>
          </div>
          <div class="desc-show input-box" v-else>
            <div class="world-textarea">
              <el-input class="textarea" placeholder="请输入灵感内容" type="textarea" resize="none"
                        :autosize="{ minRows: 7, maxRows: 7 }" v-model="createForm.content" :autofocus="true"/>
            </div>
          </div>
        </div>
        <div class="slide-box">
          <div class="hidden-box">
            <div class="icon">
              <el-icon size="18" v-if="createForm.hidden">
                <View />
              </el-icon>
              <el-icon size="18" v-else>
                <Hide />
              </el-icon>
              <span class="text">
                {{ createForm.hidden ? '公开' : '私密'}}
              </span>
            </div>
            <div>
              <el-switch v-model="createForm.hidden" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {EditPen, Hide, View} from "@element-plus/icons-vue";
import axios from "axios";

export default {
  components: {View, Hide},
  computed: {
    EditPen() {
      return EditPen
    }
  },
  data() {
    return {
      token: '',
      ideaID: -1,
      createForm: {
        title: '',
        content: '',
        hidden: false
      },
      canInputTitle: false,
      canInputContent: false
    }
  },

  created() {
    const token = localStorage.getItem('token')

    if (token !== '') {
      this.token = token
    } else {
      this.showAuthPage()
    }

    const id = this.$route.query.id || -1

    if (id === -1) {

    } else {
      this.ideaID = id
      this.getIdeaID()
    }
  },

  methods: {
    changeCanInputTitle() {
      this.canInputTitle = !this.canInputTitle
    },

    changeCanInputContent() {
      this.canInputContent = !this.canInputContent
    },

    showAuthPage() {
      this.$router.push('/auth')
    },

    back() {
      this.$router.push('/idea')
    },

    saveIdea() {
      const token = this.token
      const ideaForm = this.createForm
      ideaForm.token = token
      const response = axios.post("http://localhost:8080/api/idea/create", null, {params: ideaForm})
      response.then(response => {
        const data = response.data
        console.log(data)
      })
    },

    getIdeaID() {
      const ideaID = this.ideaID
      const response = axios.get('http://localhost:8080/api/idea/search', {params: {ideaID: ideaID}})
      response.then(response => {
        const data = response.data.result
        console.log(data)
        this.createForm = {
          title: data.title,
          content: data.content,
          hidden: data.isHidden
        }
      })
    }
  }
}
</script>

<style scoped>
.create__view {
  flex: 1;
  height: 100vh;
  position: relative;
  background: #FAFAFA;
}

.create-page {
  margin-top: 80px;
  padding: 16px 40px 0;
  display: flex;
  flex-direction: column;
}

.create-header {
  .header-box {
    padding: 0 0 26px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  .header__left {
    flex: 1;
    font-size: 18px;
    padding: 12px 0 0;
  }

  .title-fir {
    color: #898a95;
    font-weight: 400;
  }

  .title-sub {
    color: #262626;
    font-weight: 500;
  }

  .header__right .btn {
    height: 45px;
  }
}

.novel-title-input-box .novel-title {
  color: #262626;
  font-size: 28px;
  font-weight: 700;
  position: relative;

  .title {
    cursor: pointer;
    width: 80%;
  }

  .title.null {
    color: #26262699;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADwAAAA8BAMAAADI0sRBAAAAG1BMVEVHcEz0cHD/b2//VVXsbm7rbm7vcHDtbm7scHCUot/1AAAACHRSTlMAGRAGitIyZox/W5oAAAE3SURBVDjLjZVBcsMgDEVpuQACT70F9wJ2btBFZ3wBd7rtTC/QO6Stjp1FHKHAB0fLPD+ECBLG5LCnROSn2cCYaI8APrCJJPzco0Q0d2npL1RE0PSZqogKpxqr5RcC4QQTjE5mnT1h7PeaiXr60sKht/a+enPt68k/tXEEqd9/v3TyIvXA/KGSl6lX5qxXeGBWeixPdGVWeiw2PjBr3RUbX6/4X7aegMxn2XoCMv9BfJP5TTCSz/KL6cn3uJY1BrLGQFYYyZQLQ3Ku+wXJXs58BDLAWqYgf+gAZHJyHUYgq9syApmidO8IZFIX+bOWvWqD723bfu5xUM3vvU9FS7jjFjxo4Hb7u/7wmB8ZPUeDy3TXbsyPPDQtGrmPD+y6di3XxZWPje3So4fKGHu60Qk/g3ZKRP5VqxcaO7bRrLciDwAAAABJRU5ErkJggg==) no-repeat;
    background-size: 24px 24px;
    background-position: left center;
    padding-left: 30px;
  }

  .title.input-box {
    border-radius: 8px;
    opacity: 1;
    background: #ffffff;
    padding: 16px 14px;
    width: 100%;
    box-sizing: border-box;
    overflow: hidden;
    position: relative;
    transition: all .4s;

    .input {
      height: 100%;
      width: 100%;
      font-size: 30px;
      font-weight: 800;
    }

    :deep(.el-input__wrapper) {
      box-shadow: unset;
      border: none;
    }
  }
}

.novel-desc-box {
  display: flex;
  justify-content: space-between;
  margin: 24px 0;

  .novel-desc-input-box {
    min-width: 250px;
    flex: 1;
    height: 444px;
    padding: 12px 24px 16px;
    border-radius: 12px;
    background: white;
    box-sizing: border-box;
    position: relative;

    .desc-title {
      position: relative;
      color: #262626;
      font-size: 18px;
      font-weight: 700;
      padding-left: 10px;

      .right {
        float: right;
      }
    }

    .desc-title::before {
      content: "";
      width: 4px;
      height: 15px;
      background: #ec7070;
      position: absolute;
      left: 0;
      top: 25%;
    }

    .desc-show {
      height: 380px;
      width: 100%;
      padding: 16px 0;

      .world-textarea.null {
        display: flex;
        align-items: center;
        justify-content: center;

      }

      .world-textarea {
        height: 100%;

        .desc-text {
          height: 100%;
        }
      }

      .world-textarea .text {
        color: #ec7070;
        font-weight: 700;
      }
    }

    .desc-show.input-box {
      margin-top: 16px;
      border-radius: 12px;
      height: 370px;
      width: 100%;
      border: .5px solid #f4f4f4;
      background: #fafafa;
    }

    :deep(.el-textarea__inner) {
      background: transparent;
    }

    :deep(.el-textarea__inner:hover),
    :deep(.el-textarea__inner) {
      border: none;
      box-shadow: unset;
    }
  }

  .slide-box {
    min-width: 0;
    flex: 0 0 360px;
    margin-left: 16px;
  }

  .hidden-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 16px;
    margin-bottom: 26px;
    background: white;
    height: 42px;
    border-radius: 12px;

    .text {
      margin-left: 8px;
    }

    .icon {
      display: flex;
      align-items: center;
    }
  }

  .tips-box {
    padding: 12px 16px;
    background: white;
    border-radius: 12px;
  }

  .tips-title {
    padding-left: 10px;
    box-sizing: border-box;
    position: relative;
  }

  .tips-title::before {
    content: "";
    width: 4px;
    height: 15px;
    background: #ec7070;
    position: absolute;
    left: 0;
    top: 25%;
  }

  .tips {
    padding-top: 12px;
  }
}
</style>
