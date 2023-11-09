const state = {

    // 侧边栏菜单
    sidebarMenu: [
        {
            id: 101,
            menuName: "服务管理",
            icon: "el-icon-s-home",
            children: [
                {
                    id: 1001,
                    path: "/service/list",
                    component: "../views/service/list/index.vue",
                    menuName: "服务列表",
                    icon: "el-icon-s-home",
                    children: []
                },
            ],
        },
        {
            id: 201,
            menuName: "配置管理",
            icon: "el-icon-s-home",
            children: [
                {
                    id: 2001,
                    path: "/config/list",
                    component: "../views/config/list/index.vue",
                    menuName: "配置列表",
                    icon: "el-icon-s-home",
                    children: []
                },
                {
                    id: 2002,
                    path: "/config/version/list",
                    component: "../views/config/version/list/index.vue",
                    menuName: "配置版本",
                    icon: "el-icon-s-home",
                    children: []
                },
            ],
        },
        {
            id: 301,
            path: "/user/list",
            component: "../views/user/list/index.vue",
            menuName: "用户管理",
            icon: "el-icon-s-home",
            children: [],
        },
        {
            id: 401,
            path: "/role/list",
            component: "../views/role/list/index.vue",
            menuName: "角色管理",
            icon: "el-icon-s-home",
            children: [],
        },
        {
            id: 501,
            path: "/menu/list",
            component: "../views/menu/list/index.vue",
            menuName: "菜单管理",
            icon: "el-icon-s-home",
            children: [],
        },
        {
            id: 601,
            path: "/namespace/list",
            component: "../views/namespace/list/index.vue",
            menuName: "命名空间",
            icon: "el-icon-s-home",
            children: [],
        },
    ],
    // 当前高亮的侧边栏菜单的路由路径,（注意：高亮的菜单所在的目录也会被自动展开）
    currentSidebarMenuHighlightPath: '',

}

const mutations = {

    // 设置侧边栏菜单
    SET_SIDEBAR_MENU(state,newSidebarMenu){
        state.sidebarMenu = newSidebarMenu
    },
    //将newCurrentSidebarMenuHighlightPath替换成新的侧边栏高亮的菜单路由路径
    SET_CURRENT_SIDEBAR_MENU_HIGHLIGHT_PATH(state, newCurrentSidebarMenuHighlightPath) {
        state.currentSidebarMenuHighlightPath = newCurrentSidebarMenuHighlightPath;
    }

}

const actions = {

    // 设置侧边栏菜单
    setSidebarMenu(context,newSidebarMenu){
        context.commit('SET_SIDEBAR_MENU', newSidebarMenu);
    },
    //将newCurrentSidebarMenuHighlightPath替换成新的侧边栏高亮的菜单路由路径
    setCurrentSidebarMenuHighlightPath(context, newCurrentSidebarMenuHighlightPath) {
        // 更新到vuex
        context.commit('SET_CURRENT_SIDEBAR_MENU_HIGHLIGHT_PATH', newCurrentSidebarMenuHighlightPath);
    }

}


export default {

    state,
    mutations,
    actions

}