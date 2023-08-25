import request from '../utils/request'


export function selectAllOperationLogByLimit(page,size){
    return request({
        method:'get',
        url:'/petal-system/sys/operation/log/selectAllOperationLogByLimit',
        params:{
            page:page,
            size:size
        }
    })
}

export function selectAllOperationLogCount(){
    return request({
        method:'get',
        url:'/petal-system/sys/operation/log/selectAllOperationLogCount'
    })
}


export function deleteOperationLog(id){
    return request({
        method:'delete',
        url:'/petal-system/sys/operation/log/deleteOperationLog',
        params:{
            id:id
        }
    })
}


export function searchOperationLogByUserNameAndLimit(username,page,size){
    return request({
        method:'get',
        url:'/petal-system/sys/operation/log/searchOperationLogByUserNameAndLimit',
        params:{
            username:username,
            page:page,
            size:size
        }
    })
}

export function searchOperationLogCountByUserName(username){
    return request({
        method:'get',
        url:'/petal-system/sys/operation/log/searchOperationLogCountByUserName',
        params:{
            username:username
        }
    })
}
