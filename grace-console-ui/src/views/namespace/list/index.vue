<template>
  <div class="namespace-list-box">
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
        >命名空间</span
      >
    </div>

    <!-- 工具栏 -->
    <el-row :gutter="24">
      <!-- 新建命名空间 -->
      <el-col :span="5" :offset="18" style="margin-top: 10px">
        <!-- 新建命名空间 -->
        <el-button
          type="primary"
          size="medium"
          style="margin-left: 30px; margin-right: 10px"
          @click="clickOpenCreateNamespaceDialog"
          >新建命名空间</el-button
        >
        <!-- 刷新 -->
        <el-button size="medium" type="primary" plain @click="refresh"
          >刷新</el-button
        >
      </el-col>
    </el-row>

    <!-- 新建命名空间的dialog  -->
    <el-dialog :visible.sync="openCreateNamespaceDialog" top="15vh" width="50%">
      <!-- 标题插槽 -->
      <div slot="title">
        <span style="font-size: 20px">新建命名空间</span>
      </div>
      <!-- 内容表单 -->
      <el-form
        :model="createNamespaceForm"
        :rules="createNamespaceRules"
        ref="createNamespaceForm"
        label-width="125px"
      >
        <!-- 内容 -->
        <el-row :gutter="24" style="margin-left: 30px">
          <!-- 命名空间ID(不填则自动生成) -->
          <el-form-item prop="namespaceId">
            <!-- 标题 -->
            <span slot="label">
              <span style="font-weight: bold">命名空间ID(不填则自动生成)</span>
            </span>
            <el-input
              style="width: 80%"
              v-model="createNamespaceForm.namespaceId"
            ></el-input>
          </el-form-item>
          <!-- 命名空间名称 -->
          <el-form-item prop="namespaceName">
            <!-- 标题 -->
            <span slot="label">
              <span style="font-weight: bold">命名空间名称</span>
            </span>
            <el-input
              style="width: 80%"
              v-model="createNamespaceForm.namespaceName"
            ></el-input>
          </el-form-item>
          <!-- 描述 -->
          <el-form-item prop="namespaceDesc">
            <!-- 标题 -->
            <span slot="label">
              <span style="font-weight: bold">描述</span>
            </span>
            <el-input
              style="width: 80%"
              v-model="createNamespaceForm.namespaceDesc"
            ></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createNamespace">创建</el-button>
        <el-button @click="openCreateNamespaceDialog = false">取消</el-button>
      </div>
    </el-dialog>

    <!-- 表格内容  -->
    <el-table
      :data="tableData"
      border
      style="width: 100%; margin-top: 20px"
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      v-loading="tableLoading"
      element-loading-background="rgba(255, 255, 255, .5)"
      element-loading-text="加载中，请稍后..."
      element-loading-spinner="el-icon-loading"
    >
      <!-- 命名空间名称 -->
      <el-table-column prop="namespaceName" label="命名空间名称" width="235">
      </el-table-column>
      <!-- 命名空间ID -->
      <el-table-column prop="namespaceId" label="命名空间ID" width="290">
      </el-table-column>
      <!-- 描述 -->
      <el-table-column prop="namespaceDesc" label="描述" width="230">
      </el-table-column>

      <!-- 服务数 -->
      <el-table-column prop="serviceCount" label="服务数" width="130">
      </el-table-column>

      <!-- 配置数 -->
      <el-table-column prop="configCount" label="配置数" width="130">
      </el-table-column>

      <!-- 操作 -->
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <!-- 命名空间详情 -->
          <span
            class="operation"
            @click="clickOpenNamespaceDetailDialog(scope.row.namespaceId)"
            >详情</span
          >
          <span style="margin-right: 5px">|</span>


          <!-- 修改命名空间（可以进行点击操作）。除了默认的public命名空间之外,其他命名空间都会用这个 “编辑”  -->
          <span
            class="operation"
            v-if="scope.row.namespaceId != ''"
            @click="clickOpenModifyNamespaceDialog(scope.row)"
            >编辑</span
          >

          <!-- 修改命名空间（不能进行点击操作）。只有默认的public命名空间才会用这个 “编辑”  -->
          <span
            class="not-operation"
            v-if="scope.row.namespaceId == ''"
            >编辑</span
          >

          <span style="margin-right: 5px">|</span>

          <!-- 删除命名空间（可以进行点击操作）。除了默认的public命名空间之外,其他命名空间都会用这个 “删除” -->
          <span
            class="operation"
            v-if="scope.row.namespaceId != ''"
            @click="clickOpenDeleteNamespaceDialog(scope.row)"
            >删除</span
          >
           <!-- 删除命名空间（不能进行点击操作）。只有默认的public命名空间才会用这个 “删除”  -->
          <span
            class="not-operation"
            v-if="scope.row.namespaceId == ''"
            >删除</span
          >

        </template>
      </el-table-column>
    </el-table>

    <!-- 命名空间详情的dialog  -->
    <el-dialog :visible.sync="openNamespaceDetailDialog" top="15vh" width="30%">
      <!-- 标题插槽 -->
      <div slot="title">
        <i
          class="el-icon-circle-check"
          style="color: #f1c826; font-size: 23px; margin-right: 5px"
        ></i>
        <span style="font-size: 20px">命名空间详情</span>
      </div>
      <!-- 内容 -->
      <el-row :gutter="24" style="margin-left: 30px">
        <!-- 命名空间名称 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">命名空间名称: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ namespaceDetailDialogData.namespaceName }}
          </span>
        </el-col>

        <!-- 命名空间ID -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">命名空间ID: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ namespaceDetailDialogData.namespaceId }}
          </span>
        </el-col>

        <!-- 服务数 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">服务数: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ namespaceDetailDialogData.serviceCount }} / 200
          </span>
        </el-col>

        <!-- 配置数 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">配置数: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ namespaceDetailDialogData.configCount }} / 200
          </span>
        </el-col>

        <!-- 描述 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">描述: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ namespaceDetailDialogData.namespaceDesc }}
          </span>
        </el-col>
      </el-row>
      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="openNamespaceDetailDialog = false"
          >返回</el-button
        >
      </div>
    </el-dialog>

    <!-- 编辑命名空间的dialog  -->
    <el-dialog :visible.sync="openModifyNamespaceDialog" top="15vh" width="50%">
      <!-- 标题插槽 -->
      <div slot="title">
        <span style="font-size: 20px">编辑命名空间</span>
      </div>
      <!-- 内容表单 -->
      <el-form
        :model="modifyNamespaceForm"
        :rules="modifyNamespaceRules"
        ref="modifyNamespaceForm"
        label-width="125px"
      >
        <!-- 内容 -->
        <el-row :gutter="24" style="margin-left: 30px">
          <!-- 命名空间名称 -->
          <el-form-item prop="namespaceName">
            <!-- 标题 -->
            <span slot="label">
              <span style="font-weight: bold">命名空间名称</span>
            </span>
            <el-input
              style="width: 80%"
              v-model="modifyNamespaceForm.namespaceName"
            ></el-input>
          </el-form-item>
          <!-- 描述 -->
          <el-form-item prop="namespaceDesc">
            <!-- 标题 -->
            <span slot="label">
              <span style="font-weight: bold">描述</span>
            </span>
            <el-input
              style="width: 80%"
              v-model="modifyNamespaceForm.namespaceDesc"
            ></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyNamespace">确认修改</el-button>
        <el-button @click="openModifyNamespaceDialog = false">取消</el-button>
      </div>
    </el-dialog>

    <!-- 删除命名空间的dialog  -->
    <el-dialog :visible.sync="openDeleteNamespaceDialog" top="15vh" width="30%">
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
          <span style="font-size: 16px">确定要删除以下命名空间吗？</span>
        </el-col>
        <!-- 命名空间名称 -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">命名空间名称: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ deleteNamespaceDialogData.namespaceName }}
          </span>
        </el-col>

        <!-- 命名空间ID -->
        <el-col :span="24" style="margin-bottom: 10px">
          <span style="font-size: 16px">命名空间ID: </span>
          <span style="color: rgb(199, 37, 78); font-size: 16px">
            {{ deleteNamespaceDialogData.namespaceId }}
          </span>
        </el-col>
      </el-row>
      <!-- 底部插槽 -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="deleteNamespace">确定</el-button>
        <el-button @click="openDeleteNamespaceDialog = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
