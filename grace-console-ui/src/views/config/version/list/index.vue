<template>
  <div class="config-version-list-box">
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
        >配置版本</span
      >
    </div>

    <!-- 命名空间切换 -->
    <div class="namespace-toggle">
      <div
        style="cursor: pointer"
        v-for="(namespace, index) in namespaceData"
        :key="namespace.namespaceId"
      >
        <!-- 没有高亮显示（也就是当前选择的命名空间不是这个） -->
        <span
          style="
            color: rgb(102, 102, 102);
            padding-right: 10px;
            border: none;
            font-size: 14px;
          "
          v-if="namespace.namespaceId != currentSelectedNamespaceId"
          @click="namespaceToggle(namespace.namespaceId)"
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
          v-if="namespace.namespaceId == currentSelectedNamespaceId"
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
          @select="selectDataIdCallback"
          :debounce="0"
        >
          <!-- 展示输入框下面的dataId建议 -->
          <template slot-scope="{ item }">
            <div class="name">{{ item }}</div>
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
          @select="selectGroupNameCallback"
        >
          <!-- 展示输入框下面的dataId建议 -->
          <template slot-scope="{ item }">
            <div class="name">{{ item }}</div>
          </template>
        </el-autocomplete>
      </el-col>

      <!-- 查询按钮 -->
      <el-col :span="2" style="margin-top: 10px">
        <el-button type="primary" @click="query" size="medium">查询</el-button>
      </el-col>

      <!-- 输入分组名称 -->
      <el-col :span="2" style="margin-top: 10px">
        <el-button
          type="warning"
          @click="clickOpenVersionCompareDialog"
          size="medium"
          >版本对比</el-button
        >
      </el-col>
    </el-row>

    <!-- 表格内容  -->
    <el-table
      :data="tableData"
      ref="configVersionTableRef"
      border
      style="width: 100%; margin-bottom: 25px"
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      v-loading="tableLoading"
      element-loading-background="rgba(255, 255, 255, .5)"
      element-loading-text="加载中，请稍后..."
      element-loading-spinner="el-icon-loading"
      @selection-change="versionCompareMultipleSelectionChange"
    >
      <!-- 版本比较多选框 -->
      <el-table-column type="selection" width="55"> </el-table-column>
      <!-- id -->
      <el-table-column prop="id" label="id" width="180"> </el-table-column>
      <!-- dataId -->
      <el-table-column prop="dataId" label="Data Id" width="220">
      </el-table-column>
      <!-- 分组名称 -->
      <el-table-column prop="groupName" label="分组名称" width="250">
      </el-table-column>
      <!-- 这个配置被执行了什么操作（比如新增、修改、删除） -->
      <el-table-column prop="operationType" label="操作类型" width="100">
      </el-table-column>
      <!-- 如果这个版本是当前配置的版本（通过scope.row.id == currentVersionId判断）,则展示“当前版本”的标签,如果不是则不展示标签 -->
      <el-table-column prop="operationType" label="当前版本" width="100">
        <template slot-scope="scope">
          <el-tag
            :type="'success'"
            v-if="scope.row.id == currentVersionId"
            disable-transitions
            >当前版本</el-tag
          >
        </template>
      </el-table-column>
      <!-- 操作这个配置的时间 -->
      <el-table-column prop="operationTime" label="操作时间" width="180">
        <template slot-scope="scope">
          <span>{{
            scope.row.operationTime | dateformat("YYYY-MM-DD HH:mm:ss")
          }}</span>
        </template>
      </el-table-column>

      <!-- 操作 -->
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <!-- 版本详情 -->
          <span class="operation" @click="versionDetail(scope.row.id)" v-hasPerm="['version:detail']"
            >版本详情</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 版本回滚 -->
          <span class="operation" @click="versionRollback(scope.row.id)" v-hasPerm="['version:rollback']"
            >版本回滚</span
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 配置版本的比较dialog -->
    <el-dialog :visible.sync="openVersionCompareDialog" top="15vh" width="80%">
      <!-- dialog标题插槽 -->
      <div slot="title">
        <span style="font-size: 20px">配置版本比较</span>
      </div>

      <!-- 代码差异对比上面的标题 -->
      <el-row :gutter="24">
        <el-col :span="5" :offset="3">
          <span style="font-size: 16px; font-weight: 600"
            >第一个勾选的版本(作为旧版本)</span
          >
        </el-col>
        <el-col :span="6" :offset="7">
          <span style="font-size: 16px; font-weight: 600"
            >第二个勾选的版本(作为新版本)</span
          >
        </el-col>
      </el-row>

      <!-- 代码差异对比上面的标题 -->
      <el-row :gutter="24">
        <el-col :span="5" :offset="3">
          <span style="font-size: 14px"
            >该版本的id为:
            {{ this.versionCompareDialogData.firstSelectedVersionId }}</span
          >
        </el-col>
        <el-col :span="6" :offset="7">
          <span style="font-size: 14px"
            >该版本的id为:
            {{ this.versionCompareDialogData.secondSelectedVersionId }}</span
          >
        </el-col>
      </el-row>

      <!-- 内容 -->
      <el-row :gutter="24">
        <!-- 使用v-code-diff插件进行代码差异对比 -->
        <code-diff
          :old-string="
            this.versionCompareDialogData.firstSelectedVersionConfigContent
          "
          :new-string="
            this.versionCompareDialogData.secondSelectedVersionConfigContent
          "
          output-format="side-by-side"
        >
        </code-diff>
      </el-row>

      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="openVersionCompareDialog = false"
          >返回</el-button
        >
      </div>
    </el-dialog>

    <!-- 表格底部 -->
    <el-row :gutter="24" style="margin-top: 25px">
      <!-- 分页 -->
      <el-col :span="10" :offset="14">
        <div class="grid-content bg-purple">
          <el-pagination
            background
            layout="sizes, prev, pager, next"
            prev-text="上一页"
            next-text="下一页"
            :page-sizes="[10, 20, 30, 50, 100]"
            :total="totalCount"
            :current-page.sync="page"
            :page-size="size"
            @current-change="handlePageChange"
            @size-change="handleSizeChange"
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
import { getNamespaceList } from "@/api/namespace";
import {
  getConfigVersionList,
  getConfigVersionInputSuggestionData,
} from "@/api/configVersion";

