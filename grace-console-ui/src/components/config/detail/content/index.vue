<template>
  <div class="config-detail-content">
    <!-- 标题 -->
    <el-row :gutter="24">
      <el-col :span="24">
        <span style="font-size: 28px; height: 40px; font-weight: 500"
          >配置详情</span
        >
      </el-col>
    </el-row>

    <!-- 配置详情表单  -->
    <el-form
      :model="configDetailForm"
      label-width="113px"
      ref="configDetailForm"
      style="margin-top: 35px; margin-bottom: 60px"
    >
      <!-- 命名空间 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">命名空间</span>
        </span>
        <el-col :span="22">
          <el-input
            v-model="configDetailForm.namespaceName"
            :readonly="true"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- Data Id -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">Data Id</span>
        </span>
        <el-col :span="22">
          <el-input
            v-model="configDetailForm.dataId"
            :readonly="true"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- 分组名称 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">分组名称</span>
        </span>
        <el-col :span="22">
          <el-input
            v-model="configDetailForm.groupName"
            :readonly="true"
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
            v-model="configDetailForm.description"
            resize="none"
            :readonly="true"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- MD5 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">MD5</span>
        </span>
        <el-col :span="22">
          <el-input v-model="configDetailForm.md5" :readonly="true"></el-input>
        </el-col>
      </el-form-item>
      <!-- 配置格式 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">配置格式</span>
        </span>
        <el-col :span="22">
          <!-- 配置格式单选框组(只显示当前配置选择的格式的单选框,作用是让用户不能切换单选框) -->
          <el-radio-group v-model="configDetailForm.format">
            <el-radio v-if="configDetailForm.format == 'text'" label="text"
              >text</el-radio
            >
            <el-radio v-if="configDetailForm.format == 'json'" label="json"
              >json</el-radio
            >
            <el-radio
              v-if="configDetailForm.format == 'properties'"
              label="properties"
              >properties</el-radio
            >
            <el-radio v-if="configDetailForm.format == 'yaml'" label="yaml"
              >yaml</el-radio
            >
            <el-radio v-if="configDetailForm.format == 'xml'" label="xml"
              >xml</el-radio
            >
            <el-radio v-if="configDetailForm.format == 'html'" label="html"
              >html</el-radio
            >
          </el-radio-group>
        </el-col>
      </el-form-item>
      <!-- 配置内容 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">配置内容</span>
        </span>
        <!-- 代码编辑器 -->
        <editor
          v-model="configDetailForm.content"
          :lang="configDetailForm.format"
          theme="tomorrow_night"
          width="92%"
          height="320"
          @init="editorInit"
          :options="readOnlyEditorOptions"
        ></editor>
      </el-form-item>
      <!-- 底部 -->
      <el-row :gutter="24">
        <el-col :span="7" :offset="16">
          <span style="font-size: 28px; height: 40px; font-weight: 500">
            <!-- 配置对比按钮 -->
            <el-button size="medium" type="primary" style="margin-right: 10px"
              >配置对比</el-button
            >
            <!-- 版本对比按钮 -->
            <el-button size="medium" type="primary" style="margin-right: 10px"
              >版本对比</el-button
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
  name: "ConfigDetailContent",
  components: {
    Editor,
  },
  data() {
    return {
      // 配置详情表单
      configDetailForm: {
        // 命名空间
        namespaceName: "",
        // dataId
        dataId: "",
        // 分组名称
        groupName: "",
        // 描述
        description: "",
        // md5值
        md5: "",
        // 配置格式
        format: "",
        // 配置内容
        content: "",
      },
      // 只读的（不能进行编辑的）代码编辑器配置
      readOnlyEditorOptions: {
        // 是否只读
        readOnly: true,
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
    // 初始化configDetailForm对象
    initConfigDetailForm() {
      // 从路由的请求参数中获取配置id
      let configId = this.$route.query.configId;
      // 通过配置id去数据库中查询该配置的数据，并赋值给configDetailForm对象
      this.configDetailForm = {
        // 命名空间
        namespaceName: configId,
        // dataId
        dataId: "123",
        // 分组名称
        groupName: "22",
        // 描述
        description: "33",
        // md5值
        md5: "abc123456789",
        // 配置格式
        format: "json",
        // 配置内容
        content: "{'a':666}",
      };
    },
    // 返回上一个页面
    back() {
      this.$router.go(-1);
    },
  },
  created() {
    // 初始化configDetailForm对象
    this.initConfigDetailForm();
  },
};
</script>

<!-- 局部样式 -->
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