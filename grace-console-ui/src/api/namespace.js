import request from '@/utils/request'

export function getNamespaceList(){
    return request({
        method:'get',
        url:'/grace/server/namespace/getNamespaceList'
    })
}