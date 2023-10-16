import request from '@/utils/request'

export function getNamespaceList(){
    return request({
        method:'get',
        url:'/grace/server/namespace/getNamespaceList'
    })
}


export function createNamespace(createNamespaceDTO){
    return request({
        method:'post',
        url:'/grace/server/namespace/createNamespace',
        data:{
            namespaceId: createNamespaceDTO.namespaceId,
            namespaceName: createNamespaceDTO.namespaceName,
            namespaceDesc: createNamespaceDTO.namespaceDesc
        }
    })
}

export function modifyNamespace(modifyNamespaceDTO){
    return request({
        method:'put',
        url:'/grace/server/namespace/modifyNamespace',
        data:{
            namespaceId: modifyNamespaceDTO.namespaceId,
            namespaceName: modifyNamespaceDTO.namespaceName,
            namespaceDesc: modifyNamespaceDTO.namespaceDesc
        }
    })
}

export function deleteNamespace(namespaceId){
    return request({
        method:'delete',
        url:'/grace/server/namespace/deleteNamespace',
        params:{
            namespaceId:namespaceId
        }
    })
}