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
            <service-detail-content :serviceDetail="serviceDetail" />
          </el-main>
        </el-container>
        
      </el-container>
    </el-container>
  </div>
</template>

<script>
import PageHeader from "@/layout/components/PageHeader/index.vue";
import ServiceDetailContent from '.././../../components/service/detail/content/index.vue'
import { getServiceDetail } from "@/api/service";

export default {
  name: "ServiceDetail",
  components: {
    PageHeader,
    ServiceDetailContent
  },
  data(){
    return{
      serviceDetail:{}
    }
  },
  mounted(){
    // 加载数据
    this.loadData();
  },
  methods: {
    // 加载数据
    loadData(){
      let namespaceId = this.$route.query.namespaceId;
      let groupName = this.$route.query.groupName;
      let serviceName = this.$route.query.serviceName;
      // 调用后端接口从数据库查询该服务详情的数据
      getServiceDetail(namespaceId, groupName, serviceName).then((response) => {
        let result = response.data;

        this.serviceDetail = result.data;

        // let serviceData = result.data;

        // this.serviceDetail = {
        //   // 命名空间id
        //   namespaceId: serviceData.namespaceId,
        //   // 分组名称
        //   groupName: serviceData.groupName,
        //   // 服务名称
        //   serviceName: serviceData.serviceName,
        //   // 保护阈值
        //   protectThreshold: serviceData.protectThreshold,
        //   // 元数据
        //   metadata: serviceData.metadata,
        // };
      });


    },
    back() {
      this.$router.go(-1);
    },
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