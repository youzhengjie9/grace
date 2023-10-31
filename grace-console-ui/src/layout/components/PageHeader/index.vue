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

          <!--  @click.prevent作用是禁止超链接跳转到指定的href  -->
          <el-col :span="4">
            <a href="#" class="menu-item-a" @click.prevent="logout">退出</a>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { logout } from "@/api/user";

export default {
  name: "PageHeader",
  methods: {
    // 退出登录
    logout() {
      this.$confirm("是否退出登录?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let accessToken = localStorage.getItem("accessToken");
          let refreshToken = localStorage.getItem("refreshToken");

          // 退出登录
          logout(accessToken, refreshToken).then((response) => {
            let result = response.data;
            if (result.data == true) {
              //调用vuex的方法，进行退出成功后的处理（比如清空localstorage和vuex数据）
              this.$store.dispatch("logoutSuccess");
              this.$message({
                showClose: true,
                message: "用户退出成功",
                type: "success",
                duration: 1000,
              });
              //跳转到登录页。
              //this.$router.replace和push区别是这个replace跳转后不允许使用浏览器回退功能
              //如果是push跳转页面则可以回退，对于这个退出功能来说建议使用replace
              this.$router.replace({ path: "/login" });
            } else {
              this.$message({
                showClose: true,
                message: "用户退出失败",
                type: "error",
                duration: 1000,
              });
            }
          });
        }).catch(() => {
          // 当点击退出登录弹出框的“取消”时,会走到这里,不需要做任何操作        
        });
    },
  },
};
</script>

<style scoped>
.menu-item-a {
  color: #fff;
  opacity: 0.6;
  text-decoration: none;
  font-size: 14px;
}

.menu-item-a:hover {
  opacity: 1;
  text-decoration: underline;
}
</style>