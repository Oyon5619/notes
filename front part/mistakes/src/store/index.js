import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    curNotes: null
  },
  getters: {
    getCurNotes(state) {
      return state.curNotes
    }
  },
  mutations: {
    setCurNotes(state, notes) {
      state.curNotes = notes
    }
  },
  actions: {
  },
  modules: {
  }
})
