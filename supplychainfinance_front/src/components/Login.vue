<template>
  <div id="login">
    <div class="login_container">
      <!--登录块-->
      <div class="login_box">
        <div class="testdiv">
          <img src="../assets/images/logo.png" />
          <span>供应链金融</span>
        </div>

        <!--表单区域-->
        <el-form
          ref="loginFormRef"
          :rules="loginRules"
          :model="loginForm"
          class="login_form"
          label-width="0px"
        >
          <!--用户名-->
          <el-form-item prop="username" class="login_text">
            <el-input
              v-model="loginForm.username"
              placeholder="用户名"
              prefix-icon="iconfont icon-denglu"
              class="register_transp"
            ></el-input>
          </el-form-item>
          <!--密码-->
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              placeholder="密码"
              prefix-icon="iconfont icon-mima"
              type="password"
              class="register_transp"
            ></el-input>
          </el-form-item>
          <!-- 注册小链接 -->
          <div class="nRegister">
            <el-link :underline="false" @click="goRegister()"
              >新用户注册</el-link
            >
          </div>
          <!--按钮-->
          <el-form-item class="lButton">
            <el-button type="warning" @click="login()">登录</el-button>
            <!-- <el-button type="warning" @click="resetLoginForm()">重置</el-button>
            <el-button type="success" @click="goRegister()">注册</el-button> -->
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      //表单数据
      loginForm: {
        username: "",
        password: "",
      },
      //验证对象：校验规则
      loginRules: {
        username: [
          { required: true, message: "用户名称为必填项", trigger: "blur" }, //必填项验证
          {
            min: 1,
            max: 100,
            message: "长度在 1 到 100 个字符",
            trigger: "blur",
          }, //长度验证
        ],
        password: [
          { required: true, message: "用户密码为必填项", trigger: "blur" }, //必填项验证
          {
            min: 6,
            max: 10,
            message: "长度在 6 到 10 个字符",
            trigger: "blur",
          }, //长度验证
        ],
      },
    };
  },
  methods: {
    //异步加载数据，登录
    login() {
      this.$refs.loginFormRef.validate(async (valid) => {
        if (!valid) return; //验证失败
        const { data: res } = await this.$http.post("login", this.loginForm); //访问后台
        if (res.code == 200) {
          window.sessionStorage.setItem("id", res.data.id); //存储user对象
          window.sessionStorage.setItem("username", res.data.username); //存储user对象
          let role = res.data.role;
          if (role == 1) {
            this.$router.push({ path: "/home1" }); //页面跳转
          } else if (role == 2) {
            if (res.data.verify != "已通过") {
              this.$router.push({ path: "/RealNameRegister2" });
              window.sessionStorage.setItem("verify", "2");
              //页面跳转
            } else {
              this.$router.push("/home2");
            }
          } else if (role == 3) {
            if (res.data.verify != "已通过") {
              this.$router.push({ path: "/RealNameRegister3" });
              window.sessionStorage.setItem("verify", "3");
              //页面跳转
            } else {
              this.$router.push("/home3");
            }
          } else if (role == 4) {
            if (res.data.verify != "已通过") {
              this.$router.push({ path: "/RealNameRegister4" });
              window.sessionStorage.setItem("verify", "4");
              //页面跳转
            } else {
              this.$router.push("/home4");
            }
          }
          this.$message.success(res.msg); //信息提示
        } else {
          this.$message.error(res.msg); //错误提示
        }
      });
    },
    //重置表单内容
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },
    goRegister() {
      this.$router.push({ path: "/register" }); //页面跳转
    },
  },
};
</script>

<style lang='less' scoped>
.register_transp {
  /deep/.el-input__inner {
    //如果你的style 加了scoped 需要加/deep/
    background: transparent;
    color: #cececf;
  }
}

// 使图文同一行出现
a img {
  border: none;
}

.testdiv * {
  height: 125px;
  vertical-align: middle;
  font-family: "SimHei";
  font-size: 40px;
}

.login_text {
  background-color: transparent;
}

//根节点样式
.login_container {
  width: 100vw;
  padding: 0;
  margin: 0;
  height: 100vh;
  font-size: 16px;
  background-repeat: no-repeat;
  background-position: left top;
  background-color: #242645;
  color: #fff;
  font-family: "Source Sans Pro";
  background-size: cover;
  background-image: url("../assets/images/background.png");
  position: relative;
}
.login_box {
  width: 100vw;
  height: 330px;
  background-color: #6062667d;
  border-radius: 3px;
  opacity: 0.8;
  position: absolute;
  // background-color:transparent;
  top: 48%;
  left: 50%;
  transform: translate(-50%, -50%);
  .avatar_box {
    width: 150px;
    height: 150px;
    // border: 1px solid #eee;
    border-radius: 50%;
    padding: 5px;
    // box-shadow: 0 0 5px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    // background-color: #eee;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      // background-color: #eee;
    }
  }
}
.login_form {
  position: absolute;
  bottom: 0;
  width: 20%; /*这里宽度100%太宽了，所以设置成20%，为了居中，需要在向右移动40.5%的位置*/
  top: 40%;
  left: 40.5%;
  padding: 0 10px;
  box-sizing: border-box;
}
.nRegister /deep/.el-link {
  color: rgba(240, 248, 255, 0.87);
  align-items: right;
  text-align: right;
  margin-left: 70%;
  margin-top: -20px;
}
.lButton /deep/.el-button {
  padding: 12px 128px;
}
</style>