export default {
  name: "ConfigVersionList",
  components: {
    Editor,
  },
  data() {
    return {
      // 命名空间数据
      namespaceData: [],
      // 当前选择的命名空间的id
      currentSelectedNamespaceId: "",
      // 查询条件
      queryCondition: {
        // 配置文件全名（例如application-dev.yaml）
        dataId: "",
        // 分组名称
        groupName: "",
      },
      // 指定namespaceId下面的所有dataId
      allDataIds: [],
      // 指定namespaceId下面的所有groupName
      allGroupNames: [],
      // 配置版本列表数据
      tableData: [],
      // 保存表格的“版本比较”的多选框的勾选数据的“数组”(这个数组中的元素是一个个被勾选的那一行记录的全部数据)
      versionCompareMultipleSelection: [],
      // 当前配置的版本id
      currentVersionId: 0,
      // 表格是否加载中（ true说明表格正在加载中,则会显示加载动画。反之false则关闭加载动画）
      tableLoading: false,
      // 总记录数
      totalCount: 200,
      // 当前页
      page: 1,
      // 每页展示的数量
      size: 10,
      // 是否打开版本比较dialog（版本指的是同一个namespace、DataId、groupName的配置）
      openVersionCompareDialog: false,
      // 版本比较dialog所需要的数据
      versionCompareDialogData: {
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
  // “（第一次）进入该路由、点击浏览器的刷新按钮后”会执行该生命周期。
  created() {
    // 如果跳转到该路由的时候传了参数（query）,则将这些参数保存到该组件
    this.loadRouterParams();
    // 加载命名空间数据
    this.loadNamespaceData();
    // 加载dataId和groupName输入框建议的数据
    this.loadInputSuggestionData();
  },
  // “（每次）进入该路由、点击浏览器的刷新按钮后”会执行该生命周期。
  // 作用是: 实现在一个路由组件中部分数据缓存、部分数据重新加载的功能。
  activated() {
    // 如果跳转到该路由的时候传了参数（query）,则将这些参数保存到该组件
    this.loadRouterParams();
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
    // 如果跳转到该路由的时候传了参数（params）,则将这些参数保存到该组件
    loadRouterParams() {
      let namespaceId = this.$route.query.namespaceId;
      let groupName = this.$route.query.groupName;
      let dataId = this.$route.query.dataId;
      // console.log(namespaceId)
      // console.log(groupName)
      // console.log(dataId)
      // 如果路由参数传了namespaceId
      if (typeof namespaceId != "undefined") {
        this.currentSelectedNamespaceId = namespaceId;
      }
      // 如果路由参数传了groupName
      if (typeof groupName != "undefined") {
        this.queryCondition.groupName = groupName;
      }
      // 如果路由参数传了dataId
      if (typeof dataId != "undefined") {
        this.queryCondition.dataId = dataId;
      }
      // 调用查询方法,加载表格数据
      this.query();
    },
    // 加载dataId和groupName输入框建议的数据
    loadInputSuggestionData() {
      // 当前选中的命名空间id
      let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
      getConfigVersionInputSuggestionData(currentSelectedNamespaceId).then(
        (response) => {
          let result = response.data;
          this.allDataIds = result.data.allDataIds;
          this.allGroupNames = result.data.allGroupNames;
        }
      );
    },
    // 加载命名空间数据
    loadNamespaceData() {
      // 加载命名空间数据
      getNamespaceList().then((response) => {
        // 后端返回给前端的result对象
        let result = response.data;
        this.namespaceData = result.data;
      });
    },
    // 点击切换命名空间
    namespaceToggle(selectedNamespaceId) {
      console.log(selectedNamespaceId);
      this.currentSelectedNamespaceId = selectedNamespaceId;
      // 重新加载dataId和groupName输入框建议的数据
      this.loadInputSuggestionData();
    },
    // 根据dataId获取输入框建议(当我们点击dataId的输入框就会自动调用这个方法)
    getInputSuggestionsByDataId(queryString, callback) {
      // 指定命名空间下的所有dataId
      var allDataIds = this.allDataIds;
      // 输入框建议列表
      var inputSuggestionList = queryString
        ? allDataIds.filter(this.createDataIdFilter(queryString))
        : allDataIds;
      // 如果输入框建议列表为空
      // if(inputSuggestionList.length == 0){
      //   inputSuggestionList = ['无选项']
      // }

      // 调用callback方法返回输入建议列表
      callback(inputSuggestionList);
    },
    // 创建dataId过滤器。筛选出和输入数据匹配的建议
    createDataIdFilter(queryString) {
      // 模糊匹配
      return (item) => {
        return item.toUpperCase().match(queryString.toUpperCase());
      };
    },
    // 点击输入框的dataId建议项的回调方法
    selectDataIdCallback(item) {
      // 将用户点击的dataId更新到输入框内容中
      this.queryCondition.dataId = item;
    },
    // 根据分组名称获取输入框建议(当我们点击dataId的输入框就会自动调用这个方法)
    getInputSuggestionsByGroupName(queryString, callback) {
      // 指定命名空间下的所有分组名称
      var allGroupNames = this.allGroupNames;
      // 输入框建议
      var inputSuggestionList = queryString
        ? allGroupNames.filter(this.createGroupNameFilter(queryString))
        : allGroupNames;

      // 如果输入框建议列表为空
      // if(inputSuggestionList.length == 0){
      //   inputSuggestionList = ['无选项']
      // }

      // 调用callback方法返回输入建议
      callback(inputSuggestionList);
    },
    // 创建分组名称过滤器。筛选出和输入数据匹配的建议
    createGroupNameFilter(queryString) {
      // 模糊匹配
      return (item) => {
        return item.toUpperCase().match(queryString.toUpperCase());
      };
    },
    // 点击输入框的分组名称建议项的回调方法
    selectGroupNameCallback(item) {
      // 将用户点击的分组名称更新到输入框内容中
      this.queryCondition.groupName = item;
    },
    // 点击查询
    query() {
      // 开启表格的加载动画
      this.tableLoading = true;

      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        // 当前选择的命名空间的id
        let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
        // 指定分组名
        let groupName = this.queryCondition.groupName;
        // 指定dataid
        let dataId = this.queryCondition.dataId;
        // 当前页
        let page = this.page;
        // 每页展示的数量
        let size = this.size;
        // 从后端分页的获取配置版本列表的数据
        getConfigVersionList(
          currentSelectedNamespaceId,
          groupName,
          dataId,
          page,
          size
        ).then((response) => {
          // 后端返回给前端的result对象
          let result = response.data;
          // 更新当前配置的版本id
          this.currentVersionId = result.data.currentVersionId;
          // 将数据放到vue中
          this.tableData = result.data.pagedList;
          this.totalCount = result.data.totalCount;
          // 关闭表格的加载动画
          this.tableLoading = false;
        });
      }, 500);
    },
    // 勾选表格的“版本比较”的多选框回调方法
    versionCompareMultipleSelectionChange(versionCompareMultipleSelection) {
      if (versionCompareMultipleSelection.length > 2) {
        this.$message.warning(`最多只能勾选2个配置版本`);
        // 将刚刚超出勾选限制的那行记录的“选择框勾勾”给取消掉
        let del_row = versionCompareMultipleSelection.pop();
        this.$refs.configVersionTableRef.toggleRowSelection(del_row, false);
      } else {
        this.versionCompareMultipleSelection = versionCompareMultipleSelection;
      }
    },
    // 跳转到版本详情路由
    versionDetail(versionId) {
      this.$router.push({
        path: "/config/version/detail",
        query: {
          versionId: versionId,
        },
      });
    },
    // 跳转到版本回滚路由
    versionRollback(versionId) {
      this.$router.push({
        path: "/config/version/rollback",
        query: {
          versionId: versionId,
        },
      });
    },
    // 点击打开版本比较dialog
    clickOpenVersionCompareDialog() {
      // 获取表格的“版本比较”的多选框的勾选数据的“数组”
      let versionArray = this.versionCompareMultipleSelection;
      // 如果版本比较多选框“刚好勾选了两个配置版本”,则“可以打开”版本比较dialog
      if (versionArray.length == 2) {
        // 版本比较dialog所需要的数据
        this.versionCompareDialogData = {
          // 第一个勾选的版本的id
          firstSelectedVersionId: versionArray[0].id,
          // 第一个勾选的版本的配置内容
          firstSelectedVersionConfigContent: versionArray[0].content,
          // 第二个勾选的版本的id
          secondSelectedVersionId: versionArray[1].id,
          // 第二个勾选的版本的配置内容
          secondSelectedVersionConfigContent: versionArray[1].content,
        };
        // 打开版本比较dialog
        this.openVersionCompareDialog = true;
      }
      // 如果版本比较多选框“没有勾选两个配置版本”,则“不可以”打开版本比较dialog
      else {
        this.$message.warning(`必须要勾选2个配置版本才能进行比较`);
      }
    },
    // page（当前页）改变时触发
    handlePageChange(page) {
      // 开启表格的加载动画
      this.tableLoading = true;

      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        // 当前选择的命名空间的id
        let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
        // 指定分组名
        let groupName = this.queryCondition.groupName;
        // 指定dataid
        let dataId = this.queryCondition.dataId;
        // 每页展示的数量
        let size = this.size;
        // 从后端分页的获取配置版本列表的数据
        getConfigVersionList(
          currentSelectedNamespaceId,
          groupName,
          dataId,
          page,
          size
        ).then((response) => {
          // 后端返回给前端的result对象
          let result = response.data;
          // 更新当前配置的版本id
          this.currentVersionId = result.data.currentVersionId;
          // 将数据放到vue中
          this.tableData = result.data.pagedList;
          this.totalCount = result.data.totalCount;
          // 关闭表格的加载动画
          this.tableLoading = false;
        });
      }, 500);
    },
    // size（每页展示的数量）改变时触发
    handleSizeChange(size) {
      // 开启表格的加载动画
      this.tableLoading = true;

      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        // 当前选择的命名空间的id
        let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
        // 指定分组名
        let groupName = this.queryCondition.groupName;
        // 指定dataid
        let dataId = this.queryCondition.dataId;
        // 当前页
        let page = this.page;
        // 从后端分页的获取配置版本列表的数据
        getConfigVersionList(
          currentSelectedNamespaceId,
          groupName,
          dataId,
          page,
          size
        ).then((response) => {
          // 后端返回给前端的result对象
          let result = response.data;
          // 更新当前配置的版本id
          this.currentVersionId = result.data.currentVersionId;
          // 将数据放到vue中
          this.tableData = result.data.pagedList;
          this.totalCount = result.data.totalCount;
          // 关闭表格的加载动画
          this.tableLoading = false;
        });
      }, 500);
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

/* 隐藏当前组件的全选框（一定要加上 ::v-deep ） */
::v-deep .el-table__header-wrapper .el-checkbox {
  display: none;
}
</style>

