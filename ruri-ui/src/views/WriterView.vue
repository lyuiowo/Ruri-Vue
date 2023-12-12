<template>
  <div class="editor-wrapper">
    <div class="editor-header">
      <div class="navbar">
        <el-tag class="nav-btn">
          <el-icon color="#ebebeb"> <Menu /> </el-icon>
        </el-tag>
      </div>
      <div class="info-box">
        <div class="info item">
          <div class="title">
            无标题
          </div>
          <div class="chapter-info">
            <el-icon> <EditPen /> </el-icon>
            字数统计: {{ count }} 字
          </div>
        </div>
        <div class="item" />
      </div>
      <div class="tools-bar">
        <el-button class="save-btn" @click="saveChapter(novelID)">
          <el-icon><CirclePlusFilled /></el-icon>
          <span>保存</span>
        </el-button>
        <el-button class="save-btn" @click="saveChapter(novelID)">
          <el-icon><Position /></el-icon>
          <span>发布</span>
        </el-button>
      </div>
    </div>
    <div class="editor-box">
      <div class="editor">
        <div class="editor-core-box">
          <div class="input-box">
            <el-input
              name="title"
              type="text"
              v-model="title"
              class="input"
              tabindex="1"
              placeholder="请输入章节标题"
            />
          </div>
          <div class="value-box">
            <el-input
              name="content"
              type="textarea"
              v-model="content"
              class="value"
              tabindex="2"
              placeholder="请输入章节正文。"
              resize="none"
              :autosize="{ minRows: 5 }"
            />
          </div>
        </div>
      </div>
      <div class="chapter-position">
        <div class="chapter-box">
          <div class="chapter-title-box">
            <div class="title">
              {{ novelTitle }}
            </div>
            <div class="descrption">每次修改都记得保存哦</div>
          </div>
          <div class="chapter-list-box">
            <el-collapse class="list-box" v-model="active">
              <el-collapse-item class="status-list" v-for="status in statusList">
                <template #title>
                  <div class="title-box">
                    {{ status.title }}
                  </div>
                </template>
                <div class="chapter-item" v-for="chapter in chapterList[status.status]" @click="editChapter(chapter.status, chapter.chapterID)">
                  {{ chapter.title }}
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {CirclePlusFilled, EditPen, Menu, Position} from '@element-plus/icons-vue'
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";
export default {
  components: {Position, CirclePlusFilled, EditPen, Menu},

  data() {
    return {
      count: 0,
      title: "",
      content: "",

      novelTitle: "",
      novelID: 0,
      chapterID: null,

      active: [],
      statusList: [ {title: '已保存', status: 0}, {title: "已发布", status: 1}, {title: "已删除", status: 2} ],
      chapterList: {
        0: [ ],
        1: [ ],
        2: [ ]
      },
    }
  },

  created() {
    const nid = this.$route.query.nid
    this.novelID = nid
    const cid = this.$route.query.cid || ''
    this.searchNovelTitle(nid)
    this.searchChapterList(nid)

    if (cid) {
      this.searchChapter(nid, cid)
    }

  },

  methods: {
    searchNovelTitle(nid) {
      axios.get("http://localhost:8080/api/novel/searchNovel", { params: { nid: nid } }).then(response => {
        const novel = response.data.result
        this.novelTitle = novel.name
      })
    },

    searchChapterList(nid) {
      axios.get("http://localhost:8080/api/chapter/search", { params: { nid: nid } }).then(response => {
        const chapterList = response.data.result

        chapterList.forEach((item) => {
          switch (item.status) {
            case 0:
              this.chapterList['0'].push(item)
              break
            case 1:
              this.chapterList['1'].push(item)
              break
            case 2:
              this.chapterList['2'].push(item)
              break
          }
        })
      })
    },

    searchChapter(nid, cid) {
      axios.get("http://localhost:8080/api/chapter/search", { params: { nid: nid, cid: cid } }).then(response => {
        const chapter = response.data.result
        if (chapter) {
          this.title = chapter.title
          this.content = chapter.content
        }
      })
    },

    editChapter(status, chapterID) {
      ElMessageBox.confirm(
          '该操作会导致未保存的内容丢失，请确认保存后再进行操作',
          '警告',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).then(() => {
        ElMessage.success("成功")
        const foundChapter = this.chapterList[status].find(chapter => chapter.chapterID === chapterID);
        if (foundChapter) {
          this.title = foundChapter.title
          this.content = foundChapter.content
          this.chapterID = chapterID
        }
      }).catch(() => {
        ElMessage.info("取消")
      })
    },

    saveChapter(nid) {
      if (this.chapterID !== null) {
        axios.post(
          "http://localhost:8080/api/chapter/update",
          null,
            { params: { id: this.chapterID, title: this.title, content: this.content, status: 0}}
        ).then(response => {
          console.log(response.data)
        })
      } else {
        axios.post(
            "http://localhost:8080/api/chapter/create",
            null,
            { params: { id: nid, title: this.title, content: this.content, status: 0 }}
        ).then(response => {
          console.log(response.data)
        })
      }
    }
  }
}
</script>

