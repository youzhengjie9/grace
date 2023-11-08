<template>
  <div class="manage">
    <!-- 新增/修改角色的dialog -->
    <el-dialog
      :title="operateType === 'add' ? '新增角色' : '修改角色'"
      :visible.sync="openAddOrModifyRoleDialog"
    >
      <!-- dialog的内容 -->
      <role-form
        :formLabel="opertateFormLabel"
        :form="operateForm"
        ref="form"
      ></role-form>

      <!-- dialog的底部 -->
      <div slot="footer" class="dialog-footer">
        <el-button @click="openAddOrModifyRoleDialog = false">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </div>
    </el-dialog>

    <!-- 分配菜单dialog -->
    <el-dialog :title="assignMenuTitle" :visible.sync="openAssignMenuDialog">
      <!-- 树形控件 ，node-key指定的是后台查出来的每一行记录的id字段名-->
      <el-tree
        :data="menuData"
        show-checkbox
        default-expand-all
        node-key="id"
        ref="tree"
        highlight-current
        :default-checked-keys="assignMenuDefaultCheckedArray"
        :props="defaultProps"
      >
      </el-tree>

      <!-- dialog的底部 -->
      <div slot="footer" class="dialog-footer">
        <el-button @click="openAssignMenuDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmAssignMenu">确定</el-button>
      </div>
    </el-dialog>

    <!-- 表格的头部,例如新增、导入、搜索框。 -->
    <el-row :gutter="20">
      <el-col :span="6">
        <el-button v-hasPerm="['role:add']" type="primary" @click="addRole"
          >+ 新增</el-button
        >
        <el-button type="primary" @click="exportAllRole"> 导出</el-button>
      </el-col>
      <!-- 搜索表单组件 -->
      <el-col :span="6" :offset="12">
        <search-form
          :formLabel="formLabel"
          :form="searchForm"
          :inline="true"
          ref="form"
        >
          <el-button type="primary" @click="searchRole(searchForm.keyword)"
            >搜索</el-button
          >
        </search-form>
      </el-col>
    </el-row>

    <el-table
      :data="tableData"
      border
      stripe
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      v-loading="tableLoading"
      element-loading-background="rgba(255, 255, 255, .5)"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      style="width: 100%"
    >
      <el-table-column
        prop="id"
        label="id"
        width="130"
        show-overflow-tooltip
        sortable
      >
      </el-table-column>

      <el-table-column
        prop="name"
        label="角色名称"
        width="135"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column
        prop="roleKey"
        label="角色关键字"
        width="150"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column prop="status" label="角色状态" width="85">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.status === true"
            type="success"
            disable-transitions
            >正常
          </el-tag>
          <el-tag
            v-if="scope.row.status === false"
            type="danger"
            disable-transitions
            >停用
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="createTime"
        label="创建时间"
        width="110"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateformat("YYYY-MM-DD") }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="updateTime"
        label="最后一次修改时间"
        width="180"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="scope">
          <span>{{
            scope.row.updateTime | dateformat("YYYY-MM-DD HH:mm:ss")
          }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="remark"
        label="备注"
        width="150"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button
            v-hasPerm="['role:modify']"
            size="mini"
            type="primary"
            v-if="scope.row.id != 2001"
            @click="editRole(scope.row)"
            >编辑</el-button
          >
          <el-button
            v-hasPerm="['assign:menu']"
            type="warning"
            size="mini"
            @click="assignMenu(scope.row)"
            >分配菜单</el-button
          >
          <el-button
            v-hasPerm="['role:delete']"
            size="mini"
            type="danger"
            v-if="scope.row.id != 2001"
            @click="delRole(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-row :gutter="24" style="margin-top: 30px">
      <el-col :span="8" :offset="16">
        <div class="grid-content bg-purple">
          <el-pagination
            background
            class="pager"
            layout="total, prev, pager, next"
            prev-text="上一页"
            next-text="下一页"
            :current-page.sync="page"
            :page-size="size"
            :total="totalCount"
            @current-change="handlePageChange"
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import RoleForm from "@/components/role/role-form/index.vue";
import SearchForm from "@/components/common/SearchForm.vue";
import {
  getRoleList,
  getRoleListByRoleName,
  getAllRole,
  getUserCheckedRoleByUserId,
  addRole,
  modifyRole,
  deleteRole,
  assignMenu,
} from "@/api/role";

import { buildAssignMenuTree } from "@/api/menuTree";

import { getRoleCheckedMenuByRoleId } from "@/api/menu";

import { exportAllRole } from "@/api/exportExcel";

export default {
  name: "RoleList",
  components: {
    RoleForm,
    SearchForm,
  },
  data() {
    return {
      //判断新增还是修改
      operateType: "add",
      //分配菜单dialog标题
      assignMenuTitle: "分配菜单",
      // true则打开新增或者修改的表单dialog，false则关闭
      openAddOrModifyRoleDialog: false,
      //true则打开分配菜单的表单dialog，false则关闭
      openAssignMenuDialog: false,
      //记录当前需要分配菜单的角色id
      currentAssignMenuRoleId: "",
      //当前模式：正常查询或者搜索（默认是查询）
      currentModel: "select",
      //当前搜索的关键字
      currentSearchKeyword: "",
      assignMenuDefaultCheckedArray: [],
      //动态生成新增或者修改的表单内容
      opertateFormLabel: [
        {
          model: "name",
          label: "角色名称",
          type: "input",
        },
        {
          model: "roleKey",
          label: "角色关键字",
          type: "input",
        },
        {
          model: "status",
          label: "是否启用",
          type: "switch",
        },
        {
          model: "remark",
          label: "备注",
          type: "input",
        },
      ],
      //当新增或者修改时，表单数据就会同步到这里
      operateForm: {
        name: "",
        roleKey: "",
        status: "",
        remark: "",
      },
      formLabel: [
        {
          model: "keyword",
          label: "",
          type: "input",
        },
      ],
      searchForm: {
        keyword: "",
      },
      //表格数据
      tableData: [],
      // 表格是否加载中（ true说明表格正在加载中,则会显示加载动画。反之false则关闭加载动画）
      tableLoading: false,
      // 总记录数
      totalCount: 200,
      // 当前页
      page: 1,
      // 每页展示的数量
      size: 7,
      //分配菜单dialog中的菜单数据
      menuData: [],
      //分配菜单dialog中的树形控件所需要的配置
      defaultProps: {
        //子节点数组名（我们后台查出来的子节点数组名为children）
        children: "children",
        //分配菜单dialog中的树形控件每一行的展示的名字（我们后台查的每一项名字为menuName）
        label: "menuName",
      },
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    // 加载数据
    loadData() {
      this.tableLoading = true;
      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        //如果是默认的select模式：
        if (
          this.currentModel === "select" ||
          this.currentSearchKeyword === ""
        ) {
          getRoleList(this.page, this.size).then((response) => {
            let result = response.data;
            let roleList = result.data.pagedList;
            for (let i = 0; i < roleList.length; i++) {
              //status
              if (roleList[i].status === 0) {
                roleList[i].status = true;
              } else {
                roleList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = roleList;
            this.totalCount = result.data.totalCount;
            // 关闭表格的加载动画
            this.tableLoading = false;
          });
        } else if (this.currentModel === "search") {
          this.searchRole(this.currentSearchKeyword);
        }
      }, 500);
    },
    //搜索角色
    searchRole(keyword) {
      this.tableLoading = true;
      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        //切换成搜索模式
        this.currentModel = "search";
        //记录当前搜索的关键字
        this.currentSearchKeyword = keyword;
        //开始搜索
        getRoleListByRoleName(keyword, this.page, this.size).then(
          (response) => {
            let result = response.data;
            let roleList = result.data.pagedList;
            for (let i = 0; i < roleList.length; i++) {
              //status
              if (roleList[i].status === 0) {
                roleList[i].status = true;
              } else {
                roleList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = roleList;
            this.totalCount = result.data.totalCount;
            // 关闭表格的加载动画
            this.tableLoading = false;
          }
        );
      }, 500);
    },
    // page（当前页）改变时触发
    handlePageChange(page) {
      this.tableLoading = true;
      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        //如果是默认的select模式：
        if (
          this.currentModel === "select" ||
          this.currentSearchKeyword === ""
        ) {
          getRoleList(page, this.size).then((response) => {
            let result = response.data;
            let roleList = result.data.pagedList;
            for (let i = 0; i < roleList.length; i++) {
              //status
              if (roleList[i].status === 0) {
                roleList[i].status = true;
              } else {
                roleList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = roleList;
            this.totalCount = result.data.totalCount;
            // 关闭表格的加载动画
            this.tableLoading = false;
          });
        } else if (this.currentModel === "search") {
          //切换成搜索模式
          this.currentModel = "search";
          //开始搜索
          getRoleListByRoleName(keyword, page, this.size).then((response) => {
            let result = response.data;
            let roleList = result.data.pagedList;
            for (let i = 0; i < roleList.length; i++) {
              //status
              if (roleList[i].status === 0) {
                roleList[i].status = true;
              } else {
                roleList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = roleList;
            this.totalCount = result.data.totalCount;
            // 关闭表格的加载动画
            this.tableLoading = false;
          });
        }
      }, 500);
    },
    confirm() {
      if (this.operateType === "add") {
        addRole(this.operateForm)
          .then((response) => {
            if (response.data.code == 200) {
              this.$message({
                showClose: true,
                message: "添加成功",
                type: "success",
                duration: 1000,
              });
              this.openAddOrModifyRoleDialog = false;
              this.loadData();
            } else {
              this.$message({
                showClose: true,
                message: "添加失败",
                type: "error",
                duration: 1000,
              });
            }
          })
          .catch((err) => {
            this.$message({
              showClose: true,
              message: "添加失败",
              type: "error",
              duration: 1000,
            });
          });
      } else {
        modifyRole(this.operateForm)
          .then((response) => {
            if (response.data.code == 200) {
              this.$message({
                showClose: true,
                message: "修改成功",
                type: "success",
                duration: 1000,
              });
              this.openAddOrModifyRoleDialog = false;
              this.loadData();
            } else {
              this.$message({
                showClose: true,
                message: "修改失败",
                type: "error",
                duration: 1000,
              });
            }
          })
          .catch((err) => {
            this.$message({
              showClose: true,
              message: "修改失败",
              type: "error",
              duration: 1000,
            });
          });
      }
    },
    //新增角色
    addRole() {
      this.openAddOrModifyRoleDialog = true;
      this.operateType = "add";
      this.operateForm = {
        name: "",
        roleKey: "",
        status: false,
        remark: "",
      };
    },
    //修改角色
    editRole(row) {
      this.operateType = "edit";
      this.openAddOrModifyRoleDialog = true;
      this.operateForm = row;
    },
    //删除角色
    delRole(row) {
      this.$confirm("是否删除该角色？", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const id = row.id;
          deleteRole(id)
            .then((response) => {
              if (response.data.code == 200) {
                this.$message({
                  showClose: true,
                  type: "success",
                  message: "删除成功",
                });
                //刷新数据
                this.loadData();
              } else {
                this.$message({
                  showClose: true,
                  type: "error",
                  message: "删除失败",
                });
              }
            })
            .catch((err) => {
              this.$message({
                showClose: true,
                type: "error",
                message: "删除失败",
              });
            });
        })
        .catch((err) => {});
    },
    //当点击分配菜单选项时弹出dialog之前执行的方法
    assignMenu(row) {
      //记录当前需要分配菜单的角色id
      this.currentAssignMenuRoleId = row.id;
      //打开分配菜单dialog
      this.openAssignMenuDialog = true;

      //清空之前的数据
      this.menuData = [];
      this.assignMenuDefaultCheckedArray = [];

      //给menuData放数据
      buildAssignMenuTree().then((res) => {
        //把后台拿到的json数组转成Array
        this.menuData = JSON.parse(res.data.data);

        //设置默认选中
        getRoleCheckedMenuByRoleId(this.currentAssignMenuRoleId).then((res) => {
          //后端穿过来对象数据，我们要对这个数据进行下面的一些改变
          let objectsData = res.data.data;
          //由于默认选中设置只认[3001,3003]格式，所以我们要对后端数据进行一个处理
          this.assignMenuDefaultCheckedArray = [];

          //如果这个数据有菜单，则把这个数据push到assignMenuDefaultCheckedArray数组中
          if (objectsData.length !== 0) {
            for (let i = 0; i < objectsData.length; i++) {
              this.assignMenuDefaultCheckedArray.push(objectsData[i].id);
            }
          }
        });
      });
    },
    //当点击确认分配菜单时回调的方法
    confirmAssignMenu() {
      let assignMenuDTO = {
        menuList: this.$refs.tree.getCheckedKeys(),
        roleId: this.currentAssignMenuRoleId,
      };
      assignMenu(assignMenuDTO)
        .then((response) => {
          if (response.data.code == 200) {
            this.$message({
              showClose: true,
              message: "分配菜单成功",
              type: "success",
              duration: 1000,
            });
            this.openAssignMenuDialog = false;
          } else {
            this.$message({
              showClose: true,
              message: "分配菜单失败",
              type: "error",
              duration: 1000,
            });
          }
        })
        .catch((err) => {
          this.$message({
            showClose: true,
            message: "分配菜单失败",
            type: "error",
            duration: 1000,
          });
        });
    },
    //导出所有角色信息
    exportAllRole() {
      this.$confirm("是否导出所有角色的数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //调用导出所有用户信息api
          exportAllRole()
            .then((response) => {
              // -----导出excel固定写法，直接复制即可-----
              let disposition = response.headers["content-disposition"];
              let filename = disposition
                ? disposition.substring(
                    disposition.indexOf("=") + 1,
                    disposition.indexOf(".")
                  )
                : "下载文件";
              let newName = decodeURI(escape(filename));
              let extName = disposition.substring(disposition.indexOf(".") + 1);
              let blob = new Blob([response.data], {
                type: "application/vnd.ms-excel",
              });
              let link = document.createElement("a");
              let evt = document.createEvent("HTMLEvents");
              evt.initEvent("click", false, false);
              link.href = URL.createObjectURL(blob);
              link.download = newName + "." + extName;
              link.style.display = "none";
              document.body.appendChild(link);
              link.click();
              window.URL.revokeObjectURL(link.href);
            })
            .catch((err) => {
              this.$message({
                showClose: true,
                message: "导出excel失败",
                type: "error",
                duration: 1000,
              });
            });
        })
        .catch((err) => {});
    },
  },
};
</script>
<style lang="less" scoped>
</style>
