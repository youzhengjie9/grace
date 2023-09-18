<template>
  <div class="sidebar-box">
    <el-menu
      :default-openeds="['101']"
      :default-active="currentSidebarMenuHighlightIndex"
      class="el-menu-vertical-demo"
      router
    >
      <!-- 递归生成动态多级菜单 -->
      <menu-item :menuData="menuData"></menu-item>
    </el-menu>
  </div>
</template>

<script>
import MenuItem from "@/components/MenuItem/index.vue";

export default {
  name: "Sidebar",
  components: {
    MenuItem,
  },
  computed:{
    currentSidebarMenuHighlightIndex(){
      return this.$store.state.sidebar.currentSidebarMenuHighlightIndex
    }
  },
  data() {
    return {
      isCollapse: false,
      //侧边栏数据
      menuData: [
        {
          id: 101,
          menuName: "服务管理",
          icon: "el-icon-s-home",
          children: [
            {
              id:1001,
              path: "/service/list",
              component: "../views/service/list/index.vue",
              menuName: "服务列表",
              icon: "el-icon-s-home",
              children:[]
            },
          ],
        },
        {
          id: 201,
          menuName: "配置管理",
          icon: "el-icon-s-home",
          children: [
            {
              id: 2001,
              path: "/service/list",
              component: "../views/service/list/index.vue",
              menuName: "配置列表",
              icon: "el-icon-s-home",
              children:[]
            },
          ],
        },
      ],
      //当前高亮的菜单标签,为null表示默认的tag（仪表盘）高亮
      currentMenuTag: null,
    };
  },
};
</script>

<style scoped>
/* .el-aside {
  color: #333;
} */

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-menu {
  height: 100%;
  border: none;
}
</style>