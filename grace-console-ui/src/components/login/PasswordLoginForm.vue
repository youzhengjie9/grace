<template>
  <div class="passwordLoginForm">
    <div class="title">
        <span>密码登录</span>
      </div>
      <el-form
        :model="passwordLoginForm"
        status-icon
        :rules="passwordLoginRules"
        ref="passwordLoginForm"
        label-width="100px"
        class="login-container"
      >
        <div class="list">
          <!-- 帐号 -->
          <el-form-item
            label="帐号"
            label-width="65px"
            prop="username"
            class="username"
          >
            <el-input
              type="input"
              v-model="passwordLoginForm.username"
              autocomplete="off"
              placeholder="请输入账号"
            ></el-input>
          </el-form-item>
        </div>
        <div class="list">
          <!-- 密码 -->
          <el-form-item label="密码" label-width="65px" prop="password">
            <el-input
              type="password"
              v-model="passwordLoginForm.password"
              autocomplete="off"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
        </div>
        <div class="captchaList">
          <!-- 验证码 -->
          <el-form-item label="验证码" label-width="65px"  prop="code">
            <el-col :span="12">
              <el-input
                type="input"
                style="width: 140px"
                v-model="passwordLoginForm.code"
                autocomplete="off"
                placeholder="请输入验证码"
              >
              </el-input>
            </el-col>
            <img
              :src="imageBase64"
              style="width: 130px"
              @click="$emit('refreshCaptcha')"
            />
          </el-form-item>
        </div>
        <div class="checkoutLoginType">
          <el-link :underline="false" type="primary" @click="$emit('checkoutLoginType','sms')">短信验证登录</el-link>
        </div>

        <div class="btn">
          <el-form-item class="login_submit">
            <el-button
              type="primary"
              @click="$emit('passwordLogin','passwordLoginForm')"
              class="login_submit"
              style="width:330px"
              >登录</el-button
            >
          </el-form-item>
        </div>
      </el-form>
  </div>
</template>

<script>

export default {
    name: 'PasswordLoginForm',
    props:{
        passwordLoginForm: Object,
        passwordLoginRules: Object,
        imageBase64: String
    }
}
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

.list {
  display: flex;
  align-items: center;
  padding: 2px 0;
}
.captchaList{
  display: flex;
  align-items: center;
  padding: 2px 0;
  height: 30px;
  margin-top: 20px;
}

.checkoutLoginType{
  display: flex;
  align-items: center;
  padding: 1px 0;
}

.list input {
  border-radius: 3px;
  border: none;
  outline: none;
  color: #999;
  border: 1px solid #bdbdbd;
  font-size: 14px;
  line-height: 35px;
  padding: 0 10px;
  display: block;
  box-sizing: border-box;
  flex: 7;
}

.captchaList .getCode span {
  font-size: 20px;
  background: #f5f7fa;
  color: #777;
  border-radius: 4px;
  line-height: 38px;
  border: 1px solid #ccc;
  display: inline-block;
  margin-left: 10px;
  width: 80px;
  text-align: center;
  user-select: none;
  cursor: pointer;
}

.btn {
  display: flex;
  justify-content: flex-end;
  padding-top: 5px;
}
.btn button {
  font-size: 13px;
  color: #fff;
  background: #46b5ff;
  outline: none;
  border: none;
  line-height: 35px;
  padding: 0 20px;
  display: inline-block;
  flex: 1;
  cursor: pointer;
}

</style>