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

export function modifyService(serviceDTO){
    return request({
        method:'put',
        url:'/grace/server/service/modifyService',
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

export function deleteService(namespaceId,groupName,serviceName){
    return request({
        method:'delete',
        url:'/grace/server/service/deleteService',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            serviceName:serviceName
        }
    })
}