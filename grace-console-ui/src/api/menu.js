import request from '@/utils/request'


export function selectRoleCheckedMenuByRoleId(roleid){
    return request({
        method:'get',
        url:'/petal-system/sys/menu/selectRoleCheckedMenuByRoleId',
        params:{
            id:roleid
        }
    })
}

export function addMenu(menuDTO){
    return request({
        method:'post',
        url:'/petal-system/sys/menu/addMenu',
        data:menuDTO
    })
}

export function updateMenu(menuDTO){
    return request({
        method:'post',
        url:'/petal-system/sys/menu/updateMenu',
        data:menuDTO
    })
}
export function deleteMenu(menuid){
    return request({
        method:'delete',
        url:'/petal-system/sys/menu/deleteMenu',
        params:{
            menuid:menuid
        }
    })
}

export function selectMenuNameByMenuId(menuid){
    return request({
        method:'get',
        url:'/petal-system/sys/menu/selectMenuNameByMenuId',
        params:{
            menuid:menuid
        }
    })
}