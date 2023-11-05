import request from '../utils/request'

export function exportAllUser(){
    return request({
        method:'get',
        url:'/grace/server/export/excel/exportAllUser',
        responseType: 'blob' //响应类型要设置为blob
    })
}

export function exportAllRole(){
    return request({
        method:'get',
        url:'/grace/server/sys/export/excel/exportAllRole',
        responseType: 'blob' //响应类型要设置为blob
    })
}


export function exportAllMenu(){
    return request({
        method:'get',
        url:'/grace/server/export/excel/exportAllMenu',
        responseType: 'blob' //响应类型要设置为blob
    })
}

