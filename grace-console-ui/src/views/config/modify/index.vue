<template>
  <div class="config-modify-box">
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
            <config-modify-content
              :readOnlyConfigForm="readOnlyConfigForm"
              :modifyConfigForm="modifyConfigForm"
              :modifyConfigFormRules="modifyConfigFormRules"
              @initModifyConfigForm="initModifyConfigForm"
              @changeConfigTypeCallback="changeConfigTypeCallback"
              @publishConfig="publishConfig"
            />
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import PageHeader from "@/layout/components/PageHeader/index.vue";
import ConfigModifyContent from "@/components/config/modify/content/index.vue";
import { getConfig,publishConfig } from '@/api/config'

export default {
  name: "ConfigCreate",
  components: {
    PageHeader,
    ConfigModifyContent,
  },
  data() {
    return {
      // 只读的配置表单（这些属性都是不能修改的,只能用于展示给用户）
      readOnlyConfigForm: {
        // 命名空间
        namespaceId: "",
        // dataId
        dataId: "",
        // 分组名称
        groupName: "",
      },
      // 可修改配置表单
      modifyConfigForm: {
        // 配置描述
        configDesc: "",
        // 配置格式
        type: "yaml",
        // 配置内容
        content: "",
      },
      // 可修改配置表单的规则
      modifyConfigFormRules: {
        // 配置描述
        configDesc: [
          { required: true, message: "请输入描述", trigger: "blur" },
        ],
        // 配置内容
        content: [
          { required: true, message: "请输入配置内容", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    // 初始化modifyConfigForm数据
    initModifyConfigForm() {
      // 拿到配置的命名空间
      let namespaceId = this.$route.query.namespaceId;
      let groupName = this.$route.query.groupName;
      let dataId = this.$route.query.dataId;
      // 只读的配置表单（这些属性都是不能修改的,只能用于展示给用户）
      this.readOnlyConfigForm = {
        // 命名空间
        namespaceId: namespaceId,
        // dataId
        dataId: dataId,
        // 分组名称
        groupName: groupName,
      };

      // 请求api加载数据
      getConfig(namespaceId,groupName,dataId).then((response) => {
        let result = response.data;
        if(result.data != null){
          // 从数据库中查询配置信息。可以进行修改的表单。
          this.modifyConfigForm = {
            // 配置描述
            configDesc: result.data.configDesc,
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
    // 切换配置格式回调方法(selectConfigType是我们最新选择的配置格式)
    changeConfigTypeCallback(selectConfigType){
      // 更新创建配置表单的配置格式
      this.modifyConfigForm.type = selectConfigType;
    },
    // 发布配置
    publishConfig() {
      let publishConfigDTO = {
        // 命名空间
        namespaceId: this.readOnlyConfigForm.namespaceId,
        // dataId
        dataId: this.readOnlyConfigForm.dataId,
        // 分组名称
        groupName: this.readOnlyConfigForm.groupName,
        // 配置描述
        configDesc: this.modifyConfigForm.configDesc,
        // 配置格式
        type: this.modifyConfigForm.type,
        // 配置内容
        content: this.modifyConfigForm.content,   
      }
      // 调用aoi接口
      publishConfig(publishConfigDTO).then((response) => {
        let result = response.data;
        // 如果发布成功
        if(result.data == true){
          this.$message.success("发布配置成功");
          this.$router.push({
            path:'/config/list'
          })
        }else{
          this.$message.error("发布配置失败");
        }

      })
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