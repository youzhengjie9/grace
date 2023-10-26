import request from '@/utils/request'



export function publishConfig(publishConfigDTO){
    return request({
        method:'post',
        url:'/grace/server/config/publishConfig',
        data:{
            namespaceId: publishConfigDTO.namespaceId,
            groupName: publishConfigDTO.groupName,
            dataId: publishConfigDTO.dataId,
            content: publishConfigDTO.content,
            configDesc: publishConfigDTO.configDesc,
            type: publishConfigDTO.type,
        }
    })
}

export function getConfigList(namespaceId,groupName,dataId,page,size){
    return request({
        method:'get',
        url:'/grace/server/config/getConfigList',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            dataId:dataId,
            page:page,
            size:size
        }
    })
}


export function getConfig(namespaceId,groupName,dataId){
    return request({
        method:'get',
        url:'/grace/server/config/getConfig',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            dataId:dataId,
        }
    })
}


export function deleteConfig(namespaceId,groupName,dataId){
    return request({
        method:'delete',
        url:'/grace/server/config/deleteConfig',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            dataId:dataId,
        }
    })
}
