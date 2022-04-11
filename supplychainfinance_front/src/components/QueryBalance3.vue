<template>
  <el-card
    class="box-card"
    v-loading="loading"
    element-loading-text="拼命加载中..."
  >
    <div slot="header" class="clearfix">
      <span>公司信息</span>
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
        {{ "公司ID" }}
      </div>
      <div v-model="info.balance" class="item_desc">
        {{ info.companyId }}
      </div>
    </div>
    <div class="text item">
      <div v-model="info.balance" class="item_tag">
        {{ "公司名称" }}
      </div>
      <div v-model="info.balance" class="item_desc">
        {{ info.companyName }}
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      info: {
        // balance: "9999999",
        // companyId: "c8d6278cc98f4252a9924ea6ea6b4867",
        // companyName: "Flying real estate company",
        // isCore: "No",
        balance: "",
        companyId: "",
        companyName: "",
        isCore: "",
      },
      loading: true,
      // loading: false,
    };
  },
  methods: {
    async getData() {
      this.id = window.sessionStorage.getItem("id"); //自动获取id

      const { data: res } = await this.$http.get(
        "getCompanyBalance/" + this.id
      ); //访问后台
      if (res.code == 200) {
        console.log(res); //得到结果
        this.info.balance = res.data;
        this.info.companyId = window.sessionStorage.getItem("id");
        this.info.companyName = window.sessionStorage.getItem("username");
        // this.info.isCore = res.data.isCore;
      } else {
        this.info.balance = "加载失败";
        this.info.companyId = "加载失败";
        this.info.companyName = "加载失败";
      }
      this.loading = false;
    },
  },
  created() {
    if (window.sessionStorage.getItem("verify") == "3") {
      this.$router.push("/RealNameRegister3");
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