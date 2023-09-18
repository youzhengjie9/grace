<template>
  <div class="config-list-box">
    <!-- 标题 -->
    <div
      style="
        display: flex;
        align-items: center;
        margin-top: 8px;
        margin-bottom: 8px;
      "
    >
      <span style="font-size: 28px; height: 40px; font-weight: 500"
        >配置列表</span
      >
    </div>

    <!-- 命名空间切换 -->
    <div class="namespace-toggle">
      <div
        style="cursor: pointer"
        v-for="(namespace, index) in namespaceData"
        :key="namespace.id"
      >
        <!-- 没有高亮显示（也就是当前选择的命名空间不是这个） -->
        <span
          style="
            color: rgb(102, 102, 102);
            padding-right: 10px;
            border: none;
            font-size: 14px;
          "
          v-if="namespace.id != currentSelectedNamespaceId"
          @click="namespaceToggle(namespace.id)"
          >{{ namespace.namespaceName }}</span
        >

        <!-- 高亮显示（也就是当前选择的命名空间是这个） -->
        <span
          style="
            color: rgb(32, 155, 250);
            padding-right: 10px;
            border: none;
            font-size: 14px;
          "
          v-if="namespace.id == currentSelectedNamespaceId"
          >{{ namespace.namespaceName }}</span
        >

        <!-- 分隔符 -->
        <span
          style="margin-right: 8px; color: rgb(153, 153, 153)"
          v-if="namespaceData.length > 1 && index < namespaceData.length - 1"
        >
          |
        </span>
      </div>
    </div>

    <!-- 工具栏 -->
    <el-row :gutter="24">
      <!-- 创建配置按钮 -->
      <el-col :span="2" style="margin-top: 10px; margin-right: 8px">
        <el-button
          type="primary"
          size="medium"
          @click="clickOpenCreateServiceDialog"
          >创建配置</el-button
        >
      </el-col>
      <!-- 输入Data ID -->
      <el-col :span="5" style="margin-top: 7px">
        <span
          style="
            font-size: 14px;
            color: #666;
            font-weight: bold;
            margin-right: 9px;
          "
          >Data ID</span
        >
        <el-input
          v-model="queryCondition.serviceName"
          placeholder="已开启默认模糊查询"
          style="width: 168px; height: 30px"
        ></el-input>
      </el-col>

      <!-- 输入分组名称 -->
      <el-col :span="5" style="margin-top: 7px">
        <span
          style="
            font-size: 14px;
            color: #666;
            font-weight: bold;
            margin-right: 9px;
          "
          >分组名称</span
        >
        <el-input
          v-model="queryCondition.groupName"
          placeholder="已开启默认模糊查询"
          style="width: 168px; height: 30px"
        ></el-input>
      </el-col>

      <!--  模糊匹配开关  -->
      <el-col :span="3" style="margin-top: 12px">
        <span style="color: #666; font-weight: bold; margin-right: 10px"
          >模糊匹配</span
        >
        <el-switch
          v-model="queryCondition.hideEmptyService"
          active-color="#209bfa"
          inactive-color="#f5f5f5"
          :width="58"
        >
        </el-switch>
      </el-col>

      <!-- 查询按钮 -->
      <el-col :span="2" style="margin-top: 10px">
        <el-button type="primary" @click="query" size="medium">查询</el-button>
      </el-col>

      <!-- 高级查询按钮 -->
      <el-col :span="3" style="margin-top: 10px">
        <el-button
          size="medium"
          @click="clickOpenCreateServiceDialog"
          >高级查询
          <i class="el-icon-arrow-down"></i>
        </el-button>
      </el-col>

      <!-- 导入配置按钮 -->
      <el-col :span="2" style="margin-top: 10px; margin-right: 30px">
        <el-button
          type="primary"
          size="medium"
          @click="clickOpenCreateServiceDialog"
          >导入配置</el-button
        >
      </el-col>

      <!-- 最右侧的创建配置的“+”号 -->
      <el-col :span="1" style="margin-top: 10px">
        <i class="el-icon-plus create-config-plus"></i>
      </el-col>
    </el-row>

    <!-- 高级查询条件（默认是隐藏的） -->
    <el-row :gutter="24">
    
      <!-- 输入归属应用 -->
      <el-col :span="6" style="margin-top: 15px">
        <span
          style="
            font-size: 14px;
            color: #666;
            font-weight: bold;
            margin-right: 9px;
          "
          >归属应用</span
        >
        <el-input
          v-model="queryCondition.serviceName"
          placeholder="请输入应用名"
          style="width: 198px; height: 30px"
        ></el-input>
      </el-col>

      <!-- 输入标签 -->
      <el-col :span="5" style="margin-top: 15px">
        <span
          style="
            font-size: 14px;
            color: #666;
            font-weight: bold;
            margin-right: 9px;
          "
          >标签</span
        >
        <el-input
          v-model="queryCondition.serviceName"
          placeholder="请输入标签"
          style="width: 200px; height: 30px"
        ></el-input>
      </el-col>
    
      <!-- 输入配置项 -->
      <el-col :span="6" style="margin-top: 15px">
        <span
          style="
            font-size: 14px;
            color: #666;
            font-weight: bold;
            margin-right: 9px;
          "
          >配置项搜索</span
        >
        <el-input
          v-model="queryCondition.serviceName"
          placeholder="搜索具体配置项"
          style="width: 198px; height: 30px"
        ></el-input>
      </el-col>

    </el-row>

    <!-- 查询结果文字说明 -->
    <el-row :gutter="24">
      <el-col :span="24" style="margin-top: 30px; margin-right: 8px">
        <span>
          查询到 
          <strong style="font-weight: bold;">20</strong>
          条满足要求的配置。
        </span>
      </el-col>
    </el-row>


  </div>
