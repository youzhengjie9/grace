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
      <div style="cursor: pointer">
        <!-- <span
          style="
            color: rgb(102, 102, 102);
            padding-right: 10px;
            border: none;
            font-size: 14px;
          "
          >public</span
        > -->
  
        <!-- <span
          style="
            color: rgb(32, 155, 250);
            padding-right: 10px;
            border: none;
            font-size: 14px;
          "
          >public</span
        > -->

      </div>
      <div style="cursor: pointer">
        <span style="margin-right: 8px; color: rgb(153, 153, 153)">|</span
        ><span
          style="
            color: rgb(102, 102, 102);
            padding-right: 10px;
            border: none;
            font-size: 14px;
          "
          >dev</span
        >
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
                active-color="#1274e7"
                inactive-color="#f5f5f5"
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
          <el-button type="primary" size="small"
            >创建服务</el-button
          >
        </el-col>
      </el-row>
    </div>

    <!-- 表格内容  -->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="id" width="180">
      </el-table-column>
      <el-table-column prop="serviceName" label="服务名称" width="210">
      </el-table-column>
      <el-table-column prop="groupName" label="分组名称" width="210">
      </el-table-column>
      <el-table-column prop="instanceCount" label="实例数" width="150">
      </el-table-column>
      <el-table-column prop="healthInstanceCount" label="健康实例数" width="150">
      </el-table-column>
      <el-table-column prop="reachProtectionThreshold" label="触发保护阈值" width="160">
      </el-table-column>
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <a class="operation" href="#" @click="serviceDetail(scope.row)">服务详情</a>
          <span style="margin-right: 5px;">|</span>
          <a class="operation" href="#" @click="deleteService(scope.row)">删除服务</a>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      background
      layout="total, sizes, prev, pager, next"
      prev-text="上一页"
      next-text="下一页"
      :page-sizes="[10 , 20 , 30 , 50 , 100]"
      :total="totalCount"
      :page-size="pagesize"
      :current-page="currentPage"
      @size-change="handlePageSizeChange"
      @current-change="handleCurrentPageChange"
      >
    </el-pagination>
    
  </div>
</template>

<script>
export default {
  name: "ServiceList",
  data() {
    return {
      // 查询条件
      queryCondition: {
        // 服务名称
        serviceName: "",
        // 分组名称
        groupName: "",
        // 是否隐藏空服务
        hideEmptyService: true
      },
      // 表格数据
      tableData: [
        {
          id: 10001,
          serviceName: "abc",
          groupName: "DEFAULT_GROUP",
          instanceCount: 3,
          healthInstanceCount: 3,
          reachProtectionThreshold: "false",
        },
        {
          id: 10002,
          serviceName: "abc22",
          groupName: "DEFAULT_GROUP",
          instanceCount: 3,
          healthInstanceCount: 3,
          reachProtectionThreshold: "false",
        },
        {
          id: 10003,
          serviceName: "abc33",
          groupName: "DEFAULT_GROUP",
          instanceCount: 3,
          healthInstanceCount: 3,
          reachProtectionThreshold: "false",
        },
      ],
      // 总记录数
      totalCount: 200,
      // 每页展示的数量
      pagesize: 10,
      // 当前页
      currentPage: 1,
    };
  },
  methods: {
    // 点击查询
    query() {
      console.log(this.queryCondition)
    },
    // 服务详情
    serviceDetail(row){
      console.log(row);
    },
    // 删除服务
    deleteService(row){
      console.log(row);
    },
    // pageSize（每页展示的数量）改变时触发
    handlePageSizeChange(pageSize){
      console.log('pageSize='+pageSize);
    },
    // currentPage（当前页）改变时触发
    handleCurrentPageChange(currentPage){
      console.log('currentPage='+currentPage);
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