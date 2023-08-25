import request from '../utils/request'

export function buildTreeByUserId(userid){
    return request({
        method:'get',
        url:'/petal-system/sys/menutree/buildTreeByUserId',
        params:{
            userid:userid
        }
    })
}

export function buildAllMenuPermissionTree(){
    return request({
        method:'get',
        url:'/petal-system/sys/menutree/buildAllMenuPermissionTree'
    })
}

export function buildAssignMenuTree(){
    return request({
        method:'get',
        url:'/petal-system/sys/menutree/buildAssignMenuTree'
    })
}

export function assignMenu(assignMenu){
    return request({
        method:'post',
        url:'/petal-system/sys/role/assignMenu',
        data: assignMenu
    })
}

export function buildCanChooseMenuTreeByNewMenuType(type){
    return request({
        method:'get',
        url:'/petal-system/sys/menutree/buildCanChooseMenuTreeByNewMenuType',
        params:{
            type:type
        }
    })
}