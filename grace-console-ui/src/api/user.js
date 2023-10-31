import request from '../utils/request.js'

export function login(username,password){
    return request({
        method:'post',
        url:'/user/login',
        data:{
            username:username,
            password:password
        }
    })
}

export function logout(accessToken,refreshToken){
    return request({
        method:'post',
        url:'/user/logout',
        headers:{
            accessToken:accessToken,
            refreshToken:refreshToken
        }
    })
}

export function getCurrentUserInfo(){
    return request({
        method:'get',
        url:'/user/getCurrentUserInfo'
    })
}

