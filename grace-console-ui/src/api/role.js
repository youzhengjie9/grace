import request from '@/utils/request'

export function selectAllRoleByLimit(page,size){
    return request({
        method:'get',
        url:'/petal-system/sys/role/selectAllRoleByLimit',
        params:{
            page:page,
            size:size
        }
    })
}

export function selectAllRoleCount(){
    return request({
        method:'get',
        url:'/petal-system/sys/role/selectAllRoleCount'
    })
}


export function selectAllRole(){
    return request({
        method:'get',
        url:'/petal-system/sys/role/selectAllRole'
    })
}

export function selectUserCheckedRoleByUserId(userid){
    return request({
        method:'get',
        url:'/petal-system/sys/role/selectUserCheckedRoleByUserId',
        params:{
            id:userid
        }
    })
}

export function addRole(roleFormDTO){
    return request({
        method:'post',
        url:'/petal-system/sys/role/addRole',
        data:roleFormDTO
    })
}

export function updateRole(roleFormDTO){
    return request({
        method:'post',
        url:'/petal-system/sys/role/updateRole',
        data:roleFormDTO
    })
}

export function deleteRole(roleid){
    return request({
        method:'delete',
        url:'/petal-system/sys/role/deleteRole',
        params:{
            id:roleid
        }
    })
}

export function assignMenu(assignMenuDTO){
    return request({
        method:'post',
        url:'/petal-system/sys/role/assignMenu',
        data: assignMenuDTO
    })
}

export function searchRoleByRoleNameAndLimit(roleName, page, size){
    return request({
        method:'get',
        url:'/petal-system/sys/role/searchRoleByRoleNameAndLimit',
        params:{
            roleName:roleName,
            page:page,
            size:size
        }
    })
}

export function searchRoleCountByRoleName(roleName){
    return request({
        method:'get',
        url:'/petal-system/sys/role/searchRoleCountByRoleName',
        params:{
            roleName:roleName
        }
    })
}