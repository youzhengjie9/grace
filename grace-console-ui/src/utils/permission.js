//导入路由
import router from "@/router";
//导入layout公共布局页面
import Layout from '../layout/index.vue'
import store from '../store/index'

//初始化动态路由方法(重复的路由会被覆盖)
export function initDynamicRouter() {

    //根路由，通过这个root路由对象找到router/index.js的对应路由并添加上去
    let root =
    {
        path: '/',
        /*
         访问“/”路径时自动重定向到“/config/list路径”,从而实现既访问到Layout组件,
         又将当前的url（由于已经重定向到 /config/list ,所以当前url是 /config/list）所对应的组件
         展示在Layout组件中的router-view标签上（从而实现vue组件的嵌套效果）
         */
        redirect: '/config/list',
        // Layout是我们后台系统的整体布局组件
        component: Layout,
        // 动态路由。需要展示在Layout组件中的router-view标签上的路由（也就是嵌套路由）
        children: []
    };

    //获取该用户的动态路由dynamicRouterList
    //注意: 只有vue文件才可以使用this.$store.state.user.dynamicMenu获取vuex数据
    //普通的js文件可以使用store._modules.root._children.xxx模块.state.属性名获取
    let dynamicRouterList = store._modules.root._children.user.state.dynamicRouter

    // 将数据库中查到该用户有动态路由才进行遍历
    if (dynamicRouterList.length > 0) {

        dynamicRouterList.forEach(menu => {
            // 封装动态路由对象
            let routerObj = {};
            // 封装“需要”使用keepalive缓存的路由对象
            if (menu.path == '/config/list' || menu.path == '/config/version/list') {
                //封装动态路由对象
                routerObj = {
                    // 路由的路径
                    path: menu.path,
                    //巨坑：这里的常量前缀必须是'../views'，不能把/view写到component里面，不然一样会找不到模块
                    component: resolve => require(['../views' + menu.component], resolve),
                    meta: {
                        // 该路由组件是否可以被缓存
                        keepAlive: true
                    }
                }
            } 
            // 封装“不需要”使用keepalive缓存的路由对象
            else {
                //封装动态路由对象
                routerObj = {
                    // 路由的路径
                    path: menu.path,
                    //巨坑：这里的常量前缀必须是'../views'，不能把/view写到component里面，不然一样会找不到模块
                    component: resolve => require(['../views' + menu.component], resolve)
                }
            }

            // 把这个路由放到root变量中
            root.children.push(routerObj);
        });
    }

    // 加载 root 动态路由,调用了这个方法说明这个动态路由已经生效了
    router.addRoute(root);

}

