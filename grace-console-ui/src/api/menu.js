import request from '@/utils/request'


export function getRoleCheckedMenuByRoleId(roleId){
    return request({
        method:'get',
        url:'/grace/server/menu/getRoleCheckedMenuByRoleId',
        params:{
            id:roleId
        }
    })
}

export function addMenu(menuDTO){
    return request({
        method:'post',
        url:'/grace/server/menu/addMenu',
        data:menuDTO
    })
}

export function modifyMenu(menuDTO){
    return request({
        method:'post',
        url:'/grace/server/menu/modifyMenu',
        data:menuDTO
    })
}
export function deleteMenu(menuId){
    return request({
        method:'delete',
        url:'/grace/server/menu/deleteMenu',
        params:{
            menuId:menuId
        }
    })
}

export function getMenuNameByMenuId(menuId){
    return request({
        method:'get',
        url:'/grace/server/menu/getMenuNameByMenuId',
        params:{
            menuId:menuId
        }
    })
}