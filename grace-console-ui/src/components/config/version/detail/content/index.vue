<template>
    <div class="config-version-detail-content">
      <!-- 标题 -->
      <el-row :gutter="24">
        <el-col :span="24">
          <span style="font-size: 28px; height: 40px; font-weight: 500"
            >版本详情</span
          >
        </el-col>
      </el-row>
  
      <!-- 版本详情表单  -->
      <el-form
        :model="versionDetailForm"
        label-width="113px"
        ref="versionDetailForm"
        style="margin-top: 35px; margin-bottom: 60px"
      >
        <!-- 命名空间 -->
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">命名空间Id</span>
          </span>
          <el-col :span="22">
            <el-input
              v-model="versionDetailForm.namespaceId"
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
              v-model="versionDetailForm.groupName"
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
              v-model="versionDetailForm.dataId"
              :readonly="true"
            ></el-input>
          </el-col>
        </el-form-item>
        <!-- 操作人的IP -->
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">操作人的IP</span>
          </span>
          <el-col :span="22">
            <el-input
              v-model="versionDetailForm.operationUserIp"
              :readonly="true"
            ></el-input>
          </el-col>
        </el-form-item>
        <!-- 操作类型 -->
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">操作类型</span>
          </span>
          <el-col :span="22">
            <el-input
              v-model="versionDetailForm.operationType"
              :readonly="true"
            ></el-input>
          </el-col>
        </el-form-item>
        <!-- 配置描述 -->
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">配置描述</span>
          </span>
          <el-col :span="22">
            <el-input
              v-model="versionDetailForm.configDesc"
              :readonly="true"
            ></el-input>
          </el-col>
        </el-form-item>
        <!-- 配置时间 -->
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">配置时间</span>
          </span>
          <el-col :span="22">
            <el-input
              v-model="versionDetailForm.operationTime"
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
            <el-input v-model="versionDetailForm.md5" :readonly="true"></el-input>
          </el-col>
        </el-form-item>
        <!-- 配置类型 -->
        <el-form-item>
          <!-- 标题 -->
          <span slot="label">
            <span style="font-weight: bold">配置类型</span>
          </span>
          <el-col :span="22">
            <!-- 配置类型单选框组(只显示当前配置选择的类型的单选框,作用是让用户不能切换单选框) -->
            <el-radio-group v-model="versionDetailForm.type">
              <el-radio v-if="versionDetailForm.type == 'text'" label="text"
                >text</el-radio
              >
              <el-radio v-if="versionDetailForm.type == 'json'" label="json"
                >json</el-radio
              >
              <el-radio
                v-if="versionDetailForm.type == 'properties'"
                label="properties"
                >properties</el-radio
              >
              <el-radio v-if="versionDetailForm.type == 'yaml'" label="yaml"
                >yaml</el-radio
              >
              <el-radio v-if="versionDetailForm.type == 'xml'" label="xml"
                >xml</el-radio
              >
              <el-radio v-if="versionDetailForm.type == 'html'" label="html"
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
            v-model="versionDetailForm.content"
            :lang="versionDetailForm.type"
            theme="tomorrow_night"
            width="92%"
            height="320"
            @init="editorInit"
            :options="readOnlyEditorOptions"
          ></editor>
        </el-form-item>
        <!-- 底部 -->
        <el-row :gutter="24">
          <el-col :span="3" :offset="20">
            <span style="font-size: 28px; height: 40px; font-weight: 500">
              <el-button type="primary" size="medium" @click="back">返回</el-button>
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
    name: "ConfigVersionDetailContent",
    components: {
      Editor,
    },
    props:{
      versionDetailForm: Object
    },
    data() {
      return {
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
      // 返回上一个页面
      back() {
        this.$router.go(-1);
      },
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