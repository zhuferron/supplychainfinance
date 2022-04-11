<template>

<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <h1 class="modal-title" id="ModalLabel" style="alignment: center; width: 100%;">区块链账户创建</h1>

    <!-- 合约地址 -->
    <el-form-item label="合约地址" prop="contractAddress" :span="10" >
      <el-input v-model="ruleForm.contractAddress" :disabled="disabled"></el-input>
    </el-form-item>

    <!-- 私钥地址-->
    <el-form-item label="私钥地址" prop="pwdAddress" :span="10" >
      <el-input v-model="ruleForm.pwdAddress" :disabled="disabled"></el-input>
    </el-form-item>

    <!--公司名称 -->
    <el-form-item label="公司名称" prop="companyName">
      <el-input v-model="ruleForm.companyName"></el-input>
    </el-form-item>

    <!-- 公司类型 -->
    <el-form-item label="公司类型" prop="companyType">
      <el-input v-model="ruleForm.companyType"></el-input>
    </el-form-item>

    <!-- 是否授信公司 -->
    <el-form-item label="是否授信" prop="isTrusted">
      <div style="text-align: left">True</div> 
    </el-form-item>

    <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
</el-form>

</template>

<script>
    export default {
        data() {
            return {
                ruleForm: {
                    contractAddress: '',
                    pwdAddress: '',
                    companyName: '',
                    isTrusted: '',
                },
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            addCompany() {
                // 写啥？
            }
            
        }
    }
</script>


<style lang='less' scoped>
    .demo-ruleForm {
        width: 450px;
        align-items: center;
        height: 300px;
        background-color: #3399CC;
        border-radius: 3px;
        opacity:0.8;
        position: absolute;
        background-color:transparent;
        top: 40%;
        left: 50%;
        transform: translate(-50%,-50%);
    }

</style>