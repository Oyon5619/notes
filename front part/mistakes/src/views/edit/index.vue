<template>
  <div class="edit-container">
    <header class="top">
      添加错题
      <button class="show-btn" @click="onSubmitNote">Add</button>
      <button class="show-btn">
        <router-link to="/show" style="text-decoration: none; color: #111;">返回查看</router-link>
      </button>
    </header>

    <div class="main">
      <div class="middle">
        错题标题:
        <el-input v-model="notes.notesTitle" placeholder="请输入错题标题..." style="margin-right: 40px;"/>

        优先级:
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ notes.priority }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item 
              v-for="item in priorityList" 
              :key="item" 
              @click.native="onPriorityChange">
              {{ item }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        分组:
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ notes.notesGroup }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item 
              v-for="item in groupList" 
              :key="item"
              @click.native="onGroupChange">
              {{ item }}
            </el-dropdown-item>
            <el-dropdown-item icon="el-icon-plus" @click.native="open()">添加分组</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <div class="sub1">
        <h2>错题内容</h2>
        <div style="width: 90%; margin: auto;">
          <wEditor @change="changeHTML($event, 'e1')" :value="notes.subject"/>
        </div>
      </div>
      <div class="sub2">
        <h2>我的答案</h2>
        <div style="width: 90%; margin: auto;">
          <wEditor @change="changeHTML($event, 'e2')" :value="notes.respond"/>
        </div>
      </div>
      <div class="sub3">
        <h2>标准答案</h2>
        <div style="width: 90%; margin: auto;">
          <wEditor @change="changeHTML($event, 'e3')" :value="notes.answer"/>
        </div>
      </div>
      <div class="sub4">
        <h2>分析总结</h2>
        <div style="width: 90%; margin: auto;">
          <wEditor @change="changeHTML($event, 'e4')" :value="notes.summary"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import wEditor from '@/component/wEditor'
export default {
  components: { wEditor },
  data() {
    return {
      priorityList: [1,2,3,4,5,6,7,8,9,10],
      groupList: ['默认'],
      notes: {
        notesTitle: '', // 错题标题
        promulgator: 'zhangsan', // 发布者账号
        priority: '选择错题优先级...',
        updateTime: '', // 更新时间
        subject: '', // 错题内容
        notesImages: [], // 错题图片
        respond: '', // 错题答案(学生自己写的)
        answer: '', // 错题标准答案
        summary: '', // 错题分析总结
        notesGroup: '选择错题分组...', // 错题分组
      }
    }
  },
  methods: {
    changeHTML(html, name){
      switch(name) {
        case 'e1': this.notes.subject = html; break;
        case 'e2': this.notes.respond = html; break;
        case 'e3': this.notes.answer = html; break;
        case 'e4': this.notes.summary = html; break;
        deafult: break;
      }
    },
    onCategoryChange(e) {
      this.notes.category = e.target.innerText
    },
    onPriorityChange(e) {
      this.notes.priority = e.target.innerText
    },
    onSubmitNote() {
      console.log(this.notes)
      var date = new Date()
      var curTime = date.getFullYear() + '.' + (date.getMonth() + 1) + '.' + date.getDate()
      this.notes.updateTime = curTime
      this.$store.commit('setCurNotes', this.notes)
      this.$router.push('/show')
    },
    onGroupChange(e) {
      this.notes.notesGroup = e.target.innerText
    },
    open() {
      this.$prompt('输入新组名', '创建新分组', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^((?!\\|\/|:|\*|\?|<|>|\||'|%|@|#|&|\$|\^|&|\*).){1,8}$/,
        inputErrorMessage: '组名内容不能包含特殊字符'
      }).then((item) => {
        this.groupList.push(item.value)
        this.$message({
          type: 'success',
          message: '创建成功'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消创建'
        })    
      })
    }
  },
  mounted() {
    // console.log("Let's Create!")
  }
}
</script>

<style scoped>
.edit-container {
  position: absolute;
  margin-left: 5%;
  width: 90%;
  min-height: 100%;
  height: auto;
  background: #ddd;
}

.top {
  position: relative;
  padding: 15px;
  font-size: 40px;
  color: #eee;
  background: rgb(0, 194, 228);
}
.show-btn {
  float: right;
  margin-right: 40px;
  font-size: 20px;
  padding: 5px;
}

.el-input {
  width: 300px;
}

.el-dropdown-link {
  border: 1px solid #000;
  background: #fff;
  padding: 2px;
  cursor: pointer;
}

.middle {
  z-index: 2;
}

.main {
  z-index: 1;
  margin: 0 auto;
  position: relative;
  background: #ddd;
  width: 100%;
  overflow: auto;
}

.main > div {
  margin: 1%;
  padding-bottom: 30px;
  font-size: 20px;
}
.main > div > h2 {
  text-align: center;
}

.sub1 {
  position: relative;
  background: #F35325;
  float: left;
  height: 100%;
  width: 48%;
}
.sub2 {
  position: relative;
  background: #81BC06;
  height: 100%;
  float: left;
  width: 48%;
}
.sub3 {
  position: relative;
  background: #05A6F0;
  height: 100%;
  float: left;
  width: 48%;
}
.sub4 {
  position: relative;
  background: #FFBA08;
  height: 100%;
  float: left;
  width: 48%;
}
</style>