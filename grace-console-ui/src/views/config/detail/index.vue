<template>
  <div class="config-detail-box">
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
            <config-detail-content
            :configForm="configForm"
            />
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import PageHeader from "@/layout/components/PageHeader/index.vue";
import ConfigDetailContent from ".././../../components/config/detail/content/index.vue";
import { getConfig } from '@/api/config'


export default {
  name: "ConfigDetail",
  components: {
    PageHeader,
    ConfigDetailContent,
  },
  created(){
    this.initConfigForm();
  },
  data() {
    return {
      // 配置表单
      configForm: {
        // 命名空间
        namespaceId: "",
        // dataId
        dataId: "",
        // 分组名称
        groupName: "",
        // 配置描述
        configDesc: "",
        // md5
        md5:'',
        // 配置格式
        type: "yaml",
        // 配置内容
        content: "",
      },
    };
  },
  methods: {
    // 初始化modifyConfigForm数据
    initConfigForm() {
      // 拿到配置的命名空间
      let namespaceId = this.$route.query.namespaceId;
      let groupName = this.$route.query.groupName;
      let dataId = this.$route.query.dataId;
      // 请求api加载数据
      getConfig(namespaceId,groupName,dataId).then((response) => {
        let result = response.data;
        if(result.data != null){
          // 从数据库中查询配置信息。
          this.configForm = {
            // 命名空间
            namespaceId: namespaceId,
            // dataId
            dataId: dataId,
            // 分组名称
            groupName: groupName,
            // 配置描述
            configDesc: result.data.configDesc,
            // md5
            md5:result.data.md5,
            // 配置格式
            type: result.data.type,
            // 配置内容
            content: result.data.content,
          };
        }
      })
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