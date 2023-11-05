import request from '@/utils/request'

export function selectAllRoleByLimit(page,size){
    return request({
        method:'get',
        url:'/grace/server/role/selectAllRoleByLimit',
        params:{
            page:page,
            size:size
        }
    })
}

export function selectAllRoleCount(){
    return request({
        method:'get',
        url:'/grace/server/role/selectAllRoleCount'
    })
}


export function selectAllRole(){
    return request({
        method:'get',
        url:'/grace/server/role/selectAllRole'
    })
}

export function selectUserCheckedRoleByUserId(userid){
    return request({
        method:'get',
        url:'/grace/server/role/selectUserCheckedRoleByUserId',
        params:{
            id:userid
        }
    })
}

export function addRole(roleFormDTO){
    return request({
        method:'post',
        url:'/grace/server/role/addRole',
        data:roleFormDTO
    })
}

export function updateRole(roleFormDTO){
    return request({
        method:'post',
        url:'/grace/server/role/updateRole',
        data:roleFormDTO
    })
}

export function deleteRole(roleid){
    return request({
        method:'delete',
        url:'/grace/server/role/deleteRole',
        params:{
            id:roleid
        }
    })
}

export function assignMenu(assignMenuDTO){
    return request({
        method:'post',
        url:'/grace/server/role/assignMenu',
        data: assignMenuDTO
    })
}

export function searchRoleByRoleNameAndLimit(roleName, page, size){
    return request({
        method:'get',
        url:'/grace/server/role/searchRoleByRoleNameAndLimit',
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
        url:'/grace/server/role/searchRoleCountByRoleName',
        params:{
            roleName:roleName
        }
    })
}