<template>
  <el-form
    :model="ruleForm"
    :rules="rules"
    ref="ruleForm"
    label-width="100px"
    class="demo-ruleForm"
    :disabled="judge"
  >
    <h1
      class="modal-title"
      id="ModalLabel"
      style="alignment: center; width: 100%; margin-left: 7%"
    >
      申请认证
    </h1>

    <el-form-item label="公司ID" prop="id">
      <el-input v-model="ruleForm.id" :disabled="true"></el-input>
    </el-form-item>

    <el-form-item label="证明材料" prop="info">
      <el-input
        type="text"
        v-model="ruleForm.info"
        autocomplete="off"
      ></el-input>
    </el-form-item>

    <el-form-item>
      <el-button @click="resetForm()" style="width: 100px; margin-left: -10%"
        >重置</el-button
      >
      <el-button
        type="primary"
        @click="submitForm('ruleForm')"
        style="width: 100px; margin-left: 15%"
        >提交审核</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        id: "",
        info: "",
        verify: "",
      },
      judge: false,
      rules: {
        name: [
          { required: true, message: "请输入用户真实名称", trigger: "change" },
        ],

        proof: [
          { required: false, message: "上传证明材料", trigger: "change" },
        ],
      },
    };
  },
  created() {
    this.ruleForm.id = window.sessionStorage.getItem("id");
    if (!window.sessionStorage.getItem("verify")) {
      this.judge = true;
      this.getInfo();
    }
  },
  methods: {
    async submitForm() {
      //需向后台传递数据
      const { data: res } = await this.$http.post(
        "uploadVerifyInfo",
        this.ruleForm
      );
      if (res.code == 200) {
        //转到首页
        // this.goQueryBalance();
        return this.$message.success(res.msg);
      } else {
        return this.$message.error(res.msg);
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    async getInfo() {
      const { data: res } = await this.$http.get("getInfo/" + this.ruleForm.id);
      if (res.code == 200) {
        this.ruleForm.info = res.data.info;
      }
    },
  },
};
</script>


<style lang='less' scoped>
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