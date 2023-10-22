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
            <config-version-detail-content
              :versionDetailForm="versionDetailForm"
            />
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
  
  <script>
import PageHeader from "@/layout/components/PageHeader/index.vue";
import ConfigVersionDetailContent from "@/components/config/version/detail/content/index.vue";
import { getConfigVersion } from "@/api/configVersion";
// 导入moment格式化时间
import moment from 'moment'

export default {
  name: "ConfigVersionDetail",
  components: {
    PageHeader,
    ConfigVersionDetailContent,
  },
  created() {
    this.loadData();
  },
  data() {
    return {
      // 版本详情表单
      versionDetailForm: {
        // 命名空间id
        namespaceId: "",
        // 分组名称
        groupName: "",
        // dataId
        dataId: "",
        // 操作人的IP
        operationUserIp: "",
        // 操作类型
        operationType: "",
        // 配置描述
        configDesc: "",
        // 操作这个配置的时间
        operationTime: "",
        // 配置内容的MD5值
        md5: "",
        // 配置的类型
        type: "",
        // 配置内容
        content: "",
      },
    };
  },
  methods: {
    // 加载数据
    loadData() {
      // 从路由的请求参数中获取配置版本id
      let versionId = this.$route.query.versionId;
      getConfigVersion(versionId).then((response) => {
        let result = response.data;

        this.versionDetailForm = {
          // 命名空间id
          namespaceId: result.data.namespaceId,
          // 分组名称
          groupName: result.data.groupName,
          // dataId
          dataId: result.data.dataId,
          // 操作人的IP
          operationUserIp: result.data.operationUserIp,
          // 操作类型
          operationType: result.data.operationType,
          // 配置描述
          configDesc: result.data.configDesc,
          // 操作这个配置的时间(并使用moment格式化时间)
          operationTime: moment(result.data.operationTime).format("YYYY-MM-DD HH:mm:ss"),
          // 配置内容的MD5值
          md5: result.data.md5,
          // 配置的类型
          type: result.data.type,
          // 配置内容
          content: result.data.content,
        };
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