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
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      v-loading="tableLoading"
      element-loading-background="rgba(255, 255, 255, .5)"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
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
          <span
            class="operation"
            @click="
              configDetail(
                scope.row.namespaceId,
                scope.row.groupName,
                scope.row.dataId
              )
            "
            >详情</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 示例代码 -->
          <span
            class="operation"
            @click="
              sampleCode(
                scope.row.namespaceId,
                scope.row.groupName,
                scope.row.dataId
              )
            "
            >示例代码</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 修改/编辑配置 -->
          <span
            class="operation"
            @click="
              modifyConfig(
                scope.row.namespaceId,
                scope.row.groupName,
                scope.row.dataId
              )
            "
            >编辑</span
          >
          <span style="margin-right: 5px">|</span>

          <!-- 删除配置 -->
          <span
            class="operation"
            @click="
              clickOpenDeleteDialog(
                scope.row.namespaceId,
                scope.row.groupName,
                scope.row.dataId
              )
            "
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
              <el-dropdown-item
                :command="
                  customDropdownCommand(
                    0,
                    scope.row.namespaceId,
                    scope.row.groupName,
                    scope.row.dataId
                  )
                "
              >
                配置版本
              </el-dropdown-item>
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
            :current-page.sync="page"
            :page-size="size"
            @current-change="handlePageChange"
            @size-change="handleSizeChange"
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>

    <!-- 示例代码dialog  -->
    <el-dialog :visible.sync="openSampleCodeDialog" top="15vh" width="70%">
      <!-- 标题插槽 -->
      <div slot="title">
        <span style="font-size: 20px">示例代码</span>
      </div>

      <!-- 内容 -->
      <el-row :gutter="24" style="margin-left: 10px">
        <el-col :span="24" style="margin-bottom: 10px">
          <!-- 切换实例代码的标签页 -->
          <el-tabs
            v-model="currentSelectSampleCodeTab"
            @tab-click="toggleSampleCode"
          >
            <el-tab-pane label="Java" name="Java">
              <!-- Java的示例代码展示 -->
              <editor
                v-model="javaSampleCodeContent"
                lang="java"
                theme="chrome"
                width="100%"
                height="320"
                @init="editorInit"
                :options="writableEditorOptions"
              ></editor>
            </el-tab-pane>
            <el-tab-pane label="SpringBoot" name="SpringBoot">
              <!-- SpringBoot的示例代码展示 -->
              <editor
                v-model="springBootSampleCodeContent"
                lang="java"
                theme="chrome"
                width="100%"
                height="320"
                @init="editorInit"
                :options="writableEditorOptions"
              ></editor>
            </el-tab-pane>
            <el-tab-pane label="SpringCloud" name="SpringCloud">
              <!-- SpringCloud的示例代码展示 -->
              <editor
                v-model="springCloudSampleCodeContent"
                lang="java"
                theme="chrome"
                width="100%"
                height="320"
                @init="editorInit"
                :options="writableEditorOptions"
              ></editor>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 删除配置的dialog  -->
    <el-dialog :visible.sync="openDeleteDialog" top="15vh" width="30%">
      <!-- 标题插槽 -->
      <div slot="title">
        <i
          class="el-icon-circle-check"
          style="color: #f1c826; font-size: 23px; margin-right: 5px"
        ></i>
        <span style="font-size: 20px">删除配置</span>
      </div>
      <!-- 内容 -->
      <el-row :gutter="24" style="margin-left: 30px">
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px"
            >确定要删除当前命名空间下面的以下配置吗？</span
          >
        </el-col>
        <!-- Data Id -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">Data Id: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ deleteConfigDialogData.dataId }}
          </span>
        </el-col>

        <!-- 分组名称 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">分组名称:</span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ deleteConfigDialogData.groupName }}
          </span>
        </el-col>
      </el-row>
      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="deleteConfig">确定</el-button>
        <el-button @click="openDeleteDialog = false">取消</el-button>
      </div>
    </el-dialog>

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
      <!-- 如果多选的数据不为空（则可以批量删除） -->
      <div class="content" v-if="this.multipleSelectionData.length > 0">
        <el-row :gutter="24" style="margin-left: 10px">
          <!-- 提示 -->
          <el-col :span="24" style="margin-bottom: 10px">
            确定要删除以下配置吗？
          </el-col>

          <!-- 展示即将被删除的配置信息 -->
          <el-col :span="24" style="margin-bottom: 10px">
            <!-- 表格内容  -->
            <el-table :data="multipleSelectionData" border style="width: 100%">
              <!-- dataId -->
              <el-table-column prop="dataId" label="Data Id" width="225">
              </el-table-column>
              <!-- 分组名称 -->
              <el-table-column prop="groupName" label="分组名称" width="168">
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </div>

      <!-- 如果多选的数据为空（则提示不可以批量删除） -->
      <div class="content" v-if="this.multipleSelectionData.length <= 0">
        <el-row :gutter="24" style="margin-left: 10px">
          <!-- 提示 -->
          <el-col :span="24" style="margin-bottom: 10px">
            您还没有选择需要批量删除的配置！
          </el-col>
        </el-row>
      </div>

      <!-- 底部插槽(如果多选的数据不为空) -->
      <div
        slot="footer"
        v-if="this.multipleSelectionData.length > 0"
        class="dialog-footer"
      >
        <el-button type="primary" @click="batchDeleteConfig">确定</el-button>
        <el-button @click="clickCloseBatchDeleteDialog">取消</el-button>
      </div>

      <!-- 底部插槽(如果多选的数据为空) -->
      <div
        slot="footer"
        v-if="this.multipleSelectionData.length <= 0"
        class="dialog-footer"
      >
        <el-button type="primary" @click="clickCloseBatchDeleteDialog"
          >确定</el-button
        >
      </div>
    </el-dialog>

    <!-- 克隆配置dialog -->
    <el-dialog :visible.sync="openCloneDialog" top="15vh" width="36%">
      <!-- 标题插槽 -->
      <div slot="title">
        <i
          class="el-icon-circle-check"
          style="color: #f1c826; font-size: 23px; margin-right: 5px"
        ></i>
        <span style="font-size: 20px">克隆配置</span>
      </div>
      <!-- 表单 -->
      <el-form
        :model="cloneConfigDialogForm"
        :rules="cloneConfigDialogFormRules"
        ref="cloneConfigDialogForm"
      >
        <!-- 内容 -->
        <el-row :gutter="24" style="margin-left: 30px">
          <!-- 源空间(需要克隆的配置来自于哪个命名空间) -->
          <el-col :span="24" style="margin-bottom: 10px">
            源命名空间: <span style="color: rgb(73, 210, 231)">public</span>
          </el-col>

          <!-- 展示克隆数量 -->
          <el-col :span="24" style="margin-bottom: 10px">
            已选中需要进行克隆的配置数量为:
            <span style="color: rgb(73, 210, 231)">6</span>
          </el-col>

          <!-- 克隆配置的目标命名空间(也就是指定将配置克隆到哪个命名空间上) -->
          <el-col :span="24" style="margin-bottom: 0px; height: 50px">
            <el-form-item prop="cloneTargetNamespace">
              目标命名空间
              <el-select
                v-model="cloneConfigDialogForm.cloneTargetNamespace"
                size="small"
                style="width: 355px"
                placeholder="请选择命名空间"
              >
                <!-- 遍历所有命名空间 -->
                <el-option
                  v-for="ns in namespaceData"
                  :key="ns.id"
                  :label="ns.namespaceName"
                  :value="ns.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <!-- 选择一个如果当前克隆的配置已存在目标命名空间后对该配置处理的策略（方式） -->
          <el-col :span="24" style="margin-bottom: 0px; height: 50px">
            如果目标命名空间存在相同配置则
            <el-select
              v-model="cloneConfigDialogForm.cloneConfigIfExistPolicy"
              size="small"
              style="width: 160px"
            >
              <el-option label="终止导入" value="abort"></el-option>
              <el-option label="跳过" value="skip"></el-option>
              <el-option label="覆盖" value="cover"></el-option>
            </el-select>
          </el-col>

          <!-- 开始克隆按钮 -->
          <el-col :span="24" style="margin-bottom: 0px; height: 50px">
            <el-button
              type="primary"
              size="medium"
              @click="clone('cloneConfigDialogForm')"
              >开始克隆</el-button
            >
          </el-col>
          <span
            style="color: rgb(0, 170, 0); font-weight: bold; margin-left: 10px"
          >
            修改 Data Id 和 分组名称 (可选操作)
          </span>

          <!-- 可编辑的克隆配置表格 -->
          <el-table
            :data="cloneConfigTableData"
            border
            size="mini"
            style="width: 92%; margin-top: 20px; margin-left: 10px"
            :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
          >
            <!-- Data Id -->
            <el-table-column
              prop="dataId"
              label="Data Id"
              align="center"
              width="225"
            >
              <template slot-scope="scope">
                <!-- 编辑Data Id -->
                <el-input
                  type="text"
                  size="small"
                  v-model.trim="scope.row.dataId"
                />
              </template>
            </el-table-column>

            <!-- 分组名称 -->
            <el-table-column
              prop="groupName"
              label="分组名称"
              align="center"
              width="223"
            >
              <template slot-scope="scope">
                <!-- 编辑框 -->
                <el-input
                  type="text"
                  size="small"
                  v-model.trim="scope.row.groupName"
                />
              </template>
            </el-table-column>
          </el-table>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
