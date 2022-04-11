<template>
  <el-form
    :model="receiptForm"
    :rules="rules"
    ref="receiptForm"
    label-width="100px"
    class="demo-ruleForm"
  >
    <h1
      class="modal-title"
      id="ModalLabel"
      style="alignment: center; width: 100%; margin-left: 7%"
    >
      票据发行
    </h1>

    <el-form-item label="来源公司ID" prop="companyIdfrom" :span="10">
      <el-input v-model="receiptForm.companyIdFrom" :disabled="true"></el-input>
    </el-form-item>

    <el-form-item label="流向公司ID" prop="companyIdTo">
      <el-input v-model="receiptForm.companyIdTo"></el-input>
    </el-form-item>

    <el-form-item label="票据数额" prop="amount">
      <el-input v-model="receiptForm.amount" oninput ="value=value.replace(/[^\d]/g,'')"></el-input>
    </el-form-item>

    <el-form-item label="到期日期" prop="maturityDate">
      <el-col :span="11" style="cursor: pointer">
        <el-date-picker
          type="date"
          placeholder="选择日期"
          v-model="receiptForm.maturityDate"
          style="width: 218%"
        ></el-date-picker>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-button @click="resetForm()" style="width: 100px; margin-left: -10%"
        >重置</el-button
      >
      <el-button
        type="primary"
        @click="submitForm()"
        style="width: 100px; margin-left: 15%"
        >确认</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      receiptForm: {
        receiptId: "",
        companyIdFrom: "",
        companyIdTo: "",
        amount: "",
        maturityDate: "",
      },
      rules: {
        companyIdTo: [
          { required: true, message: "流向公司ID为必填项", trigger: "blur" }, //必填项验证
        ],
        amount: [
          { required: true, message: "票据数额为必填项", trigger: "blur" }, //必填项验证
        ],
        maturityDate: [
          { required: true, message: "到期日期为必填项", trigger: "blur" }, //必填项验证
        ],
      },
    };
  },
  methods: {
    async submitForm() {
      this.$refs.receiptForm.validate(async (valid) => {
        if (!valid) return; //验证失败
        const { data: res } = await this.$http.post(
          "/invokeContract/addReceipt",
          this.receiptForm
        );
        if (res.code == 200) {
          this.$message.success(res.msg); //信息提示
          this.resetForm();
        } else {
          this.$message.console.error(res.msg); //信息提示
        }
      });
    },
    resetForm() {
      this.receiptForm.companyIdTo = "";
      this.receiptForm.amount = "";
      this.receiptForm.maturityDate = "";
      this.$refs.receiptForm.resetFields();
    },
  },
  created() {
    if (window.sessionStorage.getItem("verify") == "4") {
      this.$router.push("/RealNameRegister4");
    }
    this.receiptForm.companyIdFrom = window.sessionStorage.getItem("id");
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
  top: 37%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>