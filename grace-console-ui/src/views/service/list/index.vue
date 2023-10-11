<template>
  <div class="service-list-box">
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
        >服务列表</span
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

    <!-- 查询 -->
    <div>
      <el-row>
        <el-form :inline="true" :model="queryCondition">
          <!-- 输入服务名称 -->
          <el-col :span="6">
            <el-form-item>
              <!-- 标题 -->
              <span slot="label">
                <span style="font-weight: bold">服务名称</span>
              </span>
              <el-input
                v-model="queryCondition.serviceName"
                placeholder="请输入服务名称"
              ></el-input>
            </el-form-item>
          </el-col>

          <!-- 输入分组名称 -->
          <el-col :span="6">
            <el-form-item>
              <!-- 标题 -->
              <span slot="label">
                <span style="font-weight: bold">分组名称</span>
              </span>
              <el-input
                v-model="queryCondition.groupName"
                placeholder="请输入分组名称"
              ></el-input>
            </el-form-item>
          </el-col>

          <!--  隐藏空服务 -->
          <el-col :span="3">
            <el-form-item>
              <!-- 标题 -->
              <span slot="label">
                <span style="font-weight: bold">隐藏空服务</span>
              </span>
              <el-switch
                v-model="queryCondition.hideEmptyService"
                active-color="#209bfa"
                inactive-color="#f5f5f5"
                @change="changeHideEmptyService"
              >
              </el-switch>
            </el-form-item>
          </el-col>

          <!-- 查询 -->
          <el-col :span="7">
            <el-form-item>
              <el-button type="primary" @click="query" size="small"
                >查询</el-button
              >
            </el-form-item>
          </el-col>
        </el-form>

        <el-col :span="2">
          <el-button
            type="primary"
            size="small"
            @click="clickOpenCreateServiceDialog"
            >创建服务</el-button
          >
        </el-col>
      </el-row>
    </div>

    <!-- 表格内容  -->
    <el-table
      :data="tableData"
      border
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      v-loading="tableLoading"
      element-loading-background="rgba(255, 255, 255, .5)"
      element-loading-text="加载中，请稍后..."
      element-loading-spinner="el-icon-loading"
      style="width: 100%"
    >
      <el-table-column prop="serviceName" label="服务名称" width="210">
      </el-table-column>
      <el-table-column prop="groupName" label="分组名称" width="210">
      </el-table-column>
      <el-table-column prop="instanceCount" label="实例数" width="150">
      </el-table-column>
      <el-table-column
        prop="healthyInstanceCount"
        label="健康实例数"
        width="150"
      >
      </el-table-column>
      <el-table-column
        prop="triggerProtectThresholdFlag"
        label="触发保护阈值"
        width="160"
      >
      </el-table-column>
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <span class="operation" @click="serviceDetail(scope.row.id)"
            >服务详情</span
          >
          <span style="margin-right: 5px">|</span>
          <span class="operation" href="#" @click="deleteService(scope.row.id)"
            >删除服务</span
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-row :gutter="24" style="margin-top: 30px">
      <el-col :span="12" :offset="12">
        <div class="grid-content bg-purple">
          <el-pagination
            background
            layout="total, sizes, prev, pager, next"
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

    <!-- 创建服务对话框（dialog） -->

    <el-dialog title="创建服务" :visible.sync="openCreateServiceDialog">
      <el-form
        :model="createServiceForm"
        ref="createServiceForm"
        :rules="createServiceRules"
        label-width="150px"
      >
        <el-form-item label="服务名称" prop="serviceName">
          <el-input
            v-model="createServiceForm.serviceName"
            autocomplete="off"
            style="width: 70%"
          ></el-input>
        </el-form-item>
        <el-form-item label="分组名称" prop="groupName">
          <el-input
            v-model="createServiceForm.groupName"
            autocomplete="off"
            placeholder="DEFAULT_GROUP"
            style="width: 70%"
          ></el-input>
        </el-form-item>
        <el-form-item label="保护阈值" prop="protectThreshold">
          <el-input
            v-model="createServiceForm.protectThreshold"
            autocomplete="off"
            style="width: 70%"
          ></el-input>
        </el-form-item>
        <el-form-item label="元数据" prop="metadata">
          <!-- 代码编辑器 -->
          <editor
            v-model="createServiceForm.metadata"
            lang="json"
            theme="tomorrow_night"
            width="70%"
            height="200"
            @init="editorInit"
            :options="writableEditorOptions"
          ></editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createService('createServiceForm')">确 定</el-button>
        <el-button @click="openCreateServiceDialog = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";
import { getNamespaceList } from "@/api/namespace";
import { getServiceList, createService } from "@/api/service";

