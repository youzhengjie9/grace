<template>
  <div class="main">
    <div class="backImg"></div>
    <div class="login">
      <password-login-form ref="pwdForm"
       :passwordLoginForm="passwordLoginForm"
       :passwordLoginRules="passwordLoginRules"
       :imageBase64="imageBase64"
       @refreshCaptcha="refreshCaptcha"
       @passwordLogin="passwordLogin"
       @checkoutLoginType="checkoutLoginType"
       v-show="loginType === 'password'"></password-login-form>

       <sms-login-form ref="smsForm"
       :smsLoginForm="smsLoginForm"
       :smsLoginRules="smsLoginRules"
       :sendCaptchaButtonName="sendCaptchaButtonName"
       :sendCaptchaButtonDisabled="sendCaptchaButtonDisabled"
       @smsLogin="smsLogin"
       @checkoutLoginType="checkoutLoginType"
       @sendSmsCaptcha="sendSmsCaptcha"
       v-show="loginType === 'sms'"></sms-login-form>
      
    </div>
  </div>
</template>

<script>
//引入api中的方法
import { getImageCaptcha,passwordLogin,smsLogin,sendSmsCaptcha } from "@/api/login";
import{
  initDynamicRouter
}from '@/utils/permission'
import PasswordLoginForm from "@/components/login/PasswordLoginForm.vue";
import SmsLoginForm from "@/components/login/SmsLoginForm.vue";

