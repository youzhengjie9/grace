<template>
  <div class="page-header-box">
    <el-row :gutter="24">
      <el-col :span="3" style="margin-top: 12px">
        <!-- 左上角的logo -->
      </el-col>
      <el-col :span="7" :offset="14">
        <el-row :gutter="24">
          <el-col :span="4"
            ><a
              href="https://nacos.io/zh-cn/"
              target="_blank"
              class="menu-item-a"
              >首页</a
            ></el-col
          >
          <el-col :span="4"
            ><a
              href="https://nacos.io/zh-cn/docs/what-is-nacos.html"
              target="_blank"
              class="menu-item-a"
              >文档</a
            ></el-col
          >
          <el-col :span="4"
            ><a
              href="https://nacos.io/zh-cn/blog/index.html"
              target="_blank"
              class="menu-item-a"
              >博客</a
            ></el-col
          >
          <el-col :span="4"
            ><a
              href="https://nacos.io/zh-cn/community/index.html"
              class="menu-item-a"
              target="_blank"
              >社区</a
            ></el-col
          >

          <el-col :span="4">
            <a
              href="#"
              class="menu-item-a"
              @click="logout"
              >退出</a
            >
          </el-col>



        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "PageHeader",
  methods:{
    // 退出登录
    logout(){
      this.$confirm('是否退出登录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '退出成功'
          });
          // 跳转到登录页
          this.$router.push('/login')
        }).catch(() => {
                 
        });
    }
  }
};
</script>

<style scoped>
.menu-item-a {
  color: #fff;
  opacity: 0.6;
  text-decoration: none;
  font-size: 14px;
}

.menu-item-a:hover{
  opacity: 1;
  text-decoration: underline;
}

</style>