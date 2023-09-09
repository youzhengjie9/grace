import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false

// 使用element-ui插件
Vue.use(ElementUI)

new Vue({
  el:'#app',
  render: h => h(App)
})