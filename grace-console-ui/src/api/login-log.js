import request from '../utils/request'

export function selectAllLoginLogByLimit(page,size){
    return request({
        method:'get',
        url:'/petal-system/sys/login/log/selectAllLoginLogByLimit',
        params:{
            page:page,
            size:size
        }
    })
}

export function selectAllLoginLogCount(){
    return request({
        method:'get',
        url:'/petal-system/sys/login/log/selectAllLoginLogCount'
    })
}

export function deleteLoginLog(id){
    return request({
        method:'delete',
        url:'/petal-system/sys/login/log/deleteLoginLog',
        params:{
            id:id
        }
    })
}


export function searchLoginLogByUserNameAndLimit(username,page,size){
    return request({
        method:'get',
        url:'/petal-system/sys/login/log/searchLoginLogByUserNameAndLimit',
        params:{
            username:username,
            page:page,
            size:size
        }
    })
}

export function searchLoginLogCountByUserName(username){
    return request({
        method:'get',
        url:'/petal-system/sys/login/log/searchLoginLogCountByUserName',
        params:{
            username:username
        }
    })
}