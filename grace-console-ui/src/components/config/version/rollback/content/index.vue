<template>
  <div class="config-version-rollback-content">
    <!-- 标题 -->
    <el-row :gutter="24">
      <el-col :span="24">
        <span style="font-size: 28px; height: 40px; font-weight: 500"
          >配置回滚</span
        >
      </el-col>
    </el-row>

    <!-- 版本回滚表单  -->
    <el-form
      :model="versionRollbackForm"
      label-width="113px"
      ref="versionRollbackForm"
      style="margin-top: 35px; margin-bottom: 60px"
    >
      <!-- 命名空间 -->
      <el-form-item>
        <!-- 标题 -->
        <span slot="label">
          <span style="font-weight: bold">命名空间id</span>
        </span>
        <el-col :span="22">
          <el-input
            v-model="versionRollbackForm.namespaceId"
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
            v-model="versionRollbackForm.groupName"
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
            v-model="versionRollbackForm.dataId"
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
            v-model="versionRollbackForm.operationType"
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
          <el-input
            v-model="versionRollbackForm.md5"
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
            v-model="versionRollbackForm.configDesc"
            :readonly="true"
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
          <!-- 配置格式单选框组(只显示当前配置选择的格式的单选框,作用是让用户不能切换单选框) -->
          <el-radio-group v-model="versionRollbackForm.type">
            <el-radio v-if="versionRollbackForm.type == 'text'" label="text"
              >text</el-radio
            >
            <el-radio v-if="versionRollbackForm.type == 'json'" label="json"
              >json</el-radio
            >
            <el-radio
              v-if="versionRollbackForm.type == 'properties'"
              label="properties"
              >properties</el-radio
            >
            <el-radio v-if="versionRollbackForm.type == 'yaml'" label="yaml"
              >yaml</el-radio
            >
            <el-radio v-if="versionRollbackForm.type == 'xml'" label="xml"
              >xml</el-radio
            >
            <el-radio v-if="versionRollbackForm.type == 'html'" label="html"
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
          v-model="versionRollbackForm.content"
          :lang="versionRollbackForm.type"
          theme="tomorrow_night"
          width="92%"
          height="320"
          @init="editorInit"
          :options="readOnlyEditorOptions"
        ></editor>
      </el-form-item>
      <!-- 底部 -->
      <el-row :gutter="24">
        <el-col :span="4" :offset="18">
          <span style="font-size: 28px; height: 40px; font-weight: 500">
            <el-button type="primary" size="medium" @click="clickOpenVersionRollbackDialog"
              >回滚配置</el-button
            >
            <el-button size="medium" @click="back">返回</el-button>
          </span>
        </el-col>
      </el-row>
    </el-form>


    <!-- 配置版本回滚的dialog  -->
    <el-dialog :visible.sync="openVersionRollbackDialog" top="15vh" width="30%">
      <!-- 标题插槽 -->
      <div slot="title">
        <i
          class="el-icon-circle-check"
          style="color: #f1c826; font-size: 23px; margin-right: 5px"
        ></i>
        <span style="font-size: 20px">配置回滚</span>
      </div>
      <!-- 内容 -->
      <el-row :gutter="24" style="margin-left: 30px">
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px"
            >确定要回滚以下配置吗？</span
          >
        </el-col>
        <!-- Data Id -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">Data Id: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ versionRollbackForm.dataId }}
          </span>
        </el-col>

        <!-- 分组名称 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">分组名称:</span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ versionRollbackForm.groupName }}
          </span>
        </el-col>
      </el-row>
      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="versionRollback">确定</el-button>
        <el-button @click="openVersionRollbackDialog = false">取消</el-button>
      </div>
    </el-dialog>



  </div>
</template>
  
  <script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";
import { getConfigVersion, rollbackConfig } from "@/api/configVersion";

export default {
  name: "ConfigVersionRollbackContent",
  components: {
    Editor,
  },
  data() {
    return {
      // 配置版本id
      versionId: '',
      // 版本回滚表单
      versionRollbackForm: {
        // 命名空间
        namespaceName: "",
        // 分组名称
        groupName: '',
        // dataId
        dataId: "",
        // 操作类型
        operationType: "",
        // MD5值
        md5: "",
        // 配置描述
        configDesc: '',
        // 配置格式
        type: "",
        // 配置内容
        content: "",
      },
      // 是否打开配置回滚dialog
      openVersionRollbackDialog: false,
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
    // 加载数据
    loadData() {
      // 从路由的请求参数中获取配置版本id
      this.versionId = this.$route.query.versionId;
      let versionId = this.versionId;
      // 通过配置版本id去数据库中查询该版本的信息，并赋值给versionRollbackForm对象
      getConfigVersion(versionId).then((response) => {
        let result = response.data;
        this.versionRollbackForm = {
          // 命名空间
          namespaceId: result.data.namespaceId,
          // 分组名称
          groupName: result.data.groupName,
          // dataId
          dataId: result.data.dataId,
          // 操作类型
          operationType: result.data.operationType,
          // MD5值
          md5: result.data.md5,
          // 配置描述
          configDesc: result.data.configDesc,
          // 配置格式
          type: result.data.type,
          // 配置内容
          content: result.data.content,
        };
      });
    },
    // 点击打开配置回滚dialog
    clickOpenVersionRollbackDialog() {
      // 打开dialog
      this.openVersionRollbackDialog = true;
    },
    // 配置回滚
    versionRollback() {
      let versionId = this.versionId;
      rollbackConfig(versionId).then((response) => {
        let result = response.data;
        if(result.data == true){
          this.$message.success('配置回滚成功');
          this.openVersionRollbackDialog = false;
        }else{
          this.$message.error('配置回滚失败');
        }
      })

    },
    // 返回上一个页面
    back() {
      this.$router.go(-1);
    },
  },
  created() {
    // 加载数据
    this.loadData();
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