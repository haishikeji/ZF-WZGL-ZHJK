
import { defineStore } from 'pinia'
 
export const useStore = defineStore('main', {
  state: () => {
    return {
      info: {},
      user: {},
      phone: {},
    }
  },
  persist: {
    enabled: true,
    strategies: [{
      storage: localStorage,//localStorage存储多个key
      paths: ['info'],
      key: "info"
    }, {
      storage: localStorage,
      paths: ['user'],
      key: "user"
    },{
      storage: localStorage,
      paths: ['phone'],
      key: "phone"
    },{
      storage: sessionStorage,//sessionStorage存储多个key
      paths: ['info'],
      key: "info"
    },{
      storage: sessionStorage,
      paths: ['user'],
      key: "user"
    },{
      storage: sessionStorage,
      paths: ['phone'],
      key: "phone"
    }],
  },
  getters: {
    doubleCount: (state) => state,
  },
  actions: {
    SetInfo(val) {
      this.info = val
      console.log('SetInfo',val)
    },
    SetUser(val) {
      this.user = val
    },
    SetPhone(val) {
      this.phone = val
    },
  },
  
})
