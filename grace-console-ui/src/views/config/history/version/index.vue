<template>
  <div class="config-history-version-box">
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
        >历史版本</span
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
        <el-button type="primary" size="medium" @click="createConfig"
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
          v-model="queryCondition.dataId"
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
          v-model="queryCondition.openFuzzyQuery"
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
        <el-button size="medium" @click="oppositeShowAdvancedQueryCondition"
          >高级查询
          <!-- 向下的箭头(如果高级查询条件“没有展示”的话则触发这个) -->
          <i
            v-if="showAdvancedQueryCondition == false"
            class="el-icon-arrow-down"
          ></i>

          <!-- 向上的箭头(如果高级查询条件“正在展示”的话则触发这个) -->
          <i
            v-if="showAdvancedQueryCondition == true"
            class="el-icon-arrow-up"
          ></i>
        </el-button>
      </el-col>

      <!-- 导入配置按钮 -->
      <el-col :span="2" style="margin-top: 10px; margin-right: 30px">
        <el-button
          type="primary"
          size="medium"
          @click="clickOpenImportConfigDialog"
          >导入配置</el-button
        >
      </el-col>

      <!-- 最右侧的用于创建配置的“+”号 -->
      <el-col :span="1" style="margin-top: 10px">
        <i class="el-icon-plus create-config-plus" @click="createConfig"></i>
      </el-col>
    </el-row>

    <!-- 表格内容  -->
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="multipleSelection"
    >
      <!-- 多选框 -->
      <el-table-column type="selection" width="55"></el-table-column>
      <!-- dataId -->
      <el-table-column prop="dataId" label="Data Id" width="290" sortable>
      </el-table-column>
      <!-- 分组名称 -->
      <el-table-column prop="groupName" label="分组名称" width="250" sortable>
      </el-table-column>
      <!-- 归属应用 -->
      <el-table-column
        prop="formApplication"
        label="归属应用"
        width="204"
        sortable
      >
      </el-table-column>

      <!-- 操作 -->
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <!-- 配置详情 -->
          <span class="operation" @click="configDetail(scope.row.id)"
            >详情</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 示例代码 -->
          <span class="operation" @click="sampleCode(scope.row.id)"
            >示例代码</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 修改/编辑配置 -->
          <span class="operation" @click="modifyConfig(scope.row.id)"
            >编辑</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 删除配置 -->
          <span class="operation" @click="clickOpenDeleteDialog(scope.row)"
            >删除</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- “更多” 下拉菜单 -->
          <el-dropdown
            trigger="click"
            @command="clickMoreDropdownItem"
            placement="bottom-start"
          >
            <!-- “更多” 选项 -->
            <span class="operation">更多</span>
            <!-- 点击“更多”选项,展开的下拉菜单  -->
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="1">历史版本</el-dropdown-item>
              <el-dropdown-item command="2">监听查询</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!-- 表格底部 -->
    <el-row :gutter="24" style="margin-top: 25px">
      <!-- 分页 -->
      <el-col :span="12" :offset="6">
        <div class="grid-content bg-purple">
          <el-pagination
            background
            layout="sizes, prev, pager, next"
            prev-text="上一页"
            next-text="下一页"
            :page-sizes="[10, 20, 30, 50, 100]"
            :total="totalCount"
            :page-size="pagesize"
            :current-page="currentPage"
            @size-change="handlePageSizeChange"
            @current-change="handleCurrentPageChange"
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";

export default {
  name: "ConfigHistoryVersion",
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
      },
      // 配置列表数据
      tableData: [],
      // 总记录数
      totalCount: 0,
      // 每页展示的数量
      pagesize: 7,
      // 当前页
      currentPage: 1,
      // 是否打开历史版本（同一个namespace、DataId、groupName的配置）比较dialog
      openHistoryVersionCompareDialog: false,
      // 历史版本（同一个namespace、DataId、groupName的配置）比较dialog
      openHistoryVersionCompareDialog: {
        // 当前选择的版本的配置内容
        currentSelectedVersionConfigContent: '',
        // 最新版本的配置内容
        latestVersionConfigContent: ''
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
  created() {
    // 加载数据
    this.loadData();
  },
  methods: {
    // vue2-ace-editor代码编辑器初始化(下面的额外配置（例如主题、语言等）可以在node_modules\brace文件夹找 ,然后导入即可)
    editorInit() {
      // 语言工具
      require("brace/ext/language_tools");

      // 主题样式可看: https://www.cnblogs.com/summer-qd/p/15305746.html
      // 主题（可选chrome（白）、dawn（白）、tomorrow_night（黑）、dracula（黑）、monokai（黑）等）
      require("brace/theme/tomorrow_night");
      require("brace/theme/chrome");

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
      // console.log('loadData')
      // 每页展示的数量
      let pageSize = this.pagesize;
      // 当前页
      let currentPage = this.currentPage;
      // 根据上面的属性从后端分页的获取tableData数据
      let result = {
        code: 200,
        data: {
          // 分页查询出来的数据
          tableData: [
            {
              id: 10001,
              // 配置文件全名（例如application-dev.yaml）
              dataId: "application1-dev.yaml",
              // 分组名称
              groupName: "DEFAULT_GROUP",
              // 归属应用
              formApplication: "",
            },
            {
              id: 10002,
              // 配置文件全名（例如userservice-dev.yaml）
              dataId: "userservice2-test.properties",
              // 分组名称
              groupName: "DEFAULT_GROUP",
              // 归属应用
              formApplication: "",
            },
          ],
          // 所有数据的总数（没有分页）
          totalCount: 70,
        },
      };

      // 将数据放到vue中
      this.tableData = result.data.tableData;
      this.totalCount = result.data.totalCount;
    },
    // 点击切换命名空间
    namespaceToggle(selectedNamespaceId) {
      this.currentSelectedNamespaceId = selectedNamespaceId;
    },
    // 跳转到配置历史版本详情路由
    ConfigHistoryVersionDetail() {
      this.$router.push({
        path: "/config/history/version/detail",
      });
    },
    // 跳转到配置回滚路由
    ConfigRollback() {
      this.$router.push({
        path: "/config/history/version/rollback",
      });
    },
    // 点击打开历史版本的比较dialog
    clickOpenHistoryVersionCompareDialog(){
        this.openHistoryVersionCompareDialog = true;
    }

  },
};
</script>

<style>
</style>