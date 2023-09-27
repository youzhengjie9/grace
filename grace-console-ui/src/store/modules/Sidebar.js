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
                    path: "/config/revisions/list",
                    component: "../views/config/revisions/list/index.vue",
                    menuName: "历史版本",
                    icon: "el-icon-s-home",
                    children: []
                },
            ],
        },
        {
            id: 301,
            path: "/namespace/list",
            component: "../views/namespace/list/index.vue",
            menuName: "命名空间",
            icon: "el-icon-s-home",
            children: [],
        },
    ],
    // 当前高亮的侧边栏菜单的index（这里我们说的index是菜单的路由路径）
    currentSidebarMenuHighlightIndex: '/service/list',

}

const mutations = {

    // 设置侧边栏菜单
    SET_SIDEBAR_MENU(state,newSidebarMenu){
        state.sidebarMenu = newSidebarMenu
    },
    //将newCurrentSidebarMenuHighlightIndex替换成新的侧边栏高亮的菜单index（这里我们说的index是路由路径）
    SET_CURRENT_SIDEBAR_MENU_HIGHLIGHT_INDEX(state, newCurrentSidebarMenuHighlightIndex) {
        state.currentSidebarMenuHighlightIndex = newCurrentSidebarMenuHighlightIndex;
    }

}

const actions = {

    // 设置侧边栏菜单
    setSidebarMenu(context,newSidebarMenu){
        context.commit('SET_SIDEBAR_MENU', newSidebarMenu);
    },
    //将newCurrentSidebarMenuHighlightIndex替换成新的侧边栏高亮的菜单index（这里我们说的index是路由路径）
    setCurrentSidebarMenuHighlightIndex(context, newCurrentSidebarMenuHighlightIndex) {
        context.commit('SET_CURRENT_SIDEBAR_MENU_HIGHLIGHT_INDEX', newCurrentSidebarMenuHighlightIndex);
    }

}


export default {

    state,
    mutations,
    actions

}