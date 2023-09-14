<template>
  <div class="service-detail-box">
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
            <service-detail-content/>
          </el-main>
        </el-container>
        
      </el-container>
    </el-container>
  </div>
</template>

<script>
import PageHeader from "../../../layout/components/PageHeader";
import ServiceDetailContent from '.././../../components/service/detail/content/index.vue'


export default {
  name: "ServiceDetail",
  components: {
    PageHeader,
    ServiceDetailContent
  },
  methods: {
    back() {},
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
</style>