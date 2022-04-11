<template>
  <div id="register">
    <div class="register_container">
      <!--登录块-->
      <div class="register_box">
        <div class="testdiv">
          <img src="../assets/images/logo.png" />
          <span>供应链金融</span>
        </div>

        <!--表单区域-->
        <el-form
          ref="registerFormRef"
          :rules="registerRules"
          :model="registerForm"
          class="register_form"
          label-width="0px"
        >
          <el-form-item prop="role">
            <el-select
              v-model="registerForm.role"
              placeholder="请选择用户类型"
              class="usertype"
              style="width: 100%"
            >
              <!-- <el-option label="仲裁机构" value="1"></el-option> -->
              <el-option label="银行" value="2"></el-option>
              <el-option label="非核心企业" value="3"></el-option>
              <el-option label="核心企业" value="4"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item prop="username" class="register_transp">
            <el-input
              v-model="registerForm.username"
              placeholder="用户名"
              prefix-icon="iconfont icon-denglu"
              class="register_transp"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password" class="register_transp">
            <el-input
              v-model="registerForm.password"
              placeholder="密码"
              prefix-icon="iconfont icon-mima"
              type="password"
            ></el-input>
          </el-form-item>
          <el-form-item prop="repassword" class="register_transp">
            <el-input
              v-model="registerForm.repassword"
              placeholder="确定密码"
              prefix-icon="iconfont icon-jianchacheck35"
              type="password"
            ></el-input>
          </el-form-item>
          <el-form-item prop="email" class="register_transp">
            <el-input
              v-model="registerForm.email"
              placeholder="邮箱地址"
              prefix-icon="el-icon-message"
            ></el-input>
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="info" @click="goLogin()">返回</el-button>
            <el-button type="info" @click="resetLoginForm()">重置</el-button>
            <el-button type="info" @click="register()">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    //确定两次密码输入的相同
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.registerForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      //表单数据
      registerForm: {
        role: "",
        username: "",
        password: "",
        repassword: "",
        email: "",
      },
      //验证对象：校验规则
      registerRules: {
        role: [
          { required: true, message: "用户类型为必选项", trigger: "blur" }, //必填项验证
        ],
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
        repassword: [
          { required: true, validator: validatePass, trigger: "blur" },
        ],
        email: [
          { required: true, message: "邮箱地址为必填项", trigger: "blur" }, //必填项验证
          { max: 30, message: "长度不能超过 30 个字符", trigger: "blur" }, //长度验证
        ],
      },
    };
  },
  methods: {
    resetLoginForm() {
      this.$refs.registerFormRef.resetFields();
    },
    register() {
      this.$refs.registerFormRef.validate(async (valid) => {
        if (!valid) return; //验证失败
        const { data: res } = await this.$http.post(
          "/register",
          this.registerForm
        ); //访问后台
        if (res.code == 200) {
          this.$message.success(res.msg);
          this.$router.push({ path: "/login" });
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    goLogin() {
      this.$router.push({ path: "/login" });
    },
  },
};
</script>

<style lang="less" scoped>
.usertype {
  /deep/.el-input__inner {
    //如果你的style 加了scoped 需要加/deep/
    background: url("../assets/images/usertype.png") no-repeat; //引入icon
    background-size: 20px 20.8px; //这个是图片的大小，在这里不能直接设置width  height,设置宽高其实是select的宽高，图片可能会失真只设置宽度  高度auto也行
    background-position: 3.5px 8px; //在input中定位置  这两个参数是x  y坐标
    padding: 0 0 0 26px; //需要设置padding 把placeholder向右移
    box-sizing: border-box;
    font-size: 14px;
    color: #cececf;
  }
}

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
  top: 0%;
}

//根节点样式
.register_container {
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
.register_box {
  width: 100vw;
  height: 500px;
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
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 5px;
    box-shadow: 0 0 5px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #90ee90;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}
.btns /deep/.el-button {
  // display: flex;
  justify-content: center;
  padding: 12px 28px;
}
.register_form {
  position: absolute;
  bottom: 0;
  width: 20%;
  top: 25%;
  left: 40.5%;
  padding: 0 10px;
  box-sizing: border-box;
  background: transparent;
}
</style>