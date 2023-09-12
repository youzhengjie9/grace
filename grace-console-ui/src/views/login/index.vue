<template>
  <div>
    <div class="login-box">
      <el-card class="login-card">
        <!-- 登录表单组件 -->
        <login-form
          :loginForm="loginForm"
          :formRules="formRules"
          @login="login"
          ref="loginFormComponent"
          class="login-form"
        />
      </el-card>
    </div>
  </div>
</template>

<script>
import LoginForm from "@/components/LoginForm/index.vue";

export default {
  components: {
    LoginForm,
  },
  data() {
    return {
      // 登录表单
      loginForm: {
        // 帐号
        username: "",
        // 密码
        password: "",
      },
      // 表单校验规则
      formRules: {
        //帐号校验规则
        username: [
          {
            required: true, //必填项
            message: "请输入帐号",
            trigger: "blur",
          },
          {
            min: 3, //长度不能小于3位
            max: 15, //长度不能大于15位
            message: "帐号长度要在3-15位之间",
            trigger: "blur",
          },
        ],
        //密码校验规则
        password: [
          {
            required: true, //必填项
            message: "请输入密码",
            trigger: "blur",
          },
          {
            min: 5, //长度不能小于5位
            max: 20, //长度不能大于20位
            message: "密码长度要在5-20位之间",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    // 登录方法
    login(loginForm) {
      this.$refs["loginFormComponent"].$refs[loginForm].validate((valid) => {
        // 如果前端表单格式校验通过
        if (valid) {
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>

.login-box{

  /* 让div的大小刚好铺满整个屏幕 */
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  position: fixed;
  /* 背景图片 */
  /* background: url("@/assets/login-page.jpg"); */
  /* 背景图片大小铺满整个屏幕 */
  /* background-size: 100% 100%; */

}

.login-card {
  width: 33%;
  
  /* 水平居中 + 垂直移动一部分距离 */
  position: absolute;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  
  /* 边距 */
  padding-top: 30px;
}

</style>