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

export function getConfigList(namespaceId,groupName,dataId,page,size,fuzzySearch){
    return request({
        method:'get',
        url:'/grace/server/config/getConfigList',
        params:{
            namespaceId:namespaceId,
            groupName:groupName,
            dataId:dataId,
            page:page,
            size:size,
            fuzzySearch:fuzzySearch
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

export function importConfig(namespaceId,groupName,configFile,configConflictPolicy){
    return request({
        method:'post',
        url:'/grace/server/config/importConfig',
        headers:{
            // 文件上传一定要设置这个请求头
            "Content-Type":"multipart/form-data"
        },
        data:{
            namespaceId: namespaceId,
            groupName: groupName,
            configFile: configFile,
            configConflictPolicy: configConflictPolicy,
        }
    })
}

export function exportSelectedConfig(exportConfigIdListJSON){
    return request({
        method:'post',
        url:'/grace/server/config/exportSelectedConfig',
        // 文件下载（）
        responseType: 'blob',
        data:{
            exportConfigIdListJSON:exportConfigIdListJSON
        }
    })
}

// export function exportSelectedConfig(){
//     return request({
//         method:'get',
//         url:'/grace/server/config/exportSelectedConfig',
//         // 文件下载（）
//         responseType: 'blob',
//     })
// }