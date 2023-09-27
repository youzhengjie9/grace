<template>
  <!-- 
      解决elementui菜单栏收缩，但是多级菜单字体不消失的bug。解决方式为：首先引入vue-fragment，
      然后将最外层div改成fragment标签。
      参考文档：https://www.cnblogs.com/qiunanyan/p/14240272.html
     -->
  <fragment>
    <!-- 侧边栏菜单项 -->
    <template v-for="(item, index) in sidebarMenu">
      <!-- 情况一：当前遍历到的item是《目录》（children数组不为空）。
        因为点击目录是不能进行路由跳转的,只能展开目录，所以要把当前的el-submenu标签上设置 :index="item.id + ''" 
        ,这样目录就只会展开,而不会跳转路由了。
      -->
      <el-submenu
        :key="index"
        :index="item.id + ''"
        v-if="item.children.length > 0"
      >
        <template slot="title">
          <!-- 菜单图标 -->
          <i :class="item.icon"></i>
          <!-- 菜单名称 -->
          <span slot="title">{{ item.menuName }}</span>
        </template>
        <!-- 递归子菜单（当前item的children数组） -->
        <sidebar-menu-item :sidebarMenu="item.children"></sidebar-menu-item>

      </el-submenu>

      <!-- 情况二：当前遍历到的item是《菜单》（children数组为空）
         ，因为点击菜单是会进行路由跳转的,所以要把当前的el-menu-item标签上设置 :index="item.path + ''"
         ，elementui会根据 :index 中的path值去跳转到指定的路由
          -->
      <el-menu-item
        :key="index"
        v-else
        :index="item.path + ''"
      >
        <i :class="item.icon"></i>
        <span slot="title">{{ item.menuName }}</span>
      </el-menu-item>

    </template>
  </fragment>
</template>
    
<script>
import { Fragment } from "vue-fragment";

export default {
  name: "SidebarMenuItem",
  components: {
    Fragment
  },
  props: {
    // 侧边栏菜单数据
    sidebarMenu: {
      type: Array,
      default: () => [],
    },
  },
  methods: {
  },
  // 注意： 在template标签上使用v-for，:key="index"不能写在template标签上，因为其标签不会被渲染，会引起循环错误
};
</script>