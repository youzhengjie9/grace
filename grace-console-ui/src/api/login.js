//引入二次封装axios
import request from '../utils/request'
import website from "@/config/website";
import {aesEncrypt,aesDecrypt} from "@/utils/aes";

//获取图片验证码
export function getImageCaptcha(){
    return request({
        method: 'get',
        url: '/image/captcha'
    })
}

//密码登录
export function passwordLogin(formData,image_captcha_key,image_captcha){
    console.log(formData)
    //将表单的密码使用AES加密再发给后端
    formData.password = aesEncrypt(formData.password,website.loginPasswordAESKey);
    //window.btoa 用于base64加密
    const basicAuth = 'Basic ' + window.btoa(website.passwordLoginClient) ;

    return request({
        method:'post',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            "Authorization": basicAuth
        },
        url:'/petal-auth/oauth2/token?grant_type=password&scope=server&image_captcha_key='+image_captcha_key+'&image_captcha='+image_captcha,
        data:formData
    })
}

export function sendSmsCaptcha(phone){
    return request({
        url: '/send/sms/captcha/'+phone,
        method: 'get'
    })
}

//短信登录
export function smsLogin(phone,smsCaptcha){
    //window.btoa 用于base64加密
    const basicAuth = 'Basic ' + window.btoa(website.smsLoginClient) ;

    return request({
        method:'post',
        headers: {
            "Authorization": basicAuth
        },
        url:'/petal-auth/oauth2/token?grant_type=sms_login&scope=server&phone='+phone+'&sms_captcha='+smsCaptcha,
    })
}