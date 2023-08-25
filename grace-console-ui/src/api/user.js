import request from '../utils/request.js'

export function getCurrentUserInfo(){
    return request({
        method:'get',
        url:'/petal-system/sys/user/getCurrentUserInfo'
    })
}

export function selectAllUserByLimit(page,size){
    return request({
        method:'get',
        url:'/petal-system/sys/user/selectAllUserByLimit',
        params:{
            page:page,
            size:size
        }
    })
}

export function selectAllUserCount(){
    return request({
        method:'get',
        url:'/petal-system/sys/user/selectAllUserCount'
    })
}


export function addUser(operateForm){
    return request({
        method:'post',
        url:'/petal-system/sys/user/addUser',
        data:operateForm
    })
}

export function updateUser(operateForm){
    return request({
        method:'post',
        url:'/petal-system/sys/user/updateUser',
        data:operateForm
    })
}

export function deleteUser(userid){
    return request({
        method:'delete',
        url:'/petal-system/sys/user/deleteUser',
        params:{
            id:userid
        }
    })
}

export function assignRole(roles){
    return request({
        method:'post',
        url:'/petal-system/sys/user/assignRole',
        data: roles
    })
}

export function searchUserByUserNameAndLimit(userName, page, size){
    return request({
        method:'get',
        url:'/petal-system/sys/user/searchUserByUserNameAndLimit',
        params:{
            userName:userName,
            page: page,
            size:size
        }
    })
}

export function searchUserCountByUserName(userName){
    return request({
        method:'get',
        url:'/petal-system/sys/user/searchUserCountByUserName',
        params:{
            userName:userName
        }
    })
}