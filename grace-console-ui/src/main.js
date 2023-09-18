import Vue from 'vue'
import App from './App.vue'
// 导入路由配置
import router from './router'
// 导入element-ui
import ElementUI from 'element-ui'
// 导入element-ui的css样式
import 'element-ui/lib/theme-chalk/index.css';
// 引入时间格式化插件
import moment from 'moment'
// 导入vuex的主模块
import store from './store'

Vue.config.productionTip = false

// 使用路由配置
Vue.use(router)
// 使用element-ui
Vue.use(ElementUI)


//定义一个全局过滤器实现日期格式化
Vue.filter('dateformat',function (input,fmtstring) {//当input为时间戳时，需转为Number类型
  // 使用momentjs这个日期格式化类库实现日期的格式化功能
  return moment(input).format(fmtstring);
})

new Vue({
  el:'#app',
  router,
  store,
  render: h => h(App)
})




