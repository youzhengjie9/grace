import request from '../utils/request'

export function exportAllUser(){
    return request({
        method:'get',
        url:'/petal-system/sys/export/excel/exportAllUser',
        responseType: 'blob' //响应类型要设置为blob
    })
}

export function exportAllRole(){
    return request({
        method:'get',
        url:'/petal-system/sys/export/excel/exportAllRole',
        responseType: 'blob' //响应类型要设置为blob
    })
}


export function exportAllMenu(){
    return request({
        method:'get',
        url:'/petal-system/sys/export/excel/exportAllMenu',
        responseType: 'blob' //响应类型要设置为blob
    })
}


export function exportAllLoginLog(){
    return request({
        method:'get',
        url:'/petal-system/sys/export/excel/exportAllLoginLog',
        responseType: 'blob' //响应类型要设置为blob
    })
}


export function exportAllOperationLog(){
    return request({
        method:'get',
        url:'/petal-system/sys/export/excel/exportAllOperationLog',
        responseType: 'blob' //响应类型要设置为blob
    })
}