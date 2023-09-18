const state = {

    // 当前高亮的侧边栏菜单的index（这里我们说的index是菜单的路由路径）
    currentSidebarMenuHighlightIndex: '/service/list'

}

const mutations = {

    //将newCurrentSidebarMenuHighlightIndex替换成新的侧边栏高亮的菜单index（这里我们说的index是路由路径）
    SET_CURRENT_SIDEBAR_MENU_HIGHLIGHT_INDEX(state,newCurrentSidebarMenuHighlightIndex){
        state.currentSidebarMenuHighlightIndex=newCurrentSidebarMenuHighlightIndex;
    }

}

const actions = {

    //将newCurrentSidebarMenuHighlightIndex替换成新的侧边栏高亮的菜单index（这里我们说的index是路由路径）
    setCurrentSidebarMenuHighlightIndex(context,newCurrentSidebarMenuHighlightIndex){
        context.commit('SET_CURRENT_SIDEBAR_MENU_HIGHLIGHT_INDEX',newCurrentSidebarMenuHighlightIndex);
    }

}


export default {

    state,
    mutations,
    actions

}