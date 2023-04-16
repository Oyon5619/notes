<template>
  <div class="show-container">
    <header class="header">
      展示错题
      <button class="edit-btn">
        <router-link to="/edit" style="text-decoration: none; color: #111;">返回添加</router-link>
      </button>
      <button class="edit-btn" @click="handleTest">
        testing
      </button>
    </header>

    <!-- 竖型导航菜单 -->
    <div class="process-bar">
      <el-menu background-color="#eee" text-color="#330999">
        <el-menu-item @click="goAuchor('#content')">题目内容</el-menu-item>
        <el-menu-item @click="goAuchor('#answer')">我的答案</el-menu-item>
        <el-menu-item @click="goAuchor('#standard')">标准答案</el-menu-item>
        <el-menu-item @click="goAuchor('#analysis')">分析总结</el-menu-item>
      </el-menu>
    </div>

    <!-- 展示题目内容、答案及标准答案以及分析总结 -->
    <div class="main">
      <div class="top">
        <h1 style="color: #005; font-size: 45px;" id="title">{{ note.title }}</h1>
        <div class=""></div>
      </div>
      <div>
        <h1 style="color: #751;" id="subject">题目内容</h1>
        <el-card>
          <div v-html="note.subject"></div>
        </el-card>
      </div>
      <div>
        <h1 style="color: #f51;" id="respond">我的答案</h1>
        <el-card>
          <div v-html="note.respond"></div>
        </el-card>
      </div>
      <div>
        <h1 style="color: #f8f;" id="answer">标准答案</h1>
        <el-card>
          <div v-html="note.answer"></div>
        </el-card>
      </div>
      <div>
        <h1 style="color: #059;" id="summary">分析总结</h1>
        <el-card>
          <div v-html="note.summary"></div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import { testApi } from '@/api/imgApi'

export default {
  components: {
    
  },
  data() {
    return {
      note: {
        title: '题目标题',
        priority: '',
        group: '',
        updateTime: '',
        promulgator: '',
        subject: '',
        respond: '',
        answer: '',
        summary: ''
      }
    }
  },
  mounted() {
    const curNotes = this.$store.getters.getCurNotes
    if (curNotes) {
      this.note.title = curNotes.notesTitle
      this.note.priority = curNotes.priority
      this.note.group = curNotes.notesGroup
      this.note.promulgator = curNotes.promulgator
      this.note.subject = curNotes.subject
      this.note.respond = curNotes.respond
      this.note.answer = curNotes.answer
      this.note.summary = curNotes.summary
      this.note.updateTime = curNotes.updateTime
    }
  },
  methods: {
    // 导航栏菜单项实现锚点定位
    goAuchor(id) {
      console.log(id)
      var auchor = this.$el.querySelector(id)
      console.log(auchor.offsetTop)
      console.log(auchor.offsetHeight)

      var mainDiv = document.querySelector('.main')
      mainDiv.scrollTop = auchor.offsetTop - 3 * auchor.offsetHeight
    },
    handleTest() {
      testApi().then((res) => {
        console.log(res)
      })
    }
  }
}
</script>

<style scoped>

.show-container {
  position: relative;
  width: 100%;
  height: 100%;
}

.header {
  padding: 15px;
  font-size: 40px;
  color: #eee;
  background: rgb(0, 113, 72);
}

.edit-btn {
  float: right;
  margin-right: 40px;
  font-size: 20px;
  padding: 5px;
}

.main {
  padding: 20px;
  height: 100vh;
  background: #eee;
  width: 70%;
  margin: 0 auto;
  overflow: auto;
}

li.el-menu-item {
  font-weight: bold;
  font-size: 20px;
}

.el-card {
  padding: 20px;
  margin-top: 10px;
  font-size: 25px;
  height: auto;
}

.el-card > div {
  overflow-y: auto;
}

.main > div {
  margin-left: 30px;
  margin-right: 30px;
  margin-bottom: 80px;
}

.process-bar {
  width: 14%;
  margin-top: 30px;
  height: 300px; 
  position: absolute;
}

</style>