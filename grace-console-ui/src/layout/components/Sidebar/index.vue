<template>
  <div class="sidebar-box">
    <el-menu
      :default-openeds="defaultOpeneds"
      :default-active="currentSidebarMenuHighlightIndex"
      class="el-menu-vertical-demo"
      router
    >
      <!-- 递归生成动态多级菜单 -->
      <menu-item :sidebarMenu="sidebarMenu"></menu-item>
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
      // 默认打开的 sub-menu 的 index 的数组
      defaultOpeneds: ['101'],
      // 侧边栏菜单
      sidebarMenu: [],
      //当前高亮的菜单标签,为null表示默认的tag（仪表盘）高亮
      currentMenuTag: null,
    };
  },
  methods:{
    // 初始化侧边栏菜单
    initSidebarMenu(){
       //从vuex中获取侧边栏菜单并赋值给this.sidebarMenu对象展示到页面
       this.sidebarMenu=this.$store.state.sidebar.sidebarMenu
    }
  },
  created(){
    // 初始化侧边栏菜单
    this.initSidebarMenu();
  }
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