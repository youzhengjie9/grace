<template>
  <div class="config-create-box">
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
            <config-create-content 
            :createConfigForm="createConfigForm"
            :createConfigRules="createConfigRules"
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
import ConfigCreateContent from '@/components/config/create/content/index.vue'
import { publishConfig } from '@/api/config'

export default {
  name: "ConfigCreate",
  components: {
    PageHeader,
    ConfigCreateContent
  },
  data(){
    return {
       // 创建配置表单
       createConfigForm: {
        // 配置所属的命名空间id
        namespaceId: '',
        // 配置所属的分组名称
        groupName: 'DEFAULT_GROUP',
        // dataId。也就是配置的名称
        dataId: '',
        // 配置的内容
        content: '',
        // 配置的描述
        configDesc: '',
        // 配置的类型
        type: 'yaml',
      },
      // 创建配置表单规则
      createConfigRules: {
        // 分组名称
        groupName: [
          { required: true, message: "请输入分组名称", trigger: "blur" },
        ],
        // dataId
        dataId: [{ required: true, message: "请输入Data Id", trigger: "blur" }],
        // 配置内容
        content: [
          { required: true, message: "请输入配置内容", trigger: "blur" },
        ],
      },

    }
  },
  mounted(){
    this.loadData();
  },
  methods: {
    // 加载数据
    loadData(){
      this.createConfigForm.namespaceId=this.$route.query.namespaceId
    },
    back() {
      this.$router.go(-1);
    },
    // 切换配置格式回调方法(selectConfigType是我们最新选择的配置格式)
    changeConfigTypeCallback(selectConfigType){
      // 更新创建配置表单的配置格式
      this.createConfigForm.type = selectConfigType;
    },
    // 发布配置
    publishConfig() {
      // 调用aoi接口
      publishConfig(this.createConfigForm).then((response) => {
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