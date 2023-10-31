<template>
  <div id="app">

    <!-- 展示 “可以被缓存” 的路由组件  -->
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive>

     <!-- 展示 “不可以被缓存” 的路由组件  -->
    <router-view v-if="!$route.meta.keepAlive"></router-view>


  </div>
</template>

<script>
export default {
  name: "App",
  components: {},
};
</script>

<style>
/* 自定义nprogress进度条颜色样式*/
#nprogress .bar {
  background: blue !important;
}

html,
body {
  margin: 0;
  padding: 0;
}
#app {
  height: 100vh;
}
</style>
