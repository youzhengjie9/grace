import request from '../utils/request'

export function buildTreeByUserId(userId){
    return request({
        method:'get',
        url:'/grace/server/menu/tree/buildTreeByUserId',
        params:{
            userId:userId
        }
    })
}

export function buildAllMenuPermissionTree(){
    return request({
        method:'get',
        url:'/grace/server/menu/tree/buildAllMenuPermissionTree'
    })
}

export function buildAssignMenuTree(){
    return request({
        method:'get',
        url:'/grace/server/menu/tree/buildAssignMenuTree'
    })
}

export function buildCanChooseMenuTreeByNewMenuType(type){
    return request({
        method:'get',
        url:'/grace/server/menu/tree/buildCanChooseMenuTreeByNewMenuType',
        params:{
            type:type
        }
    })
}