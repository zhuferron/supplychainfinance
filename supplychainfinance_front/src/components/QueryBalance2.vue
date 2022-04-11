<template>
  <el-card
    class="box-card"
    v-loading="loading"
    element-loading-text="拼命加载中..."
  >
    <div slot="header" class="clearfix">
      <span>银行信息</span>
    </div>
    <div class="text item">
      <div v-model="info.balance" class="item_tag">
        {{ "余额" }}
      </div>
      <div v-model="info.balance" class="item_desc">
        {{ info.balance }}
      </div>
    </div>
    <div class="text item">
      <div v-model="info.balance" class="item_tag">
        {{ "银行ID" }}
      </div>
      <div v-model="info.balance" class="item_desc">
        {{ info.bankId }}
      </div>
    </div>
    <div class="text item">
      <div v-model="info.balance" class="item_tag">
        {{ "银行名称" }}
      </div>
      <div v-model="info.balance" class="item_desc">
        {{ info.bankName }}
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      info: {
        // balance: "1000000",
        // bankId: "b2ddf14dca314e2d882bfb3aff990cdf",
        // bankName: "the Agricultural Bank of China",
        balance: "",
        bankId: "",
        bankName: "",
        // isCore: "No",
      },
      loading: true,
      // loading: false,
    };
  },
  methods: {
    async getData() {
      this.id = window.sessionStorage.getItem("id"); //自动获取id

      // this.$message.success(this.userId); //弹出提示框
      const { data: res } = await this.$http.get(
        "/getBankBalance/" + window.sessionStorage.getItem("id")
      ); //访问后台
      if (res.code == 200) {
        console.log(res.data);
        this.info.balance = res.data;
        this.info.bankId = window.sessionStorage.getItem("id");
        this.info.bankName = window.sessionStorage.getItem("username");
      } else {
        this.info.balance = "加载失败";
        this.info.bankId = "加载失败";
        this.info.bankName = "加载失败";
      }
      this.loading = false;
    },
  },
  created() {
    if (window.sessionStorage.getItem("verify") == "2") {
      this.$router.push("/RealNameRegister2");
    }
    this.getData();
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
//下面都是卡片的样式
.item {
  margin-bottom: 10px;
}
.item_tag {
  float: left;
  text-align: left;
}
.item_desr {
  margin-left: 40%;
  min-height: 30px;
  text-align: left;
}
.text {
  width: 100%;
  font-size: 16px;
  font-family: "Microsoft YaHei";
  color: #909399;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.box-card {
  width: 480px;
  margin-left: 33%;
}
</style>