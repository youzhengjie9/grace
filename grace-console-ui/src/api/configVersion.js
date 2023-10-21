import request from '@/utils/request'

export function getConfigVersionList(namespaceId,groupName,dataId,page,size){
    return request({
        method:'get',
        url:'/grace/server/config/version/getConfigVersionList',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            dataId:dataId,
            page:page,
            size:size
        }
    })
}

export function getConfigVersionInputSuggestionData(namespaceId){
    return request({
        method:'get',
        url:'/grace/server/config/version/getConfigVersionInputSuggestionData',
        params:{
            namespaceId:namespaceId
        }
    })
}

export function getConfigVersion(configVersionId){
    return request({
        method:'get',
        url:'/grace/server/config/version/getConfigVersion',
        params:{
            configVersionId:configVersionId
        }
    })
}

export function rollbackConfig(configVersionId){
    return request({
        method:'post',
        url:'/grace/server/config/version/rollbackConfig',
        params:{
            configVersionId:configVersionId
        }
    })
}