// 引入vue2-ace-editor代码编辑器
import Editor from "vue2-ace-editor";
import { getNamespaceList } from "@/api/namespace";
import { getConfigList, deleteConfig } from "@/api/config";

export default {
  name: "ConfigList",
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
      requestHeaders: {
        accessToken: "123456789",
      },
      // 多选框中勾选的所有数据
      multipleSelectionData: [],
      // 配置列表数据
      tableData: [],
      // 表格是否加载中（ true说明表格正在加载中,则会显示加载动画。反之false则关闭加载动画）
      tableLoading: false,
      // 总记录数
      totalCount: 200,
      // 当前页
      page: 1,
      // 每页展示的数量
      size: 10,
      // 是否打开示例代码dialog
      openSampleCodeDialog: false,
      // 当前选择的示例代码的标签
      currentSelectSampleCodeTab: "Java",
      // java示例代码内容
      javaSampleCodeContent: "",
      // SpringBoot示例代码内容
      springBootSampleCodeContent: "",
      // SpringCloud示例代码内容
      springCloudSampleCodeContent: "",
      // 是否打开删除dialog
      openDeleteDialog: false,
      // 当前点击删除配置的dialog所需要的数据（这个不是批量删除,而是删除）
      deleteConfigDialogData: {
        namespaceId: "",
        groupName: "",
        dataId: "",
      },
      // 是否打开批量删除dialog
      openBatchDeleteDialog: false,
      // 是否打开克隆dialog
      openCloneDialog: false,
      // 克隆配置dialog的表单
      cloneConfigDialogForm: {
        // 克隆配置的目标命名空间(也就是指定将配置克隆到哪个命名空间上)
        cloneTargetNamespace: "",
        // 选择一个如果当前克隆的配置已存在目标命名空间后对该配置处理的策略（方式）
        cloneConfigIfExistPolicy: "abort",
      },
      // 克隆配置dialog的表单校验规则
      cloneConfigDialogFormRules: {
        cloneTargetNamespace: [
          { required: true, message: "请选择命名空间", trigger: "change" },
        ],
      },
      // 想要进行克隆的配置表格数据(也就是多选出来的数据（multipleSelectionData）的深拷贝对象,深拷贝的作用是避免在修改克隆表格数据的时候影响到tableData对象的数据)
      cloneConfigTableData: [],
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
  // “（第一次）进入该路由、点击浏览器的刷新按钮后”会执行该生命周期。
  created() {
    // 加载命名空间数据
    this.loadNamespaceData();
    // 加载表格数据
    this.loadTableData();
  },
  // “（每次）进入该路由、点击浏览器的刷新按钮后”会执行该生命周期。
  // 作用是: 实现在一个路由组件中部分数据缓存（比如currentSelectedNamespaceId、page、size）、部分数据重新加载的功能（比如tableData、totalCount）。
  // 可以在被keep-alive缓存的路由组件中（比如当前组件）“刷新部分缓存数据”（比如tableData、totalCount）,
  // 这样我们每次进入这个路由的时候,（tableData、totalCount）都会重新刷新，而该vue路由组件其余定义data数据（比如currentSelectedNamespaceId、page、size）保持不变。
  activated() {
    // 加载命名空间数据
    this.loadNamespaceData();
    // 加载表格数据
    this.loadTableData();
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
      require("brace/mode/json");
      require("brace/mode/java");

      // 编译器代码段（html、java、javascript、golang、json、mysql、python、properties、sql、xml、yaml 等）
      require("brace/snippets/json");
      require("brace/mode/java");
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
    // 加载表格数据
    loadTableData() {
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
        // 从后端分页的获取配置列表的数据
        getConfigList(
          currentSelectedNamespaceId,
          groupName,
          dataId,
          page,
          size
        ).then((response) => {
          // 后端返回给前端的result对象
          let result = response.data;
          // 将数据放到vue中
          this.tableData = result.data.pagedList;
          this.totalCount = result.data.totalCount;
          // 关闭表格的加载动画
          this.tableLoading = false;
        });
      }, 500);
    },
    // 点击切换命名空间
    namespaceToggle(selectedNamespaceId) {
      this.currentSelectedNamespaceId = selectedNamespaceId;
      // 开启表格的加载动画
      this.tableLoading = true;
      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        // 指定分组名
        let groupName = this.queryCondition.groupName;
        // 指定dataid
        let dataId = this.queryCondition.dataId;
        // 是否隐藏空服务
        // 将当前页重置回第 1 页（分页标签上的current-page属性必须加上.sync,不然无法将当前页重置回第 1 页）
        this.page = 1;
        // 每页展示的数量
        let size = this.size;
        // 从后端分页的获取配置列表的数据
        getConfigList(
          this.currentSelectedNamespaceId,
          groupName,
          dataId,
          this.page,
          size
        ).then((response) => {
          // 后端返回给前端的result对象
          let result = response.data;
          // 将数据放到vue中
          this.tableData = result.data.pagedList;
          this.totalCount = result.data.totalCount;
          // 关闭表格的加载动画
          this.tableLoading = false;
        });
      }, 500);
    },
    // 跳转到创建配置路由
    createConfig() {
      this.$router.push({
        path: "/config/create",
        query: {
          namespaceId: this.currentSelectedNamespaceId,
        },
      });
    },
    // 点击查询
    query() {
      // 开启表格的加载动画
      this.tableLoading = true;

      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        //当前选择的命名空间的id
        let currentSelectedNamespaceId = this.currentSelectedNamespaceId;
        // 指定分组名
        let groupName = this.queryCondition.groupName;
        // 指定dataid
        let dataId = this.queryCondition.dataId;
        // 当前页
        let page = this.page;
        // 每页展示的数量
        let size = this.size;
        // 从后端分页的获取配置列表的数据
        getConfigList(
          currentSelectedNamespaceId,
          groupName,
          dataId,
          page,
          size
        ).then((response) => {
          // 后端返回给前端的result对象
          let result = response.data;
          // 将数据放到vue中
          this.tableData = result.data.pagedList;
          this.totalCount = result.data.totalCount;
          // 关闭表格的加载动画
          this.tableLoading = false;
        });
      }, 500);
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
    configDetail(namespaceId, groupName, dataId) {
      this.$router.push({
        path: "/config/detail",
        query: {
          namespaceId: namespaceId,
          groupName: groupName,
          dataId: dataId,
        },
      });
    },
    // 打开示例代码
    sampleCode(namespaceId, groupName, dataId) {
      this.openSampleCodeDialog = true;
      // 从后端获取默认的（Java）示例代码内容
      this.javaSampleCodeContent = "";
    },
    // 切换示例代码回调方法
    toggleSampleCode(tab) {
      // 获取当前选择的标签的名称
      let currentSelectTabName = tab.name;

      // 根据当前选择的标签的名称,从后端获取指定的示例代码内容
    },
    // 跳转修改/编辑配置
    modifyConfig(namespaceId, groupName, dataId) {
      this.$router.push({
        path: "/config/modify",
        query: {
          namespaceId: namespaceId,
          groupName: groupName,
          dataId: dataId,
        },
      });
    },
    // 点击打开删除配置dialog(deleteConfigDialogData是用户所点击删除的那一行配置信息)
    clickOpenDeleteDialog(namespaceId, groupName, dataId) {
      // 记录当前点击删除配置的dialog所需要的数据（这个不是批量删除,而是删除）
      this.deleteConfigDialogData = {
        namespaceId: namespaceId,
        groupName: groupName,
        dataId: dataId,
      };
      // 打开dialog
      this.openDeleteDialog = true;
    },
    // 删除配置
    deleteConfig() {
      let namespaceId = this.deleteConfigDialogData.namespaceId;
      let groupName = this.deleteConfigDialogData.groupName;
      let dataId = this.deleteConfigDialogData.dataId;
      // 请求后端进行删除
      deleteConfig(namespaceId, groupName, dataId).then((response) => {
        let result = response.data;
        if (result.data == true) {
          // 关闭删除配置dialog
          this.openDeleteDialog = false;
          // 重新加载tableData数据（记住要保留当前分页）
          this.loadTableData();
        }
      });
    },
    // 自定义下拉菜单（ dropdown ）传值(也可以说是命令command)
    customDropdownCommand(index, namespaceId, groupName,dataId) {
      return {
        index: index,
        namespaceId: namespaceId,
        groupName: groupName,
        dataId: dataId
      };
    },
    // 点击“更多”选项所展开的下拉菜单项
    clickMoreDropdownItem(customDropdownCommand) {
      console.log(customDropdownCommand)
      // 点击配置版本
      if (customDropdownCommand.index == 0) {
        // // 使用name+params进行跳转路由,特点是“url不会出现请求参数”（不会出现例如这种url格式:?groupName=aa&dataId=bb）
        // this.$router.push({
        //   // 路由名称
        //   name:'configVersionList',
        //   params: {
        //     namespaceId: customDropdownCommand.namespaceId,
        //     groupName: customDropdownCommand.groupName,
        //     dataId: customDropdownCommand.dataId,
        //   },
        // });
        // 使用name+params进行跳转路由,特点是“url不会出现请求参数”（不会出现例如这种url格式:?groupName=aa&dataId=bb）
        this.$router.push({
          // 路由名称
          path:'/config/version/list',
          query: {
            namespaceId: customDropdownCommand.namespaceId,
            groupName: customDropdownCommand.groupName,
            dataId: customDropdownCommand.dataId,
          },
        });
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
    batchDeleteConfig() {
      // 获取所有多选的数据
      let multipleSelectionData = this.multipleSelectionData;
      // 根据所有多选的数据去后端进行批量删除

      // 重新加载tableData数据
      this.loadData();
      // 关闭批量删除dialog
      this.openBatchDeleteDialog = false;
    },
    // 点击打开克隆dialog
    clickOpenCloneDialog() {
      this.openCloneDialog = true;
      // 将multipleSelectionData对象深拷贝到cloneConfigTableData对象,深拷贝的作用是避免在修改克隆表格数据的时候影响到tableData对象的数据
      this.cloneConfigTableData = this.deepCopy(this.multipleSelectionData);
    },
    // 开始克隆
    clone(cloneConfigDialogForm) {
      this.$refs[cloneConfigDialogForm].validate((valid) => {
        if (valid) {
          alert("克隆成功");
        } else {
          console.log("克隆失败");
          return false;
        }
      });
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
    // page（当前页）改变时触发
    handlePageChange(page) {
      console.log("page=" + page);
    },
    // size（每页展示的数量）改变时触发
    handleSizeChange(size) {
      console.log("size=" + size);
    },
    // 将创建/修改的配置进行发布
    publishConfig() {},
    // 深拷贝(将obj对象进行深拷贝,返回值就是深拷贝出来的对象)
    deepCopy(obj) {
      // 判断是否是对象
      if (typeof obj !== "object") return;
      // 判断obj类型，根据类型新建一个对象或者数组
      var newObj = obj instanceof Array ? [] : {};
      // 遍历对象，进行赋值
      for (var key in obj) {
        if (obj.hasOwnProperty(key)) {
          let val = obj[key];
          // 判断属性值的类型，如果是对象，递归调用deepCopy
          newObj[key] = typeof val === "object" ? this.deepCopy(val) : val;
        }
      }
      return newObj;
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