<template>
  <div class="config-modify-content">
    <!-- 标题 -->
    <el-row :gutter="24">
      <el-col :span="24">
        <span style="font-size: 28px; height: 40px; font-weight: 500"
          >编辑配置</span
        >
      </el-col>
    </el-row>

    <!-- 修改配置表单  -->
    <el-form
      :model="modifyConfigForm"
      :rules="modifyConfigFormRules"
      label-width="113px"
      ref="modifyConfigForm"
      style="margin-top: 35px; margin-bottom: 60px"
    >
      <!-- 命名空间 -->
      <el-form-item prop="namespaceName">
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">命名空间</span>
        </span>
        <el-col :span="22">
          <el-input
            v-model="readOnlyConfigForm.namespaceName"
            autocomplete="off"
            :disabled="true"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- Data Id -->
      <el-form-item prop="dataId">
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">Data Id</span>
        </span>
        <el-col :span="22">
          <el-input
            v-model="readOnlyConfigForm.dataId"
            autocomplete="off"
            :disabled="true"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- 分组名称 -->
      <el-form-item prop="groupName">
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">分组名称</span>
        </span>
        <el-col :span="22">
          <el-input
            v-model="readOnlyConfigForm.groupName"
            autocomplete="off"
            :disabled="true"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- 描述 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">描述</span>
        </span>
        <el-col :span="22">
          <el-input
            type="textarea"
            :rows="3"
            v-model="modifyConfigForm.description"
            autocomplete="off"
            resize="none"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- 配置格式 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">配置格式</span>
        </span>
        <el-col :span="22">
          <!-- 配置格式单选框组 -->
          <el-radio-group
            v-model="modifyConfigForm.format"
            @input="changeConfigFormatCallback"
          >
            <el-radio label="text">text</el-radio>
            <el-radio label="json">json</el-radio>
            <el-radio label="properties">properties</el-radio>
            <el-radio label="yaml">yaml</el-radio>
            <el-radio label="xml">xml</el-radio>
            <el-radio label="html">html</el-radio>
          </el-radio-group>
        </el-col>
      </el-form-item>
      <!-- 配置内容 -->
      <el-form-item prop="content">
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">配置内容</span>
        </span>
        <!-- 代码编辑器 -->
        <editor
          v-model="modifyConfigForm.content"
          :lang="modifyConfigForm.format"
          theme="tomorrow_night"
          width="92%"
          height="320"
          @init="editorInit"
          :options="writableEditorOptions"
        ></editor>
      </el-form-item>
      <!-- 底部 -->
      <el-row :gutter="24">
        <el-col :span="4" :offset="18">
          <span style="font-size: 28px; height: 40px; font-weight: 500">
            <el-button
              size="medium"
              type="primary"
              style="margin-right: 10px"
              @click="publishConfig('modifyConfigForm')"
              >发布配置</el-button
            >
            <el-button size="medium" @click="back">返回</el-button>
          </span>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";

export default {
  name: "ConfigModifyContent",
  components: {
    Editor,
  },
  data() {
    return {
      // 只读的配置表单（这些属性都是不能修改的,只能用于展示给用户）
      readOnlyConfigForm:{
        // 命名空间
        namespaceName: "public",
        // dataId
        dataId: "",
        // 分组名称
        groupName: "",
      },
      // 修改配置表单
      modifyConfigForm: {
        // 描述
        description: "",
        // 配置格式
        format: "text",
        // 配置内容
        content: "",
      },
      // 修改配置表单的规则
      modifyConfigFormRules: {
        // 描述
        description: [
          { required: true, message: "请输入描述", trigger: "blur" },
        ],
        // 配置内容
        content: [
          { required: true, message: "请输入配置内容", trigger: "blur" },
        ],
      },
      // 可写的（可以进行编辑的）代码编辑器配置
      writableEditorOptions: {
        // 启用基本自动完
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
  methods: {
    // vue2-ace-editor代码编辑器初始化(下面的额外配置（例如主题、语言等）可以在node_modules\brace文件夹找 ,然后导入即可)
    editorInit() {
      // 语言工具
      require("brace/ext/language_tools");

      // 主题（可选chrome（白）、dawn（白）、tomorrow_night（黑）、dracula（黑）、monokai（黑）等）
      require("brace/theme/tomorrow_night");

      // 编译器语言（可选html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/mode/text");
      require("brace/mode/json");
      require("brace/mode/properties");
      require("brace/mode/yaml");
      require("brace/mode/xml");
      require("brace/mode/html");

      // 编译器代码段（html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/snippets/text");
      require("brace/snippets/json");
      require("brace/snippets/properties");
      require("brace/snippets/yaml");
      require("brace/snippets/xml");
      require("brace/snippets/html");

    },
    // 初始化modifyConfigForm数据
    initModifyConfigForm(){
        // 拿到配置的id
        let configId = this.$route.query.configId;
        // 根据配置的id从数据库中查询配置信息。只读的配置表单（这些属性都是不能修改的,只能用于展示给用户）
        this.readOnlyConfigForm = {
            // 命名空间
            namespaceName: "public",
            // dataId
            dataId: 'configid='+configId,
            // 分组名称
            groupName: "123",
        };
        // 根据配置的id从数据库中查询配置信息。可以进行修改的表单。
        this.modifyConfigForm = {
            // 描述
            description: "1236",
            // 配置格式
            format: "json",
            // 配置内容
            content: '666',
        }
    },
    // 返回上一个页面
    back() {
      this.$router.go(-1);
    },
    // 切换配置格式回调方法(selectConfigFormat是我们最新选择的配置格式)
    changeConfigFormatCallback(selectConfigFormat){

      // 更新创建配置表单的配置格式
      this.modifyConfigForm.format = selectConfigFormat;
      
    },
    // 发布配置
    publishConfig(modifyConfigForm) {
      this.$refs[modifyConfigForm].validate((valid) => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
  mounted(){
    // 初始化modifyConfigForm数据
    this.initModifyConfigForm();
  }
};
</script>

<style scoped>

</style>

<!-- 全局样式 -->
<style>
/* 解决Elementui输入框(text)与文本域(textarea)字体不一样问题（必须放到全局css中,也就是style标签上不加scoped） */
.el-textarea__inner {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
</style>