<template>
  <div class="service-detail-content">
    <!-- 标题 -->
    <!-- <div
      style="
        display: flex;
        align-items: center;
        margin-top: 8px;
        margin-bottom: 8px;
      "
    >
      <span style="font-size: 28px; height: 40px; font-weight: 500"
        >服务详情</span
      >
    </div> -->

    <el-row :gutter="24">
      <el-col :span="6">
        <span style="font-size: 28px; height: 40px; font-weight: 500"
          >服务详情</span
        >
      </el-col>
      <el-col :span="6" :offset="12">
        <span style="font-size: 28px; height: 40px; font-weight: 500">
          <el-button size="medium" style="margin-right: 10px"
            >编辑服务</el-button
          >
          <el-button type="primary" size="medium">返回</el-button>
        </span>
      </el-col>
    </el-row>

    <!-- 表单  -->

    <el-form
      :model="changeServiceForm"
      :rules="changeServiceRules"
      label-width="150px"
    >
      <el-form-item label="服务名称" prop="serviceName">
        <el-input
          v-model="changeServiceForm.serviceName"
          autocomplete="off"
          style="width: 70%"
        ></el-input>
      </el-form-item>
      <el-form-item label="分组名称" prop="groupName">
        <el-input
          v-model="changeServiceForm.groupName"
          autocomplete="off"
          placeholder="DEFAULT_GROUP"
          style="width: 70%"
        ></el-input>
      </el-form-item>
      <el-form-item label="保护阈值" prop="protectionThreshold">
        <el-input
          v-model="changeServiceForm.protectionThreshold"
          autocomplete="off"
          style="width: 70%"
        ></el-input>
      </el-form-item>
      <el-form-item label="元数据" prop="metadata">
        <!-- 代码编辑器 -->
        <editor
          v-model="changeServiceForm.metadata"
          lang="json"
          theme="tomorrow_night"
          width="70%"
          height="320"
          @init="editorInit"
          :options="editorOptions"
        ></editor>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";

export default {
  name: "ServiceDetailContent",
  components: {
    Editor,
  },
  data() {
    return {
      // 创建服务表单
      changeServiceForm: {
        // 服务名称
        serviceName: "",
        // 分组名称
        groupName: "",
        // 保护阈值
        protectionThreshold: "",
        // 元数据
        metadata: "",
      },
      changeServiceRules: {
        // 服务名称
        serviceName: [
          { required: true, message: "请输入服务名称", trigger: "blur" },
        ],
        // 保护阈值（纯整数（123）和小数（567.6512））
        protectionThreshold: [
          {
            required: true,
            pattern: /^(\-|\+)?\d+(\.\d+)?$/,
            message: "请输入保护阈值",
            trigger: "blur",
          },
        ],
      },
      // vue2-ace-editor代码编辑器配置
      editorOptions: {
        // 启用基本自动完成
        enableBasicAutocompletion: true,
        //启用实时自动完成
        enableLiveAutocompletion: true,
        // 是否只读
        readOnly: false,
        // 启用代码段
        enableSnippets: true,
        // 高亮配置
        highlightActiveLine: true,
        //标签大小
        tabSize: 2,
        //设置字号
        fontSize: 18,
        //去除编辑器里的竖线
        showPrintMargin: false,
        // 超出内容的滚动范围
        scrollPastEnd: 0.1,
      },
    };
  },
  methods:{
    // vue2-ace-editor代码编辑器初始化(下面的额外配置（例如主题、语言等）可以在node_modules\brace文件夹找 ,然后导入即可)
    editorInit() {
      // 语言工具
      require("brace/ext/language_tools");

      // 主题（可选chrome（白）、dawn（白）、tomorrow_night（黑）、dracula（黑）、monokai（黑）等）
      require("brace/theme/tomorrow_night");

      // 编译器语言（可选html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/mode/json");

      // 编译器代码段（html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/snippets/json");
    },
  }
};
</script>

<style>
</style>