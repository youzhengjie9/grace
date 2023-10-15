import request from '@/utils/request'

export function registerInstance(registerInstanceDTO){
    return request({
        method:'post',
        url:'/grace/server/instance/registerInstance',
        data:{
            namespaceId: registerInstanceDTO.namespaceId,
            groupName: registerInstanceDTO.groupName,
            serviceName: registerInstanceDTO.serviceName,
            ipAddr: registerInstanceDTO.ipAddr,
            port: registerInstanceDTO.port,
            weight: registerInstanceDTO.weight,
            healthy: registerInstanceDTO.healthy,
            online: registerInstanceDTO.online,
            ephemeral: registerInstanceDTO.ephemeral,
            metadata: registerInstanceDTO.metadata,
        }
    })
}

export function modifyInstance(modifyInstanceDTO){
    return request({
        method:'put',
        url:'/grace/server/instance/modifyInstance',
        data:{
            namespaceId: modifyInstanceDTO.namespaceId,
            groupName: modifyInstanceDTO.groupName,
            serviceName: modifyInstanceDTO.serviceName,
            instanceId: modifyInstanceDTO.instanceId,
            weight: modifyInstanceDTO.weight,
            healthy: modifyInstanceDTO.healthy,
            online: modifyInstanceDTO.online,
            metadata: modifyInstanceDTO.metadata,
        }
    })
}