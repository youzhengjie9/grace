import request from '../utils/request.js'

export function login(username,password){
    return request({
        method:'post',
        url:'/grace/server/user/login',
        data:{
            username:username,
            password:password
        }
    })
}

export function logout(accessToken,refreshToken){
    return request({
        method:'post',
        url:'/grace/server/user/logout',
        headers:{
            accessToken:accessToken,
            refreshToken:refreshToken
        }
    })
}

export function getCurrentUserInfo(){
    return request({
        method:'get',
        url:'/grace/server/user/getCurrentUserInfo'
    })
}

export function getUserList(page,size){
    return request({
        method:'get',
        url:'/grace/server/user/getUserList',
        params:{
            page:page,
            size:size
        }
    })
}

export function getUserListByUsername(username, page, size){
    return request({
        method:'get',
        url:'/grace/server/user/getUserListByUsername',
        params:{
            username:username,
            page: page,
            size:size
        }
    })
}

export function addUser(operateForm){
    return request({
        method:'post',
        url:'/grace/server/user/addUser',
        data:operateForm
    })
}

export function modifyUser(operateForm){
    return request({
        method:'post',
        url:'/grace/server/user/modifyUser',
        data:operateForm
    })
}

export function deleteUser(userId){
    return request({
        method:'delete',
        url:'/grace/server/user/deleteUser',
        params:{
            userId:userId
        }
    })
}

export function assignRole(assignRoleDTO){
    return request({
        method:'post',
        url:'/grace/server/user/assignRole',
        data: assignRoleDTO
    })
}
