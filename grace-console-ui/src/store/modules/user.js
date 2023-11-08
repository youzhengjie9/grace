import {getCurrentUserInfo} from '../../api/user'

const state = {
    //用户名
    username:'',
    // accessToken
    accessToken: localStorage.getItem('accessToken'),
    // refreshToken
    refreshToken: localStorage.getItem('refreshToken'),
    // 动态菜单（侧边栏）数组
    dynamicMenu:[],
    // 动态路由（router）
    dynamicRouter:[],
    // 用户权限perm（包括菜单、按钮）,需要配合directive/permission/has-perm自定义指令
    perm:[]
}

const mutations = {
    SET_USERNAME:(state,username) => {
        state.username=username;
    },
    SET_ACCESSTOKEN: (state, accessToken) => {
        state.accessToken = accessToken
    },
    SET_REFRESHTOKEN: (state, refreshToken) => {
        state.refreshToken = refreshToken
    },
    SET_DYNAMIC_MENU: (state,dynamicMenu)=>{
        state.dynamicMenu=dynamicMenu
    },
    SET_DYNAMIC_ROUTER: (state,dynamicRouter)=>{
        state.dynamicRouter=dynamicRouter
    },
    SET_PERM: (state,perm)=>{
        state.perm=perm;
    }
}

const actions = {

    //用户登录成功（只需要返回token即可）
    loginSuccess(context,data){
        //将token到localstorage中进行持久化，因为vuex的数据是没有持久化效果的，刷新页面就会丢失，所以要放到localstorage中
        localStorage.setItem('accessToken',data.accessToken)
        localStorage.setItem('refreshToken',data.refreshToken)
        //点击登录后还需要执行一次手动把token放到vuex中（这步操作只在login操作执行一次即可）
        context.commit('SET_ACCESSTOKEN',data.accessToken);
        context.commit('SET_REFRESHTOKEN',data.refreshToken);
        //因为oauth2登录只会获得accessToken和refreshToken这两个重要的内容,而少了很多重要内容（比如动态菜单、动态路由、用户权限perm）
        //所以我们要携带accessToken去调用getCurrentUserInfo接口，获取到动态菜单、动态路由、用户权限perm
        //然后将用户基本信息存储到vuex中，这部分的数据会随着刷新页面而丢失
        getCurrentUserInfo(context);
    },
    // 获取当前用户信息
    getCurrentUserInfo(context){
        return new Promise((resolve,reject)=>{
            getCurrentUserInfo().then((response)=>{
                let result = response.data;
                if(result.code===200){
                    let username = result.data.username;
                    let dynamicMenu = result.data.dynamicMenu;
                    let dynamicRouter = result.data.dynamicRouter;
                    let perm = result.data.perm;
                    context.commit('SET_USERNAME',username);
                    // 用户的动态菜单（侧边栏）
                    if(dynamicMenu != null && dynamicMenu.length > 0){
                        context.commit('SET_DYNAMIC_MENU',JSON.parse(dynamicMenu))
                    }
                    // 用户的动态路由
                    if(dynamicRouter != null && dynamicRouter.length > 0){
                        context.commit('SET_DYNAMIC_ROUTER',JSON.parse(dynamicRouter))
                    }
                    // 用户权限perm（包括菜单、按钮）,需要配合directive/permission/has-perm自定义指令
                    if(perm != null && perm.length > 0){
                        context.commit('SET_PERM',JSON.parse(perm))
                    }
                    resolve(response);
                }
                else{
                    //防止accessToken和refreshToken过期了
                    //而这两个token会一直存在localstorage，需要客户手动删除才可以进入/login页面
                    //（因为我们设置了路由守卫，一旦有token在localstorage中就无法访问login页面的配置）
                    //所以当获取不到用户信息则要清空这些数据（特别是两个token数据）
                    //清空VUEX内容
                    context.commit('SET_USERNAME','')
                    context.commit('SET_ACCESSTOKEN','');
                    context.commit('SET_REFRESHTOKEN','');
                    context.commit('SET_DYNAMIC_MENU',[])
                    context.commit('SET_DYNAMIC_ROUTER',[])
                    context.commit('SET_PERM',[])
                    //清空localstorage的accessToken和refreshToken
                    localStorage.removeItem('accessToken')
                    localStorage.removeItem('refreshToken')
                    resolve(response);
                }
                
            }).catch((err)=>{
                console.log(err)
                //防止accessToken和refreshToken过期了
                //而这两个token会一直存在localstorage，需要客户手动删除才可以进入/login页面
                //（因为我们设置了路由守卫，一旦有token在localstorage中就无法访问login页面的配置）
                //所以当获取不到用户信息则要清空这些数据（特别是两个token数据）
                //清空VUEX内容
                context.commit('SET_USERNAME','')
                context.commit('SET_ACCESSTOKEN','');
                context.commit('SET_REFRESHTOKEN','');
                context.commit('SET_DYNAMIC_MENU',[])
                context.commit('SET_DYNAMIC_ROUTER',[])
                context.commit('SET_PERM',[])
                //清空localstorage的accessToken和refreshToken
                localStorage.removeItem('accessToken')
                localStorage.removeItem('refreshToken')
                reject(err);
            })
        })
    },
    //退出成功
    logoutSuccess(context){
        //此时，后端已经成功将用户退出，前端只需要把token和用户信息清空即可（localstorage内容也要清空）
        //清空VUEX内容
        context.commit('SET_USERNAME','')
        context.commit('SET_ACCESSTOKEN','');
        context.commit('SET_REFRESHTOKEN','');
        context.commit('SET_DYNAMIC_MENU',[])
        context.commit('SET_DYNAMIC_ROUTER',[])
        context.commit('SET_PERM',[])
        //清空localstorage的accessToken和refreshToken
        localStorage.removeItem('accessToken')
        localStorage.removeItem('refreshToken')
    
    },
}


export default {
    state,
    mutations,
    actions
}