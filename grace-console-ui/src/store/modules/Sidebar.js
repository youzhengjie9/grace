const state = {

    // 侧边栏菜单
    sidebarMenu: [],
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