<style lang="stylus" scoped>
.editor-wrapper {
  background-color: #FAFBFC;
  width: 100%;
  height: 100vh;

  .editor-header {
    background-color: white;
    display: flex;
    height: 60px;
    width: 100%;
    border-bottom: 1px solid #ebebeb;
    align-items: center;

    .navbar {
      text-align: center;
      flex: 0 0 60px;
      height: 60px;
      cursor: pointer;

      .nav-btn {
        background-color: transparent;
        border: none;
        border-right: 1px solid #ebebeb;
        width: 100%;
        height: 100%;
      }
    }

    .info-box {
      flex: 1;
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;

      .item {
        flex: 1;
      }
    }

    .info {
      padding-left: 34px;

      .title {
        color: #262626;
        font-size: 16px;
        font-weight: 600;
        margin-bottom: 5px;
      }

      .chapter-info {
        color: #898a95;
        font-size: 12px;
        font-weight: 400;
      }
    }

    .tools-bar {
      padding-right: 40px;

      .save-btn {
        width: 98px;
        height: 32px;
        background: transparent;
        border-radius: 25px;
        margin-right: 12px;
      }

      .save-btn:hover {
        border: #ec7070 1px solid;
        color: #ec7070;
      }
    }
  }

  .editor-box {
    position: relative;
    width: 100%;
    height: calc(100vh - 60px);
    padding-left: 24px;
    display: flex;
    flex-direction: row;
  }

  .editor {
    flex: 1;
    padding-top: 31px;
    height: 100%;
    overflow: hidden;
    box-sizing: border-box;
    overflow-y: scroll;
    position: relative;

    .editor-core-box {
      width: 100%;
      padding: 90px 0;
      background: #fff;
      position: relative;
      min-height: 100%;
      border: 1px solid #ebebeb;

      .input-box {
        height: 42px;
        margin-bottom: 24px;
        width: 100%;
      }

      .input {
        height: 100%;
        padding-left: 90px;
        padding-right: 90px;
        width: 100%;
        color: #3e4155;
        font-size: 30px;
        font-weight: 400;

        :deep(.el-input__wrapper) {
          box-shadow: unset;
          border: none;
        }
      }

      .value-box {
        width: 100%;
        position: relative;
        min-height: 30px;
        word-break: break-all;
      }

      .value {
        min-height: 200px;
        padding-left: 90px;
        padding-right: 90px;
        width: 100%;
        font-size: 16px;
        font-weight: 400;
        color: #3e4155;

        :deep(.el-textarea__inner:hover),
        :deep(.el-textarea__inner) {
          border: none;
          box-shadow: unset;
        }
      }
    }
  }

  .chapter-position {
    padding-top: 31px;
    padding-right: 24px;
    flex: 0 0 424px;
    height: 100%;
    top: 0;
    right: 0;

    .chapter-box {
      display: flex;
      flex-direction: column;

      .chapter-title-box {
        padding-bottom: 16px;
      }

      .title {
        color: #262626;
        font-size: 18px;
        font-weight: 500;
        margin-bottom: 4px;
      }

      .descrption {
        color: #898a95;
        font-size: 12px;
        font-weight: 400;
      }

      .status-list {
        margin-bottom: 12px;
      }

      .status-list:last-child {
        margin-bottom: 0;
      }

      :deep(.el-collapse-item__header) {
        border-radius: 10px 10px 0 0;
      }

      .list-box {
        padding: 12px;

        .title-box {
          padding-left: 12px;
        }

        .chapter-item {
          padding-left: 12px;
          cursor: pointer;
        }
      }
    }
  }
}
</style>