import Vue from 'vue'
//导入vuex，实现数据的共享
import Vuex from 'vuex'
import sidebar from '@/store/modules/Sidebar'
import user from '@/store/modules/User'
import form from '@/store/modules/form'

//使用vuex插件
Vue.use(Vuex);

export default new Vuex.Store({

    //vuex的模块化
    modules: {
        // vuex的子模块
        user,
        form,
        sidebar,
    }

})
