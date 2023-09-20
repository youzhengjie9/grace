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

    <!-- 导入配置dialog -->
    <el-dialog :visible.sync="openImportConfigDialog" top="15vh" width="30%">
      <!-- 标题插槽 -->
      <div slot="title">
        <i
          class="el-icon-circle-check"
          style="color: #f1c826; font-size: 23px; margin-right: 5px"
        ></i>
        <span style="font-size: 20px">导入配置</span>
      </div>
      <!-- 内容 -->
      <el-row :gutter="24" style="margin-left: 30px">
        <!-- 目标空间 -->
        <el-col :span="24" style="margin-bottom: 10px">
          目标空间: <span style="color: rgb(73, 210, 231)">public</span>
        </el-col>
        <!-- 选择一个如果当前导入的配置已存在后对该配置处理的策略（方式） -->
        <el-col :span="24" style="margin-bottom: 10px">
          如果当前导入的配置已存在则
          <el-select
            v-model="importConfigIfExistPolicy"
            size="small"
            style="width: 150px"
          >
            <el-option label="终止导入" value="abort"></el-option>
            <el-option label="跳过" value="skip"></el-option>
            <el-option label="覆盖" value="cover"></el-option>
          </el-select>
        </el-col>
        <el-col :span="24" style="margin-bottom: 20px">
          <i
            class="el-icon-warning-outline"
            style="color: #f1c826; font-size: 23px; margin-right: 5px"
          ></i>
          文件上传后将直接导入配置，请务必谨慎操作！
        </el-col>

        <!-- 上传配置文件 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            name="configFile"
            :headers="requestHeaders"
            :multiple="true"
            :before-upload="beforeUploadConfigFile"
            :on-success="configFileUploadSuccess"
            :on-error="configFileUploadError"
            list-type="text"
            :auto-upload="true"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 高级查询条件（默认是隐藏的） -->
    <el-row :gutter="24" v-if="showAdvancedQueryCondition == true">
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
          v-model="queryCondition.advancedQueryCondition.formApplication"
          placeholder="请输入归属应用"
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
          v-model="queryCondition.advancedQueryCondition.tag"
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
          v-model="queryCondition.advancedQueryCondition.configItem"
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
          <strong style="font-weight: bold">{{ totalCount }}</strong>
          条满足要求的配置。
        </span>
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
          <span class="operation" @click="exampleCode(scope.row.id)"
            >示例代码</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 修改/编辑配置 -->
          <span class="operation" @click="modifyConfig(scope.row.id)"
            >编辑</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 删除配置 -->
          <span class="operation" @click="deleteConfig(scope.row.id)"
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
      <!-- 批量删除按钮 -->
      <el-col :span="2" style="margin-right: 5px">
        <el-button
          type="danger"
          size="medium"
          @click="clickOpenBatchDeleteDialog"
          >批量删除</el-button
        >
      </el-col>
      <!-- 克隆按钮 -->
      <el-col :span="1" style="margin-right: 30px">
        <el-button type="primary" size="medium" @click="clickOpenCloneDialog"
          >克隆</el-button
        >
      </el-col>
      <!-- “导出”下拉菜单 -->
      <el-col :span="2">
        <el-dropdown
          trigger="click"
          @command="clickExportDropdownItem"
          placement="bottom-start"
        >
          <!-- 导出按钮 -->
          <el-button type="primary" size="medium">
            导出<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="1">导出查询结果</el-dropdown-item>
            <el-dropdown-item command="2">新版导出查询结果</el-dropdown-item>
            <el-dropdown-item command="3">导出选中的配置</el-dropdown-item>
            <el-dropdown-item command="4">新版导出选中的配置</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>

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

    <!-- 批量删除配置dialog -->
    <el-dialog :visible.sync="openBatchDeleteDialog" top="15vh" width="30%">
      <!-- 标题插槽 -->
      <div slot="title">
        <i
          class="el-icon-circle-check"
          style="color: #f1c826; font-size: 23px; margin-right: 5px"
        ></i>
        <span style="font-size: 20px">批量删除配置</span>
      </div>
      <!-- 内容 -->
      <el-row :gutter="24" style="margin-left: 10px">
        <!-- 目标空间 -->
        <el-col :span="24" style="margin-bottom: 10px">
          确定要删除以下配置吗？
        </el-col>

        <!-- 展示即将被删除的配置信息 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <!-- 表格内容  -->
          <el-table :data="tableData" border style="width: 100%">
            <!-- dataId -->
            <el-table-column prop="dataId" label="Data Id" width="225">
            </el-table-column>
            <!-- 分组名称 -->
            <el-table-column
              prop="groupName"
              label="分组名称"
              width="168"
            >
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>

      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="batchDeleteConfig">确定</el-button>
        <el-button @click="clickCloseBatchDeleteDialog">取消</el-button>
      </div>
    </el-dialog>

    <!-- 克隆配置dialog -->

    




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
      // 查询条件
      queryCondition: {
        // 配置文件全名（例如application-dev.yaml）
        dataId: "",
        // 分组名称
        groupName: "",
        // 是否开启模糊查询
        openFuzzyQuery: true,
        // 高级查询条件
        advancedQueryCondition: {
          // 归属应用
          formApplication: "",
          // 标签
          tag: "",
          // 配置项
          configItem: "",
        },
      },
      // 是否显示高级查询条件（默认是隐藏）
      showAdvancedQueryCondition: false,
      // 是否打开导入配置dialog
      openImportConfigDialog: false,
      // 当前导入的配置如果已存在后对该配置处理的策略（方式）
      importConfigIfExistPolicy: "abort",
      // 请求头
      requestHeaders: { accessToken: "123456789" },
      // 多选框中勾选的所有数据
      multipleSelectionData: [],
      // 总记录数
      totalCount: 200,
      // 每页展示的数量
      pagesize: 10,
      // 当前页
      currentPage: 1,
      // 表格数据
      tableData: [
        {
          id: 10001,
          // 配置文件全名（例如application-dev.yaml）
          dataId: "application-dev.yaml",
          // 分组名称
          groupName: "DEFAULT_GROUP",
          // 归属应用
          formApplication: "",
        },
        {
          id: 10002,
          // 配置文件全名（例如userservice-dev.yaml）
          dataId: "userservice-test.properties",
          // 分组名称
          groupName: "DEFAULT_GROUP",
          // 归属应用
          formApplication: "",
        },
      ],
      // 是否打开批量删除dialog
      openBatchDeleteDialog: false,
      // 是否打开克隆dialog
      OpenCloneDialog: false,
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
    // 跳转到创建配置路由
    createConfig() {
      this.$router.push({
        path: "/config/create",
      });
    },
    // 点击查询
    query() {
      console.log(this.queryCondition);
    },
    // 控制高级查询条件的显示和隐藏，如果高级查询条件（showAdvancedQueryCondition）为true,则将其变成false,反之变为true。
    oppositeShowAdvancedQueryCondition() {
      this.showAdvancedQueryCondition = !this.showAdvancedQueryCondition;
    },
    // 点击打开导入配置dialog
    clickOpenImportConfigDialog() {
      this.openImportConfigDialog = true;
    },
    // 上传配置文件之前的回调（作用是校验上传的文件）
    beforeUploadConfigFile(configFile) {
      // 上传的配置文件大小的校验
      if (configFile.size > 1024 * 1024 * 1) {
        this.$message({
          type: "error",
          message:
            "你上传的 “" + configFile.name + "” 文件过大,请上传小于1M的文件。",
        });
        return false;
      }
      // 上传的配置文件的后缀名（文件格式）校验
      let index = configFile.name.lastIndexOf(".");
      let fileType = configFile.name.substr(index + 1, configFile.name.length);
      if (
        ["txt", "json", "properties", "yaml", "yml", "xml", "html"].indexOf(
          fileType.toLowerCase()
        ) === -1
      ) {
        this.$message({
          type: "error",
          message:
            "请上传的配置文件的后缀名为txt、json、properties、yaml、yml、xml、html的附件",
        });
        return false;
      }
    },
    // 配置文件上传成功回调
    configFileUploadSuccess(response, file, fileList) {
      console.log("===========");
      console.log(response);
      console.log(file);
      console.log(fileList);
      console.log("===========");
      this.$message({
        type: "success",
        message: "上传成功",
      });
    },
    // 配置文件上传失败回调
    configFileUploadError(response, file, fileList) {
      console.log("===========");
      console.log(response);
      console.log(file);
      console.log(fileList);
      console.log("===========");
      this.$message({
        type: "error",
        message: "上传失败",
      });
    },
    // 当多选框被勾选（或被取消勾选）,curMultipleSelectionData是最新的多选框被勾选的所有数据
    multipleSelection(curMultipleSelectionData) {
      // 更新多选框被勾选的所有数据
      this.multipleSelectionData = curMultipleSelectionData;
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
    // 打开示例代码
    exampleCode(id) {},
    // 跳转修改/编辑配置
    modifyConfig(id) {
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
    // 点击“更多”选项所展开的下拉菜单项
    clickMoreDropdownItem(command) {
      // 点击历史版本
      if (command == 1) {
        console.log("历史版本");
      }
      // 点击监听查询
      else if (command == 2) {
        console.log("监听查询");
      }
    },
    // 点击打开批量删除dialog
    clickOpenBatchDeleteDialog() {
      this.openBatchDeleteDialog = true;
    },
    // 点击关闭批量删除dialog
    clickCloseBatchDeleteDialog() {
      this.openBatchDeleteDialog = false;
    },
    //批量删除配置
    batchDeleteConfig(){
      console.log('批量删除')
    },
    // 点击打开克隆dialog
    clickOpenCloneDialog() {
      this.OpenCloneDialog = true;
    },
    // 点击“导出”选项所展开的下拉菜单项
    clickExportDropdownItem(command) {
      // 点击查询结果
      if (command == 1) {
        console.log("导出查询结果");
      }
      // 点击新版导出查询结果
      else if (command == 2) {
        console.log("新版导出查询结果");
      }
      // 点击导出选中的配置
      else if (command == 3) {
        console.log("导出选中的配置");
      }
      // 点击新版导出选中的配置
      else if (command == 4) {
        console.log("新版导出选中的配置");
      }
    },
    // pageSize（每页展示的数量）改变时触发
    handlePageSizeChange(pageSize) {
      console.log("pageSize=" + pageSize);
    },
    // currentPage（当前页）改变时触发
    handleCurrentPageChange(currentPage) {
      console.log("currentPage=" + currentPage);
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