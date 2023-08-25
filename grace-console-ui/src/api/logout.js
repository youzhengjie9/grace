import request from '../utils/request'

//密码登录
export function logout(){
 
    return request({
        method:'delete',
        url:'/petal-auth/token/logout'
    })
}