</template>

<script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";
export default {
  name: "ConfigList",
  components: {
    Editor,
  },
  data() {
    return {
      // 命名空间数据
      namespaceData: [
        {
          id: 1,
          namespaceName: "public",
        },
        {
          id: 2,
          namespaceName: "dev",
        },
        {
          id: 3,
          namespaceName: "test",
        },
      ],
      // 当前选择的命名空间的id
      currentSelectedNamespaceId: 1,
      // 查询条件
      queryCondition: {
        // 配置文件全名（例如application-dev.yaml）
        dataId: "",
        // 分组名称
        groupName: "",
        // 是否开启模糊查询
        openFuzzyQuery: true,
      },
      // 表格数据
      tableData: [
        {
          id: 10001,
          // 配置文件全名（例如application-dev.yaml）
          dataId: "application-dev.yaml",
          // 分组名称
          groupName: "DEFAULT_GROUP",
        },
        {
          id: 10002,
          // 配置文件全名（例如userservice-dev.yaml）
          dataId: "userservice-test.properties",
          // 分组名称
          groupName: "DEFAULT_GROUP",
        },
      ],
      // 总记录数
      totalCount: 200,
      // 每页展示的数量
      pagesize: 10,
      // 当前页
      currentPage: 1,
      // 是否打开创建配置的对话框（dialog）
      openCreateConfigDialog: false,
      // 创建配置表单
      createConfigForm: {
        // 命名空间
        namespaceName: "",
        // 配置文件全名（例如application-dev.yaml）
        dataId: "",
        // 分组名称
        groupName: "",
        // 配置文件描述
        description: "",
        // 配置文件类型（例如text、json、yaml、html、properties、xml）
        type: "",
        // 配置内容
        content: "",
      },
      // 创建配置规则
      createConfigRules: {
        // 命名空间
        namespaceName: [
          { required: true, message: "请输入命名空间", trigger: "blur" },
        ],
        // 配置文件名
        dataId: [{ required: true, message: "请输入Data Id", trigger: "blur" }],
        // 分组名称
        groupName: [
          { required: true, message: "请输入分组名称", trigger: "blur" },
        ],
        // 配置内容
        content: [
          { required: true, message: "请输入配置内容", trigger: "blur" },
        ],
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
      require("brace/mode/json");

      // 编译器代码段（html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/snippets/json");
    },
    // 点击切换命名空间
    namespaceToggle(selectedNamespaceId) {
      this.currentSelectedNamespaceId = selectedNamespaceId;
    },
    // 点击查询
    query() {
      console.log(this.queryCondition);
    },
    // 跳转配置详情
    configDetail(id) {
      this.$router.push({
        path: "/config/detail",
        query: {
          id: id,
        },
      });
    },
    // 删除配置
    deleteConfig(id) {
      console.log(id);
    },
    // 跳转修改/编辑配置
    modifyDetail(id) {
      this.$router.push({
        path: "/config/detail",
        query: {
          id: id,
        },
      });
    },
    // 将创建/修改的配置进行发布
    publishConfig() {},
  },
};
</script>

<style scoped>
.namespace-toggle {
  padding: 5px 15px;
  overflow: hidden;
  background-color: #efefef;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-top: 8px;
  margin-bottom: 16px;
}

.operation {
  color: #06c;
  cursor: pointer;
  text-decoration: none;
  margin-right: 5px;
}

.operation:hover {
  text-decoration: underline;
}

/* 最右侧创建配置的“+”号样式 */
.create-config-plus {
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  -webkit-text-size-adjust: 100%;
  font-size: 18px;
  line-height: 1.5;
  margin: 0;
  display: inline-block;
  font-family: NextIcon;
  font-style: normal;
  font-weight: 400;
  text-transform: none;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
  color: black;
  margin-right: 0px;
  vertical-align: middle;
  cursor: pointer;
  background-color: rgb(238, 238, 238);
  border: 1px solid rgb(221, 221, 221);
  padding: 3px 6px;
  width: 35px;
  height: 35px;
}
</style>