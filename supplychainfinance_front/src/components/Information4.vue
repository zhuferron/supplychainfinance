<template>
  <el-form
    :model="infoForm"
    :rules="rules"
    ref="infoFormRef"
    label-width="100px"
    class="demo-ruleForm"
  >
    <h1
      class="modal-title"
      id="ModalLabel"
      style="alignment: center; width: 100%; margin-left: 7%"
    >
      个人信息
    </h1>
    <!--ID -->
    <el-form-item label="ID" prop="name" :span="10">
      <el-input v-model="infoForm.id" :disabled="true"></el-input>
    </el-form-item>
    <!--用户名 -->
    <el-form-item label="用户名" prop="name">
      <el-input v-model="infoForm.username"></el-input>
    </el-form-item>
    <!--密码 -->
    <el-form-item label="密码" prop="name">
      <el-input v-model="infoForm.password" show-password></el-input>
    </el-form-item>
    <!--email -->
    <el-form-item label="email" prop="name">
      <el-input v-model="infoForm.email"></el-input>
    </el-form-item>
    <!--是否实名认证 -->
    <el-form-item label="是否认证通过" prop="ifRealName">
      <div>
        <div style="float: left">{{ infoForm.isRealName }}</div>
        <div style="float: right" @click="goRealNameR">
          <el-link :underline="false">申请认证</el-link>
        </div>
      </div>
    </el-form-item>
    <el-form-item>
      <el-button @click="resetForm()" style="width: 100px; margin-left: -10%"
        >重置</el-button
      >
      <el-button
        type="primary"
        @click="updateInfo()"
        style="width: 100px; margin-left: 15%"
        >修改</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      //表单信息
      infoForm: {
        id: "",
        username: "",
        password: "",
        email: "",
        isRealName: "No",
      },
      //表单校验规则
      rules: {
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
        email: [
          { required: true, message: "邮箱地址为必填项", trigger: "blur" }, //必填项验证
          { max: 30, message: "长度不能超过 30 个字符", trigger: "blur" }, //长度验证
        ],
      },
      disabled: true,
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    goRealNameR() {
      this.$router.push("/RealNameRegister4");
    },
    async getInfo() {
      this.infoForm.id = window.sessionStorage.getItem("id");
      const { data: res } = await this.$http.get("getInfo/" + this.infoForm.id);
      if (res.code == 200) {
        //填充数据
        //this.infoForm.id = res.user.id;
        this.infoForm.username = res.data.username;
        this.infoForm.password = res.data.password;
        this.infoForm.email = res.data.email;
        this.infoForm.isRealName = res.data.verify;
        return this.$message.success(res.msg);
      } else {
        return this.$message.error(res.msg);
      }
    },
    updateInfo() {
      this.$refs.infoFormRef.validate(async (valid) => {
        if (!valid) return; //验证失败
        const { data: res } = await this.$http.put("updateInfo", this.infoForm); //访问后台
        if (res.code == 200) {
          return this.$message.success(res.msg);
        } else {
          return this.$message.error(res.msg);
        }
      });
    },
    resetForm() {
      this.infoForm.username = "";
      this.infoForm.password = "";
      this.infoForm.email = "";
      this.$refs.receiptForm.resetFields();
    },
  },
};
</script>

<style lang="less" scoped>
.demo-ruleForm {
  width: 450px;
  align-items: center;
  height: 300px;
  background-color: #3399cc;
  border-radius: 3px;
  opacity: 0.8;
  position: absolute;
  background-color: transparent;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>