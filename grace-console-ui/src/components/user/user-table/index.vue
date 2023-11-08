<template>
  <div class="common-table">
    <el-table :data="tableData" border stripe style="width: 100%">
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
            v-hasPerm="['user:add']"
            size="mini"
            type="primary"
            @click="handleEdit(scope.row)"
            >编辑</el-button
          >
          <el-button
            v-hasPerm="['assign:role']"
            type="warning"
            size="mini"
            @click="handleAssignRole(scope.row)"
            >分配角色</el-button
          >
          <el-button
            v-hasPerm="['user:delete']"
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-row :gutter="24" style="margin-top: 30px">
      <el-col :span="10" :offset="14">
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
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "UserTable",
  props: {
    tableData: {
      type: Array,
      default: [],
    },
    totalCount: Number,
    page: Number,
    size: Number,
  },
  data() {
    return {};
  },
  methods: {
    handleEdit(row) {
      this.$emit("edit", row);
    },
    handleDelete(row) {
      this.$emit("del", row);
    },
    changePage(page) {
      this.$emit("changePage", page);
    },
    //当点击分配角色选项时弹出dialog之前执行的方法
    handleAssignRole(row) {
      this.$emit("assignRole", row);
    },
  },
};
</script>
<style lang="less" scoped>
.common-table {
  height: calc(100% - 62px);
  background-color: #fff;
  position: relative;
}
.pager {
  position: absolute;
  // bottom: 0;
  right: 20px;
}
</style>
