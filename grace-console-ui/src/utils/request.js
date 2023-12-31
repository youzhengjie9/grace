//二次封装axios
import axios from 'axios'
import store from '@/store'
//引入element-ui的消息弹出
import { Message } from 'element-ui'
import router from '@/router'


const service = axios.create({
    //我们springboot后端网关服务器的ip+端口号
    baseURL: "http://localhost:8848",
    //6s没有响应就算超时
    timeout: 6000
})

//二次封装axios的使用（在api包下使用方式）
/*
1：get请求传参：（get请求传参是params属性）

export function 自定义的方法名(){
    return request({
        method: 'get',
        url: '/xxx',
        params: {
            参数1: '111',
            参数2: '222'
         }
    })
}

2：post请求传参：（post请求传参是data属性，后端要用@RequestBody接收）

export function 自定义的方法名(){
    return request({
        method: 'post',
        url: '/xxx',
        data: {
            参数1: '111',
            参数2: '222'
        }
    })
}


*/


//添加axios请求拦截器（在发送axios请求前自动执行）
service.interceptors.request.use(function (config) {
    //如果有accessToken，则每一次发送请求之前都要在localStorage中拿到accessToken并放到请求头accessToken中
    if (store.state.user.accessToken) {
        config.headers['accessToken'] = store.state.user.accessToken
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

//添加axios响应拦截器（axios请求发送后，后台返回数据给前端，当接收响应数据后自动执行）
service.interceptors.response.use(function (response) {
    // 响应数据
    const data = response.data;

    return response; //记得要返回response。

}, function (error) {
    //响应错误回调（比如 接口的403无权限异常将会在这里进行处理 ） 
    console.log(error)
    // 403无权限异常处理
    if (error.response.status === 403) {

        Message({
            message: '您没有该操作的权限!',
            type: 'error',
            duration: 1000
        })

        setTimeout(() => {
            router.push({
                path: '/403'
            })
        }, 1000)
    }
    // 对响应错误做点什么
    return Promise.reject(error);

});


//对外暴露
export default service
