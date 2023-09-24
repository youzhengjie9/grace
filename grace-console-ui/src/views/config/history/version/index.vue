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
      <!-- 输入Data ID -->
      <el-col
        :span="6"
        style="margin-top: 7px; margin-bottom: 35px; margin-left: 10px"
      >
        <span
          style="
            font-size: 14px;
            color: #666;
            font-weight: bold;
            margin-right: 9px;
          "
          >Data ID</span
        >
        <!-- dataId的可搜索+可提供建议的输入框 -->
        <el-autocomplete
          v-model="queryCondition.dataId"
          :fetch-suggestions="getInputSuggestionsByDataId"
          placeholder="请输入Data ID"
          clearable
          @select="selectDataIdCallback">
          <!-- 展示输入框下面的dataId建议 -->
          <template slot-scope="{ item }">
            <div class="name">{{ item.dataId }}</div>
          </template>
        </el-autocomplete>
      </el-col>

      <!-- 输入分组名称 -->
      <el-col :span="6" style="margin-top: 7px">
        <span
          style="
            font-size: 14px;
            color: #666;
            font-weight: bold;
            margin-right: 9px;
          "
          >分组名称</span
        >
        <!-- 分组名称的可搜索+可提供建议的输入框 -->
        <el-autocomplete
          v-model="queryCondition.groupName"
          :fetch-suggestions="getInputSuggestionsByGroupName"
          placeholder="请输入分组名称"
          clearable
          @select="selectGroupNameCallback">
          <!-- 展示输入框下面的dataId建议 -->
          <template slot-scope="{ item }">
            <div class="name">{{ item.groupName }}</div>
          </template>
        </el-autocomplete>

      </el-col>

      <!-- 查询按钮 -->
      <el-col :span="2" style="margin-top: 10px">
        <el-button type="primary" @click="query" size="medium">查询</el-button>
      </el-col>
    </el-row>

    <!-- 表格内容  -->
    <el-table
      :data="tableData"
      border
      style="width: 100%; margin-bottom: 25px"
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
    >
      <!-- dataId -->
      <el-table-column prop="dataId" label="Data Id" width="290">
      </el-table-column>
      <!-- 分组名称 -->
      <el-table-column prop="groupName" label="分组名称" width="250">
      </el-table-column>
      <!-- 操作人 -->
      <el-table-column prop="operator" label="操作人" width="204">
      </el-table-column>

      <!-- 最后修改时间 -->
      <el-table-column prop="updateTime" label="最后修改时间" width="204">
      </el-table-column>

      <!-- 操作 -->
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <!-- 版本详情 -->
          <span
            class="operation"
            @click="ConfigHistoryVersionDetail(scope.row.id)"
            >版本详情</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 版本回滚 -->
          <span class="operation" @click="ConfigRollback(scope.row.id)"
            >版本回滚</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 版本比较 -->
          <span
            class="operation"
            @click="clickOpenHistoryVersionCompareDialog(scope.row.id)"
            >版本比较</span
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 历史版本的比较dialog -->
    <el-dialog
      :visible.sync="openHistoryVersionCompareDialog"
      top="15vh"
      width="30%"
    >
      <!-- 标题插槽 -->
      <div slot="title">
        <span style="font-size: 20px">历史版本比较</span>
      </div>

      <!-- 内容 -->
      <el-row :gutter="24" style="margin-left: 30px">
        <!-- 当前选择的版本 -->

        <!-- 最新版本 -->
      </el-row>

      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="openHistoryVersionCompareDialog = false"
          >返回</el-button
        >
      </div>
    </el-dialog>

    <!-- 表格底部 -->
    <el-row :gutter="24" style="margin-top: 25px">
      <!-- 分页 -->
      <el-col :span="12" :offset="12">
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
      // 数据库中所有dataId
      allDataIdsInDatabase:[],
      // 数据库中所有分组名称
      allGroupNamesInDatabase: [],
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
      // 历史版本比较dialog所需要的数据
      historyVersionCompareDialogData: {
        // 当前选择的版本的配置内容
        currentSelectedVersionConfigContent: "",
        // 最新版本的配置内容
        latestVersionConfigContent: "",
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
              // 操作人
              operator: "yzj",
              // 最后修改时间
              updateTime: "2023/9/24 07:47:18",
            },
            {
              id: 10002,
              // 配置文件全名（例如userservice-dev.yaml）
              dataId: "userservice2-test.properties",
              // 分组名称
              groupName: "DEFAULT_GROUP",
              // 操作人
              operator: "abc",
              // 最后修改时间
              updateTime: "2023/9/26 07:47:18",
            },
          ],
          // 所有数据的总数（没有分页）
          totalCount: 70,
        },
      };
      // 将数据放到vue中
      this.tableData = result.data.tableData;
      this.totalCount = result.data.totalCount;

      // 从后端服务器中查询在数据库中所有dataId
      this.allDataIdsInDatabase = [
        {
          "dataId": 'application.yaml'
        },
        {
          "dataId": 'application-test.yaml'
        },
        {
          "dataId": 'application-dev.yaml'
        },
        {
          "dataId": 'userservice.yaml'
        },
        {
          "dataId": 'application.properties'
        },
      ];

      // 从后端服务器中查询在数据库中所有分组名称
      this.allGroupNamesInDatabase = [
        {
          "groupName": 'DEFAULT_GROUP'
        },
        {
          "groupName": 'abc_group'
        },
      ]
    },
    // 点击切换命名空间
    namespaceToggle(selectedNamespaceId) {
      this.currentSelectedNamespaceId = selectedNamespaceId;
    },
    // 根据dataId获取输入框建议(当我们点击dataId的输入框就会自动调用这个方法)
    getInputSuggestionsByDataId(queryString, callback) {
      // 数据库中所有dataId
      var allDataIdsInDatabase = this.allDataIdsInDatabase;
      // 输入框建议
      var inputSuggestions = queryString ? allDataIdsInDatabase.filter(this.createDataIdFilter(queryString)) : allDataIdsInDatabase;
      
      // 调用callback方法返回输入建议（这里使用定时器延迟调用,作用是显示一下建议列表区的转圈圈）
      clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          callback(inputSuggestions);
      }, 500);

    },
    // 创建dataId过滤器。筛选出和输入数据匹配的建议
    createDataIdFilter(queryString) {
      // //模糊匹配
      // return (item) => {
      //   return item.value.toUpperCase().match(queryString.toUpperCase());
      // };

      // 精确匹配
        return (item) => {
          return (item.dataId.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    // 点击输入框的dataId建议项的回调方法
    selectDataIdCallback(item){
      // 将用户点击的dataId更新到输入框内容中
      this.queryCondition.dataId = item.dataId;
    },
    // 根据分组名称获取输入框建议(当我们点击dataId的输入框就会自动调用这个方法)
    getInputSuggestionsByGroupName(queryString, callback) {
      // 数据库中所有分组名称
      var allGroupNamesInDatabase = this.allGroupNamesInDatabase;
      // 输入框建议
      var inputSuggestions = queryString ? allGroupNamesInDatabase.filter(this.createGroupNameFilter(queryString)) : allGroupNamesInDatabase;
      
      // 调用callback方法返回输入建议（这里使用定时器延迟调用,作用是显示一下建议列表区的转圈圈）
      clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          callback(inputSuggestions);
      }, 500);

    },
    // 创建分组名称过滤器。筛选出和输入数据匹配的建议
    createGroupNameFilter(queryString) {
      // //模糊匹配
      // return (item) => {
      //   return item.value.toUpperCase().match(queryString.toUpperCase());
      // };

      // 精确匹配
        return (item) => {
          return (item.groupName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    // 点击输入框的分组名称建议项的回调方法
    selectGroupNameCallback(item){
      // 将用户点击的分组名称更新到输入框内容中
      this.queryCondition.groupName = item.groupName;
    },
    // 点击查询
    query() {
      console.log(this.queryCondition);
    },
    // 跳转到配置历史版本详情路由
    ConfigHistoryVersionDetail(versionId) {
      this.$router.push({
        path: "/config/history/version/detail",
      });
    },
    // 跳转到配置回滚路由
    ConfigRollback(versionId) {
      this.$router.push({
        path: "/config/history/version/rollback",
      });
    },
    // 点击打开历史版本的比较dialog
    clickOpenHistoryVersionCompareDialog(versionId) {
      this.openHistoryVersionCompareDialog = true;
    },
    // pageSize（每页展示的数量）改变时触发
    handlePageSizeChange(pageSize) {
      console.log("pageSize=" + pageSize);
    },
    // currentPage（当前页）改变时触发
    handleCurrentPageChange(currentPage) {
      console.log("currentPage=" + currentPage);
    },
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
</style>