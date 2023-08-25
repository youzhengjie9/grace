import request from '@/utils/request'


export function getServerInfo(){
    return request({
        method:"get",
        url:'/petal-system/sys/server/monitor/getServerInfo',
    })
}