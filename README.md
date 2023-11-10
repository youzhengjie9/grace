
# 手写微服务组件-轻量级的注册中心&配置中心

## 项目介绍
该项目是一个基于 SpringBoot+SpringCloud 集注册中心和配置中心于一身的微服务组件，并提供
了可视化管理平台，用户能够通过操作可视化管理平台从而动态的修改配置以及服务发现，同时有心跳机制来
监控服务的状态，以及有 pull+push 两种模式共同来保证修改的配置实时刷新，也可以实现配置的回滚

## 效果图

- **登录模块**效果图

![0.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/0.png)

- **配置中心模块**效果图

![1.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/1.png)
![2.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/2.png)
![3.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/3.png)
![4.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/4.png)
![5.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/5.png)
![6.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/6.png)
![7.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/7.png)

- **注册中心模块**效果图

![8.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/8.png)
![9.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/9.png)
![10.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/10.png)

- **用户模块**效果图

![11.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/11.png)
![12.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/12.png)

- **角色模块**效果图

![13.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/13.png)
![14.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/14.png)

- **菜单模块**效果图

![15.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/15.png)
![16.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/16.png)

- **命名空间模块**效果图

![17.png](https://gitee.com/youzhengjie/grace/raw/main/doc/images/17.png)



### 基本插件安装

#### 安装vue2最新稳定版

```
npm install vue@^2
```

#### 安装element-ui最新版

```
npm i element-ui -S
```

#### 安装vuex3.0
```
npm i vuex@3
```

#### 安装vue-router3.0
```
npm i vue-router@3
```

#### 安装sass

- 1:

```
npm install sass@1.26.8 --save-dev
```

- 2:

```
npm install sass-loader@8.0.2 --save-dev
```


#### 安装axios和vue-axios
```
npm install --save axios vue-axios
```


#### 安装less
```
npm i less less-loader@7
```

#### 安装vue-fragment
```
npm install vue-fragment --save
```

#### 安装nprogress进度条的插件
```
npm i -S nprogress
```

#### 安装echarts
```
npm install echarts --save
```

#### 安装moment时间格式化插件

```
npm install moment
```

#### 安装vue2-ace-editor代码编辑器

```
npm install --save-dev vue2-ace-editor
```

#### vue2代码差异对比插件

- 1:
```
npm i v-code-diff
```

- **2:（注意,如果当前vue的版本>2.7则不需要执行这条命令,因为vue2.7自带composition-api）**

```
npm i @vue/composition-api
```























