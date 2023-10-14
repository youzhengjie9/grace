import request from '@/utils/request'

export function getServiceList(namespaceId,groupName,serviceName,hideEmptyService,page,size){
    return request({
        method:'get',
        url:'/grace/server/service/getServiceList',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            serviceName:serviceName,
            hideEmptyService:hideEmptyService,
            page:page,
            size:size
        }
    })
}

export function createService(serviceDTO){
    return request({
        method:'post',
        url:'/grace/server/service/createService',
        data:{
            namespaceId: serviceDTO.namespaceId,
            groupName: serviceDTO.groupName,
            serviceName: serviceDTO.serviceName,
            protectThreshold: serviceDTO.protectThreshold,
            metadata: serviceDTO.metadata,
        }
    })
}

export function getServiceDetail(namespaceId,groupName,serviceName){
    return request({
        method:'get',
        url:'/grace/server/service/getServiceDetail',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            serviceName:serviceName
        }
    })
}