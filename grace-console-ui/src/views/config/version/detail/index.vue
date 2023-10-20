<template>
    <div class="config-version-detail-box">
      <el-container style="height: 500px">
        <!-- 页头 -->
        <el-header style="text-align: right; font-size: 12px">
          <page-header />
        </el-header>
        <el-container>
          <!-- 侧边 -->
          <el-aside
            width="200px"
            style="
              border-right: 1px solid #eee;
              background: #fff;
              box-shadow: none;
            "
          >
            <!-- 返回上个页面 -->
            <p style="text-align: center">
              <i class="el-icon-arrow-left" @click="back"></i>
            </p>
          </el-aside>
  
          <el-container>
            <!-- 右侧内容 -->
            <el-main>
              <config-version-detail-content/>
            </el-main>
          </el-container>
          
        </el-container>
      </el-container>
    </div>
  
  
  </template>
  
  <script>
  import PageHeader from "@/layout/components/PageHeader/index.vue";
  import ConfigVersionDetailContent from '@/components/config/version/detail/content/index.vue'
  
  export default {
    name: "ConfigVersionDetail",
    components: {
      PageHeader,
      ConfigVersionDetailContent
    },
    methods: {
      back() {
        this.$router.go(-1);
      },
    },
  }
  </script>
  
  <style scoped>
  .el-header {
    background-color: #252a2f;
    color: #fff;
    font-family: Avenir-Medium;
    line-height: 60px;
  }
  </style>