<script>
import { getNamespaceList } from "@/api/namespace";

export default {
  name: "NamespaceList",
  data() {
    return {
      // 是否打开新建命名空间的对话框（dialog）
      openCreateNamespaceDialog: false,
      // 创建命名空间表单
      createNamespaceForm: {
        // 命名空间ID(不填则自动生成)
        namespaceId: "",
        // 命名空间名称
        namespaceName: "",
        // 描述
        namespaceDesc: "",
      },
      // 创建命名空间规则
      createNamespaceRules: {
        // 命名空间名称
        namespaceName: [
          { required: true, message: "命名空间名称不能为空", trigger: "blur" },
        ],
      },
      // 请求头
      requestHeaders: { accessToken: "123456789" },
      // 命名空间列表数据
      tableData: [],
      // 表格是否加载中（ true说明表格正在加载中,则会显示加载动画。反之false则关闭加载动画）
      tableLoading: false,
      // 是否打开命名空间详情dialog
      openNamespaceDetailDialog: false,
      // namespace详情的dialog所需要的数据
      namespaceDetailDialogData: {
        // 命名空间名称
        namespaceName: "",
        // 命名空间ID
        namespaceId: "",
        // 服务数
        serviceCount: 0,
        // 配置数
        configCount: 0,
        // 描述
        namespaceDesc: "",
      },
      // 是否打开修改命名空间dialog
      openModifyNamespaceDialog: false,
      // 编辑命名空间表单
      modifyNamespaceForm: {
        // 命名空间名称
        namespaceName: "",
        // 描述
        namespaceDesc: "",
      },
      // 编辑命名空间规则
      modifyNamespaceRules: {
        // 命名空间名称
        namespaceName: [
          { required: true, message: "命名空间名称不能为空", trigger: "blur" },
        ],
      },
      // 是否打开删除命名空间dialog
      openDeleteNamespaceDialog: false,
      // 删除namespace的dialog所需要的数据
      deleteNamespaceDialogData: {
        // 命名空间名称
        namespaceName: "",
        // 命名空间ID
        namespaceId: "",
      },
      // 当前点击删除配置的dialog所需要的数据（这个不是批量删除,而是删除）
      deleteConfigDialogData: "",
    };
  },
  created() {
    // 加载数据
    this.loadData();
  },
  methods: {
    // 加载数据
    loadData() {
      // 开启表格的加载动画
      this.tableLoading = true;

      // 从后端获取tableData数据
      getNamespaceList().then((response) => {

        let result = response.data;
        this.tableData = result.data;

        // 关闭表格的加载动画
        this.tableLoading = false;
      })
      
    },
    // 跳转到创建配置路由
    createConfig() {
      this.$router.push({
        path: "/config/create",
      });
    },
    //刷新
    refresh() {
      this.loadData();
    },
    // 点击打开创建命名空间dialog
    clickOpenCreateNamespaceDialog() {
      this.openCreateNamespaceDialog = true;
    },
    // 创建命名空间
    createNamespace() {},
    // 点击打开命名空间详情dialog
    clickOpenNamespaceDetailDialog(namespaceId) {
      // 根据namespaceId从后端获取当前点击namespace详情的dialog所需要的数据
      (this.namespaceDetailDialogData = {
        // 命名空间名称
        namespaceName: "abc",
        // 命名空间ID
        namespaceId: "123",
        // 服务数
        serviceCount: 10,
        // 配置数
        configCount: 20,
        // 描述
        namespaceDesc: "666",
      }),
        (this.openNamespaceDetailDialog = true);
    },
    // 点击打开修改命名空间dialog
    clickOpenModifyNamespaceDialog(namespace) {
      // 深拷贝当前所点击的那行namespace记录
      let ns = this.deepCopy(namespace);

      this.modifyNamespaceForm = {
        namespaceName: ns.namespaceName,
        namespaceDesc: ns.namespaceDesc,
      };

      this.openModifyNamespaceDialog = true;
    },
    // 修改命名空间
    modifyNamespace() {},
    // 点击打开删除命名空间
    clickOpenDeleteNamespaceDialog(namespace) {
      // 记录当前点击删除namespace的dialog所需要的数据
      this.deleteNamespaceDialogData = {
        namespaceName: namespace.namespaceName,
        namespaceId: namespace.namespaceId,
      };
      // 打开dialog
      this.openDeleteNamespaceDialog = true;
    },
    // 删除命名空间
    deleteNamespace() {
      // 删除的命名空间id
      let namespaceId = this.deleteNamespaceDialogData.namespaceId;
      // 请求后端进行删除

      // 重新加载tableData数据
      this.loadData();
      // 关闭删除命名空间dialog
      this.openDeleteNamespaceDialog = false;
    },
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
/* span超链接（可以进行点击操作） */
.operation {
  color: #06c;
  cursor: pointer;
  text-decoration: none;
  margin-right: 5px;
}
.operation:hover {
  text-decoration: underline;
}

/* span超链接（不能进行点击操作） */
.not-operation {
  color: rgb(153, 153, 153);
  text-decoration: none;
  margin-right: 5px;
  /* 禁用点击操作 */
  cursor: not-allowed;
}


</style>