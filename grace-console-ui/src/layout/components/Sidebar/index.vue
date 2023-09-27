<template>
  <div class="sidebar-box">
    <!-- 侧边栏菜单(unique-opened为true说明只允许同时一个目录展开,如果打开多个目录,则其他目录会自动收缩回去) -->
    <el-menu
      :default-active="currentSidebarMenuHighlightPath"
      :unique-opened="true"
      class="el-menu-vertical-demo"
      router
    >
      <!-- 递归生成动态的多级菜单（侧边栏的菜单项） -->
      <sidebar-menu-item :sidebarMenu="sidebarMenu"></sidebar-menu-item>

    </el-menu>
  </div>
</template>

<script>
import SidebarMenuItem from "@/components/SidebarMenuItem/index.vue";

export default {
  name: "Sidebar",
  components: {
    SidebarMenuItem,
  },
  computed:{
    currentSidebarMenuHighlightPath(){
      return this.$store.state.sidebar.currentSidebarMenuHighlightPath
    }
  },
  data() {
    return {
      // 当前展开的侧边栏目录（sub-menu标签）的index数组。用于控制侧边栏目录的展开和收缩
      // currentOpenSidebarDirIndexArray: ['101'],
      // 侧边栏菜单
      sidebarMenu: [],
      // 当前高亮的菜单标签,为null表示默认的tag（仪表盘）高亮
      currentMenuTag: null,
    };
  },
  methods:{
    // 初始化侧边栏菜单
    initSidebarMenu(){
       //从vuex中获取侧边栏菜单并赋值给this.sidebarMenu对象展示到页面
       this.sidebarMenu=this.$store.state.sidebar.sidebarMenu;
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