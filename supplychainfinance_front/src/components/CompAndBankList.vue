<template>
  <div>
    <el-tabs :stretch="true">
      <el-tab-pane label="公司">
        <el-table
          :data="companyList"
          height="590"
          cell-style="background:#F5F5F5"
          style="width: 100%; font-size: 12px"
          v-loading="loading"
          element-loading-text="拼命加载中"
        >
          <el-table-column label="公司ID" prop="companyId"></el-table-column>
          <el-table-column
            label="公司名称"
            prop="companyName"
          ></el-table-column>
          <el-table-column label="是否核心企业" prop="core"></el-table-column>
          <el-table-column label="票据" prop="receiptId" type="expand">
            <template slot-scope="scope1">
              <el-table :data="scope1.row.receiptId">
                <el-table-column label="票据" prop="id"></el-table-column>
                <el-table-column label="type" prop="type"></el-table-column>
              </el-table>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 银行列表 -->
      <el-tab-pane label="银行">
        <el-table
          :data="bankList"
          height="590"
          cell-style="background:#F5F5F5"
          style="width: 100%; font-size: 12px"
          v-loading="loading"
          element-loading-text="拼命加载中"
        >
          <el-table-column label="银行ID" prop="bankId"></el-table-column>
          <el-table-column label="银行名称" prop="bankName"></el-table-column>
          <el-table-column
            label="拥有票据"
            prop="receiptId_Asto"
            type="expand"
            width="80"
          >
            <template slot-scope="scope">
              <el-table :data="scope.row.receiptId_Asto">
                <el-table-column
                  label="拥有票据 Id"
                  prop="id"
                ></el-table-column>
              </el-table>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  data() {
    return {
      companyList: [],

      bankList: [],

      loading: "",
    };
  },

  created() {
    this.getBankData();
    this.getCompData();
  },

  methods: {
    async getBankData() {
      this.loading = true;
      const { data: res } = await this.$http.get("getBanksInfo"); //访问后台
      if (res.code == 200) {
        for (let n in res.data) {
          let receiptId_AstoList = res.data[n].receiptId_Asto;
          let receipt = [];
          for (let i in receiptId_AstoList) {
            let receipt_n = {};
            receipt_n.id = receiptId_AstoList[i];
            receipt.push(receipt_n);
          }

          //填充展示列表数据
          let bank_n = {};
          bank_n.bankId = res.data[n].bankId;
          bank_n.bankName = res.data[n].bankName;
          bank_n.receiptId_Asto = receipt;
          this.bankList.push(bank_n);
        }
        this.loading = false;
      }
    },

    async getCompData() {
      this.loading = true;
      const { data: res } = await this.$http.get("getCompaniesInfo"); //访问后台
      if (res.code == 200) {
        console.log(res.data);
        for (let n in res.data) {
          let receiptId_AstoList = res.data[n].receiptId_Asto;
          let receiptId_AsFromList = res.data[n].receiptId_AsFrom;
          let receipt = [];

          for (let i in receiptId_AsFromList) {
            let receipt_n = {};
            receipt_n.id = receiptId_AsFromList[i];
            receipt_n.type = "from";
            receipt.push(receipt_n);
          }
          for (let i in receiptId_AstoList) {
            let receipt_n = {};
            receipt_n.id = receiptId_AstoList[i];
            receipt_n.type = "to";
            receipt.push(receipt_n);
          }

          // console.log(receipt)
          let comp_n = {};
          comp_n.companyId = res.data[n].companyId;
          comp_n.companyName = res.data[n].companyName;
          comp_n.core = res.data[n].core;
          comp_n.receiptId = receipt;
          this.companyList.push(comp_n);
        }
        this.loading = false;
      }
    },
  },
  components: {},
};
</script>

<style>
</style>