export default {
  components:{
    PasswordLoginForm,
    SmsLoginForm
  },
  data() {
    return {
      //登录类型（默认展示的是密码登录表单）
      loginType: 'password',
      //密码登录表单数据
      passwordLoginForm: {
        username:'',
        password:'',
        codeKey:'', //存储在redis中的正确的验证码的key，通过这个key能找到正确的验证码
        code:'' //前端输入的验证码
      },
      //配置前端密码登录表单校验规则
      passwordLoginRules: {
        //配置username校验规则
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
        //配置password校验规则
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
        //配置验证码规则
        code: [
          {
            required: true, //必填项
            message: "请输入验证码",
            trigger: "blur",
          },
          {
            min: 5,
            max: 5,
            message: "验证码长度只能为5位",
            trigger: "blur",
          },
        ],
      },
      //短信登录表单数据
      smsLoginForm: {
        phone: '', //手机号
        smsCaptcha: '' //短信验证码
      },
      //配置前端短信登录表单校验规则
      smsLoginRules: {
        //配置手机号校验规则
        phone: [
          {
            required: true, 
            message: "请填写手机号码"
          },
          {
            min: 11,
            message: '请输入不少于11个字符',
            trigger: 'blur'
          }
        ],
        //配置短信验证码校验规则
        smsCaptcha: [
          {
            required: true, //必填项
            message: "请输入短信验证码",
            trigger: "blur",
          }
        ]
      },
      //验证码图片的base64编码数据
      imageBase64: "",
      // 发送验证码按钮
      sendCaptchaButtonName : '获取验证码', //按钮名称
      sendCaptchaButtonDisabled : false,  //按钮是否禁用
      sendCaptchaTime : 60,  //时间
      sendCaptchaSuccess : '',  //是否成功发送，根据这个条件来判断是否进入定时器　　
    };
  },
  mounted() {
    //一进入login页面自动刷新验证码
    this.refreshCaptcha();
  },
  methods: {
    //切换登录类型
    checkoutLoginType(type){
      this.loginType = type;
    },
    //密码登录逻辑
    passwordLogin(passwordLoginForm) {
      
      this.$refs['pwdForm'].$refs[passwordLoginForm].validate((valid) => {
        //如果前端校验通过，则进入这里
        if (valid) {
          const newFormData={
            username:this.passwordLoginForm.username,
            password:this.passwordLoginForm.password
          }
          let image_captcha_key = this.passwordLoginForm.codeKey; //存储在redis中的正确的验证码的key，通过这个key能找到正确的验证码
          let image_captcha = this.passwordLoginForm.code; //前端输入的验证码
          //调用userLogin的api方法
          passwordLogin(newFormData,image_captcha_key,image_captcha).then((res) => {
            // 拿到oauth2登录返回的数据（比如accessToken和refreshToken等等）
            let data=res.data;
            console.log(data)
            //用户登录成功
            if(res.status===200)
            {
                this.$store.dispatch('loginSuccess',data);
                this.$message({
                    showClose: true,
                    message: '登录成功',
                    type: 'success',
                    duration:1000
                });
                //登陆成功后就可以为这个用户生成动态路由（调用permission.js的初始化动态路由方法）
                initDynamicRouter();

                //登录成功后跳转到首页
                this.$router.push({
                  path:'/'
                })
            }
            else{
                this.$message({
                  showClose: true,
                  message: '登录失败,请检查输入的帐号、密码、验证码是否正确',
                  type: 'error',
                  duration:1000
                });
            }
            
        }).catch((err)=>{
          this.$message({
                  showClose: true,
                  message: '登录失败,请检查输入的帐号、密码、验证码是否正确',
                  type: 'error',
                  duration:1000
                });
        })
     
        } else {
          return false;
        }
      });
    },
    //调用刷新验证码api接口
    refreshCaptcha() {
      getImageCaptcha()
        .then((res) => {
          //把验证码的key存储到表单对象中，请求登录接口时方便通过携带这个key从redis中找到正确的验证码
          this.passwordLoginForm.codeKey=res.data.data.imageCaptchaKey;
          //存储验证码图片base64
          this.imageBase64 = res.data.data.imageCaptchaBase64;
        })
        .catch((err) => {});
    },

    //短信登录逻辑
    smsLogin(smsLoginForm) {

      this.$refs['smsForm'].$refs[smsLoginForm].validate((valid) => {
        //如果前端校验通过，则进入这里
        if (valid) {
          //调用userLogin的api方法
          smsLogin(this.smsLoginForm.phone,this.smsLoginForm.smsCaptcha).then((res) => {
            // 拿到oauth2登录返回的数据（比如accessToken和refreshToken等等）
            let data=res.data;
            //用户登录成功
            if(res.status===200)
            {
                this.$store.dispatch('loginSuccess',data);
                this.$message({
                    showClose: true,
                    message: '登录成功',
                    type: 'success',
                    duration:1000
                });
                //登陆成功后就可以为这个用户生成动态路由（调用permission.js的初始化动态路由方法）
                initDynamicRouter();

                //登录成功后跳转到首页
                this.$router.push({
                  path:'/'
                })
            }
            else{
                  this.$message({
                  showClose: true,
                  message: '登录失败,请检查输入的短信验证码是否正确',
                  type: 'error',
                  duration:1000
                });
            }
            
        }).catch((err)=>{
          this.$message({
                  showClose: true,
                  message: '登录失败,请检查输入的短信验证码是否正确',
                  type: 'error',
                  duration:1000
                });
        })
     
        } else {
          return false;
        }

      });
      
    },
    sendSmsCaptcha(){
      
      sendSmsCaptcha(this.smsLoginForm.phone).then(res=>{
        let data = res.data;
        // 发送验证码成功
        if(data.code === 1102){
                this.$message({
                    showClose: true,
                    message: data.msg,
                    type: 'success',
                    duration:1000
                });
                this.sendCaptchaSuccess = '发送成功'
                var countDown = setInterval(() => {

                    if (this.sendCaptchaTime < 1) {
                      this.sendCaptchaButtonDisabled = false
                      this.sendCaptchaButtonName = '获取验证码'
                      this.sendCaptchaTime = 60
                      clearInterval(countDown)
                    } else if(this.sendCaptchaTime >= 0 && this.sendCaptchaSuccess == '发送成功'){
                      this.sendCaptchaButtonDisabled = true
                      this.sendCaptchaButtonName = this.sendCaptchaTime-- + 's后重发'
                    }
                  }, 1000)
            }
            else{
               this.$message({
                    showClose: true,
                    message: data.msg,
                    type: 'error',
                    duration:1000
                });
                this.sendCaptchaSuccess = '发送失败'
            }
      }).catch((err)=>{
          console.log(err)
            this.$message({
              showClose: true,
              message: '系统异常,发送短信验证码失败',
              type: 'error',
              duration:1000
            });
        })

      
    },
  },
};
</script>

<style scoped>

.backImg {
  background: url("@/assets/login-page.jpg");
  background-size: 100% 100%;
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
}
.login {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid #ccc;
  background: #fff;
  width: 22%;
  padding: 20px 20px 25px 20px;
}

</style>