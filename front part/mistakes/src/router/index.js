import Vue from 'vue'
import VueRouter from 'vue-router'
import Edit from '@/views/edit'
import Show from '@/views/show'
import wEditor from '@/component/wEditor'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/w'
  },
  {
    path: '/edit',
    name: 'edit',
    component: Edit,
    meta: { title: '错题添加或编辑' }
  },
  {
    path: '/show',
    name: 'show',
    component: Show,
    meta: { title: '展示' }
  },
  {
    path: '/w',
    name: 'w',
    component: wEditor,
    meta: { title: '富文本编辑器展示' }
  }
]

const router = new VueRouter({
  routes
})

export default router
