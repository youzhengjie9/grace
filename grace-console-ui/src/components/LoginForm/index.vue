<template>
  <div class="loginForm">
    <div class="title">
      <span>系统登录</span>
    </div>
    <el-form
      :model="loginForm"
      :rules="formRules"
      ref="loginForm"
      label-width="100px"
    >
      <!-- 帐号 -->
      <el-form-item label="帐号" prop="username">
        <el-input
          v-model="loginForm.username"
          autocomplete="off"
          placeholder="请输入账号"
          style="width: 85%"
        ></el-input>
      </el-form-item>

      <!-- 密码 -->
      <el-form-item label="密码" prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          autocomplete="off"
          placeholder="请输入密码"
          style="width: 85%"
        ></el-input>
      </el-form-item>

      <!-- 登录按钮 -->
      <el-form-item>
        <el-button
          type="primary"
          @click="$emit('login', 'loginForm')"
          style="width: 230px;margin-left: 20px;"
          >登录</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "LoginForm",
  props:{
    loginForm : Object,
    formRules : Object
  }
};
</script>

<style scoped>

.title {
  text-align: center;
  padding-bottom: 5px;
}
.title span {
  font-size: 30px;
  color: #000;
}


</style>