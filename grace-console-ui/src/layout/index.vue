<template>
  <div class="layout-box">
    <el-container style="height: 500px;">
      <!-- 页头 -->
      <el-header style="text-align: right; font-size: 12px">
        <page-header />
      </el-header>

      <el-container>
        <!-- 侧边栏 -->
        <el-aside width="200px" style="height:560px;">
            <!-- 项目版本号 -->
            <p class="version">v2.0.0</p>
            <sidebar />
        </el-aside>

        <el-container>
          <!-- 嵌套路由的展示区域 -->
          <el-main>
            <router-view/>
          </el-main>
          <!-- 页尾 -->
          <el-footer>
            <page-footer/>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import PageHeader from "../layout/components/PageHeader/index.vue";
import Sidebar from "../layout/components/Sidebar/index.vue";
import PageFooter from '../layout/components/PageFooter/index.vue'

export default {
  name: "Layout",
  components: {
    PageHeader,
    Sidebar,
    PageFooter
  },
};
</script>

<style scoped>

.el-header {
  background-color: #252a2f;
  color: #fff;
  font-family: Avenir-Medium;
  line-height: 60px;
}

.version{
  font-size: 16px;
  color: #333;
  font-weight: 700;
  text-align: center;
}

.el-footer{

width: 90%;
text-align: center;

}

</style>