import Vue from "vue";
// 导入vue-router
import VueRouter from "vue-router";
// 导入进度条nprogress
import NProgress from "nprogress";
// 导入进度条nprogress的css样式
import 'nprogress/nprogress.css'
// 导入我们后台系统整体布局组件Layout
import Layout from '../layout/index.vue'


//解决Vue路由重复跳转报错,要放到Vue.use(VueRouter)之前
const routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function (location) {
    return routerPush.call(this, location).catch(err => {})
};

// 使用vue-router
Vue.use(VueRouter);

// 配置路由
const router = new VueRouter({
    // 路由模式
    mode: 'history',
    // 定义路由
    routes:[
        {
            path:'/',
            /*
             访问“/”路径时自动重定向到“/service/list路径”,从而实现既访问到Layout组件,
             又将当前的url（由于已经重定向到 /service/list ,所以当前url是 /service/list）所对应的组件
             展示在Layout组件中的router-view标签上（从而实现vue组件的嵌套效果）
             */
            redirect:'/service/list',
            // Layout是我们后台系统的整体布局组件
            component: Layout,
            // 需要展示在Layout组件中的router-view标签上的路由（也就是嵌套路由）
            children:[
                {
                    path:'/service/list',
                    component: () => import('../views/service/list/index.vue')
                },
                {
                    path:'/config/list',
                    component: () => import('../views/config/list/index.vue')
                },
                {
                    // 配置的历史版本路由
                    path:'/config/history/version',
                    component: () => import('../views/config/history/version/index.vue')
                },
            ]  
        },
        {
            // 服务详情路由
            path:'/service/detail',
            component: () => import('../views/service/detail/index.vue')
        },
        {
            // 创建配置路由
            path:'/config/create',
            component: () => import('../views/config/create/index.vue')
        },
        {
            // 配置详情路由
            path:'/config/detail',
            component: () => import('../views/config/detail/index.vue')
        },
        {
            // 修改配置路由
            path:'/config/modify',
            component: () => import('../views/config/modify/index.vue')
        },
        {
            // 配置的历史版本详情路由
            path:'/config/history/version/detail',
            component: () => import('../views/config/history/version/detail/index.vue')
        },
        {
            // 配置回滚路由
            path:'/config/history/version/rollback',
            component: () => import('../views/config/history/version/rollback/index.vue')
        },
        {
            // 登录路由
            path:'/login',
            component: () => import('../views/login/index.vue')
        },
        {
            // 403没有权限路由
            path:'/403',
            component: () =>import('../views/error/403.vue')
        },
        {
            // 404未找到页面路由
            path:'*',
            component: () => import('../views/error/404.vue')
        }    

    ]


})


//配置全局路由守卫（在进入路由前会自动执行）
router.beforeEach((to,from,next) => {

    //当进入路由前进度条开启
    NProgress.start();

    next();

})

//配置全局路由守卫（退出路由后会自动执行）
router.afterEach(() => {
    
    //当退出路由后进度条关闭
    NProgress.done();
    
})

//导出路由
export default router


