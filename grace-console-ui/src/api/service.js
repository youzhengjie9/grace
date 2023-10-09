import request from '@/utils/request'

export function getServiceList(namespaceId,hideEmptyService,page,size){
    return request({
        method:'get',
        url:'/grace/server/service/getServiceList',
        params:{
            namespaceId:namespaceId,
            hideEmptyService:hideEmptyService,
            page:page,
            size:size
        }
    })
}