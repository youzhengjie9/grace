import request from '@/utils/request'

export function getRoleList(page,size){
    return request({
        method:'get',
        url:'/grace/server/role/getRoleList',
        params:{
            page:page,
            size:size
        }
    })
}

export function getRoleListByRoleName(roleName, page, size){
    return request({
        method:'get',
        url:'/grace/server/role/getRoleListByRoleName',
        params:{
            roleName:roleName,
            page:page,
            size:size
        }
    })
}

export function getAllRole(){
    return request({
        method:'get',
        url:'/grace/server/role/getAllRole'
    })
}

export function getUserCheckedRoleByUserId(userId){
    return request({
        method:'get',
        url:'/grace/server/role/getUserCheckedRoleByUserId',
        params:{
            id:userId
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

export function modifyRole(roleFormDTO){
    return request({
        method:'post',
        url:'/grace/server/role/modifyRole',
        data:roleFormDTO
    })
}

export function deleteRole(roleId){
    return request({
        method:'delete',
        url:'/grace/server/role/deleteRole',
        params:{
            roleId:roleId
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

