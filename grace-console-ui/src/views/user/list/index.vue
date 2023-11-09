<template>
  <div class="manage">
    <!-- 新增/修改用户的dialog -->
    <el-dialog
      :title="operateType === 'add' ? '新增用户' : '修改用户'"
      :visible.sync="openAddOrModifyUserDialog"
    >
      <!-- dialog的内容 -->
      <user-form
        :formLabel="opertateFormLabel"
        :form="operateForm"
        :operateType="operateType"
        ref="form"
      ></user-form>

      <!-- dialog的底部 -->
      <div slot="footer" class="dialog-footer">
        <el-button @click="openAddOrModifyUserDialog = false">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </div>
    </el-dialog>

    <!-- 分配角色dialog -->
    <el-dialog :title="assignRoleTitle" :visible.sync="assignRoleDialogShow">
      <!-- 分配角色dialog的内容 -->

      <el-table
        ref="multipleTable"
        :data="assignRoleTableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <!-- 全选 -->
        <el-table-column type="selection" width="55"> </el-table-column>

        <el-table-column prop="id" label="id" width="120" show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="name"
          label="角色名称"
          width="120"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column
          prop="roleKey"
          label="角色关键字"
          width="120"
          show-overflow-tooltip
        >
        </el-table-column>

        <el-table-column
          prop="remark"
          label="备注"
          width="120"
          show-overflow-tooltip
        >
        </el-table-column>
      </el-table>

      <!--  -->

      <!-- dialog的底部 -->
      <div slot="footer" class="dialog-footer">
        <el-button @click="assignRoleDialogShow = false">取消</el-button>
        <el-button type="primary" @click="confirmAssignRole">确定</el-button>
      </div>
    </el-dialog>

    <!-- 表格的头部,例如新增、导入、搜索框。 -->
    <el-row :gutter="20">
      <el-col :span="6">
        <el-button v-hasPerm="['user:add']" type="primary" @click="addUser"
          >+ 新增</el-button
        >
        <el-button type="primary" @click="exportAllUser"> 导出</el-button>
      </el-col>
      <!-- 搜索表单组件 -->
      <el-col :span="6" :offset="12">
        <search-form
          :formLabel="formLabel"
          :form="searchForm"
          :inline="true"
          ref="form"
        >
          <el-button type="primary" @click="searchUser(searchForm.keyword)"
            >搜索</el-button
          >
        </search-form>
      </el-col>
    </el-row>

    <!-- 表格 -->
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
        width="200"
        show-overflow-tooltip
        sortable
      >
      </el-table-column>

      <el-table-column
        prop="username"
        label="用户名"
        width="200"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column prop="status" label="用户状态" width="118">
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
        width="170"
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
        width="260"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="scope">
          <span>{{
            scope.row.updateTime | dateformat("YYYY-MM-DD HH:mm:ss")
          }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button
            v-hasPerm="['user:modify']"
            size="mini"
            type="primary"
            v-if="scope.row.username != 'grace'"
            @click="editUser(scope.row)"
            >编辑</el-button
          >
          <el-button
            v-hasPerm="['assign:role']"
            type="warning"
            size="mini"
            v-if="scope.row.username != 'grace'"
            @click="assignRole(scope.row)"
            >分配角色</el-button
          >
          <el-button
            v-hasPerm="['user:delete']"
            size="mini"
            type="danger"
            v-if="scope.row.username != 'grace'"
            @click="delUser(scope.row)"
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
import UserForm from "../../../components/user/user-form/index.vue";
import SearchForm from "@/components/common/SearchForm.vue";
import {
  getUserList,
  getUserListByUsername,
  addUser,
  modifyUser,
  deleteUser,
  assignRole,
} from "@/api/user";

import { getAllRole, getUserCheckedRoleByUserId } from "@/api/role";
import { exportAllUser } from "@/api/exportExcel";

export default {
  name: "UserList",
  //注册组件
  components: {
    UserForm,
    SearchForm,
  },
  data() {
    return {
      //判断新增还是修改
      operateType: "add",
      //分配角色dialog标题
      assignRoleTitle: "分配角色",
      //true则打开新增或者修改的表单dialog，false则关闭
      openAddOrModifyUserDialog: false,
      //true则打开分配角色的表单dialog，false则关闭
      assignRoleDialogShow: false,
      //记录当前需要分配角色的用户id
      currentAssignRoleUserId: "",
      //当前模式：正常查询或者搜索（默认是查询）
      currentModel: "select",
      //当前搜索的关键字
      currentSearchKeyword: "",
      // 动态生成新增或者修改的表单内容
      opertateFormLabel: [
        {
          model: "username",
          label: "用户名",
          type: "input",
        },
        {
          model: "password",
          label: "密码",
          type: "password",
        },
        {
          model: "status",
          label: "是否启用",
          type: "switch",
        },
      ],
      //当新增或者修改时，表单数据就会同步到这里
      operateForm: {
        username: "",
        password: "",
        status: "",
      },
      //后端所有可供选择的角色数据都要存到这(这部分内容可以从后端数据库获取)
      assignRoleTableData: [],
      assignRoleSelectedList: [],
      // 搜索框
      formLabel: [
        {
          model: "keyword",
          label: "用户名",
          type: "input",
        },
      ],
      searchForm: {
        keyword: "",
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
      size: 7,
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
          getUserList(this.page, this.size).then((response) => {
            let result = response.data;
            let userList = result.data.pagedList;
            for (let i = 0; i < userList.length; i++) {
              // status
              if (userList[i].status == 0) {
                userList[i].status = true;
              } else {
                userList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = userList;
            this.totalCount = result.data.totalCount;
            // 关闭表格的加载动画
            this.tableLoading = false;
          });
        } else if (this.currentModel === "search") {
          this.searchUser(this.currentSearchKeyword);
        }
      }, 500);
    },
    //搜索,keywork就是搜索的关键字
    searchUser(keyword) {
      this.tableLoading = true;
      // 模拟延迟,让加载动画更明显
      setTimeout(() => {
        //切换成搜索模式
        this.currentModel = "search";
        //记录当前搜索的关键字
        this.currentSearchKeyword = keyword;
        getUserListByUsername(keyword, this.page, this.size).then(
          (response) => {
            let result = response.data;
            let userList = result.data.pagedList;
            for (let i = 0; i < userList.length; i++) {
              // status
              if (userList[i].status === 0) {
                userList[i].status = true;
              } else {
                userList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = userList;
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
          getUserList(page, this.size).then((response) => {
            let result = response.data;
            let userList = result.data.pagedList;
            for (let i = 0; i < userList.length; i++) {
              // status
              if (userList[i].status == 0) {
                userList[i].status = true;
              } else {
                userList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = userList;
            this.totalCount = result.data.totalCount;
            // 关闭表格的加载动画
            this.tableLoading = false;
          });
        } else if (this.currentModel === "search") {
          //切换成搜索模式
          this.currentModel = "search";
          getUserListByUsername(keyword, page, this.size).then((response) => {
            let result = response.data;
            let userList = result.data.pagedList;
            for (let i = 0; i < userList.length; i++) {
              // status
              if (userList[i].status === 0) {
                userList[i].status = true;
              } else {
                userList[i].status = false;
              }
            }
            // 将数据放到vue中
            this.tableData = userList;
            this.totalCount = result.data.totalCount;
            // 关闭表格的加载动画
            this.tableLoading = false;
          });
        }
      }, 500);
    },
    confirm() {
      //从vuex中获取表单提交过来的是否启用开关
      this.operateForm.status = this.$store.state.form.curSwitchStatus;
      //如果是添加操作
      if (this.operateType === "add") {
        addUser(this.operateForm)
          .then((res) => {
            if (res.data.code == 200) {
              this.$message({
                showClose: true,
                message: "添加成功",
                type: "success",
                duration: 1000,
              });
              this.openAddOrModifyUserDialog = false;
              //成功之后将表单vuex数据还原
              this.$store.dispatch("setCurSwitchStatus", true);
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
      }
      //反之则是修改操作
      else {
        modifyUser(this.operateForm)
          .then((res) => {
            if (res.data.code == 200) {
              this.$message({
                showClose: true,
                message: "修改成功",
                type: "success",
                duration: 1000,
              });
              this.openAddOrModifyUserDialog = false;

              //成功之后将表单vuex数据还原
              this.$store.dispatch("setCurSwitchStatus", true);
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
            console.log(err);
            this.$message({
              showClose: true,
              message: "修改失败",
              type: "error",
              duration: 1000,
            });
          });
      }
    },
    //新增用户
    addUser() {
      this.openAddOrModifyUserDialog = true;
      this.operateType = "add";
      this.operateForm = {
        // 给新增用户表单设置默认值
        username: "",
        password: "",
        status: true,
      };
    },
    //修改用户
    editUser(row) {
      this.operateType = "edit";
      this.openAddOrModifyUserDialog = true;
      this.operateForm = row;
    },
    //删除用户
    delUser(row) {
      this.$confirm("是否删除该用户？", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //id
          const id = row.id;
          deleteUser(id)
            .then((res) => {
              if (res.data.code == 200) {
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
        }).catch(err=>{
          
        })
    },
    //当点击分配角色选项时弹出dialog之前执行的方法
    assignRole(row) {
      //记录当前需要分配角色的用户id
      this.currentAssignRoleUserId = row.id;
      //打开分配角色dialog
      this.assignRoleDialogShow = true;
      //初始化角色列表数据，后端所有可选择的角色数据都要存到这(这部分内容可以从后端数据库获取)
      getAllRole().then((res1) => {
        this.assignRoleTableData = res1.data.data;
        //当分配角色时，表单数据就会同步到这里，这个数据后面要提交给后端，也就是用户更新后的角色数据
        //数据格式为[{id:'2002'}]
        getUserCheckedRoleByUserId(row.id).then((res2) => {
          this.assignRoleSelectedList = res2.data.data;
          this.assignRoleSelectedList.forEach((row) => {
            this.$refs.multipleTable.toggleRowSelection(
              this.assignRoleTableData.find((item) => {
                return row.id == item.id;
              }),
              true
            );
          });
        });
      });
    },
    //当点击确认分配角色时回调的方法
    confirmAssignRole() {
      let jsonData = {
        roles: this.assignRoleSelectedList,
        userId: this.currentAssignRoleUserId,
      };
      assignRole(jsonData)
        .then((res) => {
          if (res.data.code == 200) {
            this.$message({
              showClose: true,
              message: "分配角色成功",
              type: "success",
              duration: 1000,
            });
            //关闭dialog
            this.assignRoleDialogShow = false;
            //将currentAssignRoleUserId还原
            this.currentAssignRoleUserId = "";
            // this.getList();
          } else {
            this.$message({
              showClose: true,
              message: "分配角色失败",
              type: "error",
              duration: 1000,
            });
          }
        })
        .catch((err) => {
          this.$message({
            showClose: true,
            message: "分配角色失败",
            type: "error",
            duration: 1000,
          });
        });
    },
    //处理分配角色dialog的选中
    handleSelectionChange(val) {
      this.assignRoleSelectedList = val;
    },
    //导出所有用户信息
    exportAllUser() {
      this.$confirm("是否导出所有用户的数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //调用导出所有用户信息api
          exportAllUser()
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
    