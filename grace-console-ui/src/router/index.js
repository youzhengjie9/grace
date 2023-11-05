import Vue from "vue";
// 导入vue-router
import VueRouter from "vue-router";
// 导入进度条nprogress
import NProgress from "nprogress";
// 导入进度条nprogress的css样式
import 'nprogress/nprogress.css'
// 导入我们后台系统整体布局组件Layout
import Layout from '../layout/index.vue'
import store from "@/store";

//解决Vue路由重复跳转报错,要放到Vue.use(VueRouter)之前
const routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function (location) {
    return routerPush.call(this, location).catch(err => { })
};

// 使用vue-router
Vue.use(VueRouter);

// 配置路由
const router = new VueRouter({
    // 路由模式
    mode: 'history',
    // 定义路由
    routes: [
        {
            path: '/',
            /*
             访问“/”路径时自动重定向到“/service/list路径”,从而实现既访问到Layout组件,
             又将当前的url（由于已经重定向到 /service/list ,所以当前url是 /service/list）所对应的组件
             展示在Layout组件中的router-view标签上（从而实现vue组件的嵌套效果）
             */
            redirect: '/service/list',
            // Layout是我们后台系统的整体布局组件
            component: Layout,
            // 需要展示在Layout组件中的router-view标签上的路由（也就是嵌套路由）
            children: [
                {
                    path: '/service/list',
                    name: 'serviceList',
                    component: () => import('../views/service/list/index.vue')
                },
                {
                    path: '/config/list',
                    name: 'configList',
                    component: () => import('../views/config/list/index.vue'),
                    meta: {
                        // 该路由组件是否可以被缓存
                        keepAlive: true
                    }
                },
                {
                    // 配置版本列表路由
                    path: '/config/version/list',
                    name: 'configVersionList',
                    component: () => import('../views/config/version/list/index.vue'),
                    meta: {
                        // 该路由组件是否可以被缓存
                        keepAlive: true
                    }
                },
                {
                    // 用户管理
                    path: '/user/list',
                    name: 'userList',
                    component: () => import('../views/user/list/index.vue')
                },
                {
                    // 命名空间列表路由
                    path: '/namespace/list',
                    name: 'namespaceList',
                    component: () => import('../views/namespace/list/index.vue')
                },
            ]
        },
        {
            // 服务详情路由
            path: '/service/detail',
            name: 'serviceDetail',
            component: () => import('../views/service/detail/index.vue')
        },
        {
            // 创建配置路由
            path: '/config/create',
            name: 'configCreate',
            component: () => import('../views/config/create/index.vue')
        },
        {
            // 配置详情路由
            path: '/config/detail',
            name: 'configDetail',
            component: () => import('../views/config/detail/index.vue')
        },
        {
            // 修改配置路由
            path: '/config/modify',
            name: 'configModify',
            component: () => import('../views/config/modify/index.vue')
        },
        {
            // 配置版本详情路由
            path: '/config/version/detail',
            name: 'configVersionDetail',
            component: () => import('../views/config/version/detail/index.vue')
        },
        {
            // 配置回滚路由
            path: '/config/version/rollback',
            name: 'configVersionRollback',
            component: () => import('../views/config/version/rollback/index.vue')
        },
        {
            // 登录路由
            path: '/login',
            name: 'login',
            component: () => import('../views/login/index.vue')
        },
        {
            // 403没有权限路由
            path: '/403',
            name: '403',
            component: () => import('../views/error/403.vue')
        },
        {
            // 404未找到页面路由
            path: '*',
            component: () => import('../views/error/404.vue')
        }

    ]


})


//配置全局路由守卫（在进入路由前会自动执行）
router.beforeEach((to, from, next) => {

    //当进入路由前进度条开启
    NProgress.start()
    // 把侧边栏高亮菜单的路由路径更新为当前跳转到的路由路径（to.path）
    store.dispatch('setCurrentSidebarMenuHighlightPath', to.path)
    //如果有accessToken
    if (store.state.user.accessToken) {
        //并且请求路由路径是登录页则跳转到首页（防止重复登录）
        if (to.path === '/login') {
            next({ path: '/' })
        }
        //如果不是登录页
        else {
            //因为vuex的state会随着页面刷新导致丢失（也就是说vuex数据不会持久化），所以存储在vuex的用户信息会丢失
            //所以当每次路由跳转（刷新页面）同时localstorage又存在着accessToken，就要去后端查询当前用户信息并保存到vuex中
            if (store.state.user.username.length === 0) {

                store.dispatch('getCurrentUserInfo').then(res => {
                    next({ ...to, replace: true })
                }).catch(() => {
                    next({ path: '/login' })
                })
            }
            else {
                next();
            }
        }
    }
    //如果没有accessToken
    else {
        //如果去的页面不是登录的话，则打回到登录页面，不允许进入后台系统
        if (to.path !== '/login') {
            next({ path: '/login' })
        } else { //如果去的页面是登录页面的话，则放行
            next();
        }
    }

})

//配置全局路由守卫（退出路由后会自动执行）
router.afterEach(() => {

    //当退出路由后进度条关闭
    NProgress.done();

})

//导出路由
export default router