export default {
  name: "ServiceList",
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
        // 服务名称
        serviceName: "",
        // 分组名称
        groupName: "",
        // 是否隐藏空服务
        hideEmptyService: false,
      },
      // 表格数据
      tableData: [],
      // 表格是否加载中（ true说明表格正在加载中,则会显示加载动画。反之false则关闭加载动画）
      tableLoading: false,
      // 总记录数
      totalCount: 200,
      // 当前页
      page: 1,
      // 每页展示的数量
      size: 10,
      // 是否打开创建服务的对话框（dialog）
      openCreateServiceDialog: false,
      // 创建服务表单
      createServiceForm: {
        // 服务名称
        serviceName: "",
        // 分组名称
        groupName: "",
        // 保护阈值
        protectThreshold: "",
        // 元数据
        metadata: "",
      },
      // 创建服务规则
      createServiceRules: {
        // 服务名称
        serviceName: [
          { required: true, message: "请输入服务名称", trigger: "blur" },
        ],
        // 保护阈值（纯整数（123）和小数（567.6512））
        protectThreshold: [
          {
            required: true,
            pattern: /^(\-|\+)?\d+(\.\d+)?$/,
            message: "请输入保护阈值",
            trigger: "blur",
          },
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
  mounted() {
    this.loadData();
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
    // 加载数据
    loadData() {
      // 开启表格的加载动画
      this.tableLoading = true;

      // 加载命名空间数据
      getNamespaceList().then((response) => {
        // 后端返回给前端的result对象
        let result = response.data;
        this.namespaceData = result.data;
      });

      //当前选择的命名空间的id
      let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
      // 是否隐藏空服务
      let hideEmptyService = this.queryCondition.hideEmptyService;
      // 当前页
      let page = this.page;
      // 每页展示的数量
      let size = this.size;
      // 从后端分页的获取service列表的数据
      getServiceList(
        currentSelectedNamespaceId,
        hideEmptyService,
        page,
        size
      ).then((response) => {
        // 后端返回给前端的result对象
        let result = response.data;

        // 将数据放到vue中
        this.tableData = result.data.pagedServiceList;
        this.totalCount = result.data.totalCount;
        // 关闭表格的加载动画
        this.tableLoading = false;
      });
    },
    // 点击切换命名空间
    namespaceToggle(selectedNamespaceId) {
      // 更新当前选择的命名空间id
      this.currentSelectedNamespaceId = selectedNamespaceId;
      // 是否隐藏空服务
      let hideEmptyService = this.queryCondition.hideEmptyService;
      // 将当前页重置回第 1 页（分页标签上的current-page属性必须加上.sync,不然无法将当前页重置回第 1 页）
      this.page = 1;
      // 每页展示的数量
      let size = this.size;
      // 从后端分页的获取service列表的数据
      getServiceList(
        this.currentSelectedNamespaceId,
        hideEmptyService,
        this.page,
        size
      ).then((response) => {
        // 后端返回给前端的result对象
        let result = response.data;

        // 将数据放到vue中
        this.tableData = result.data.pagedServiceList;
        this.totalCount = result.data.totalCount;
        // 关闭表格的加载动画
        this.tableLoading = false;
      });
    },
    // 点击切换隐藏空服务
    changeHideEmptyService(hideEmptyService) {
      //当前选择的命名空间的id
      let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
      // 当前页
      let page = this.page;
      // 每页展示的数量
      let size = this.size;
      // 从后端分页的获取service列表的数据
      getServiceList(
        currentSelectedNamespaceId,
        hideEmptyService,
        page,
        size
      ).then((response) => {
        // 后端返回给前端的result对象
        let result = response.data;

        // 将数据放到vue中
        this.tableData = result.data.pagedServiceList;
        this.totalCount = result.data.totalCount;
        // 关闭表格的加载动画
        this.tableLoading = false;
      });
    },
    // 点击查询
    query() {
      console.log(this.queryCondition);
    },
    // 点击打开创建服务dialog
    clickOpenCreateServiceDialog() {
      this.openCreateServiceDialog = true;
    },
    // 服务详情
    serviceDetail(id) {
      this.$router.push({
        path: "/service/detail",
        query: {
          id: id,
        },
      });
    },
    // 删除服务
    deleteService(id) {
      console.log(id);
    },
    // page（当前页）改变时触发
    handlePageChange(page) {
      // 开启表格的加载动画
      this.tableLoading = true;

      //当前选择的命名空间的id
      let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
      // 是否隐藏空服务
      let hideEmptyService = this.queryCondition.hideEmptyService;
      // 每页展示的数量
      let size = this.size;
      // 从后端分页的获取service列表的数据
      getServiceList(
        currentSelectedNamespaceId,
        hideEmptyService,
        page,
        size
      ).then((response) => {
        // 后端返回给前端的result对象
        let result = response.data;

        // 将数据放到vue中
        this.tableData = result.data.pagedServiceList;
        this.totalCount = result.data.totalCount;
        // 关闭表格的加载动画
        this.tableLoading = false;
      });
    },
    // size（每页展示的数量）改变时触发
    handleSizeChange(size) {
      // 开启表格的加载动画
      this.tableLoading = true;

      //当前选择的命名空间的id
      let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
      // 是否隐藏空服务
      let hideEmptyService = this.queryCondition.hideEmptyService;
      // 当前页
      let page = this.page;
      // 从后端分页的获取service列表的数据
      getServiceList(
        currentSelectedNamespaceId,
        hideEmptyService,
        page,
        size
      ).then((response) => {
        // 后端返回给前端的result对象
        let result = response.data;

        // 将数据放到vue中
        this.tableData = result.data.pagedServiceList;
        this.totalCount = result.data.totalCount;
        // 关闭表格的加载动画
        this.tableLoading = false;
      });
    },
    // 创建服务
    createService(formName) {
      // 校验表单
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 设置namespaceId
          this.createServiceForm.namespaceId = this.currentSelectedNamespaceId;
          createService(this.createServiceForm).then((response) => {
            let result = response.data;
            if(result.data){
              this.$message.success('创建服务成功');
              // 重新加载数据
              this.loadData();
              // 关闭dialog
              this.openCreateServiceDialog = false;
            }else{
              this.$message.error('创建服务失败');
            }
          });
        } else {
          return false;
        }
      });
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