<template>
  <div>
    <el-table
      :data="receiptData"
      height="630"
      style="width: 100%; font-size: 12px"
      v-loading="loading"
      element-loading-text="拼命加载中"
    >
      <el-table-column label="票据ID" prop="receiptId"></el-table-column>
      <el-table-column label="发行方" prop="companyIdFrom"></el-table-column>
      <el-table-column label="接收方" prop="IdTo"></el-table-column>
      <el-table-column label="金额" prop="amount"></el-table-column>
      <el-table-column label="到期日" prop="maturityDate"></el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      receiptData: [],
      loading: true,
    };
  },
  created() {
    this.getData();
  },
  methods: {
    async getData() {
      const { data: res } = await this.$http.get("getReceiptsInfo"); //访问后台

      if (res.code == 200) {
        for (let n in res.data) {
          let origin = res.data[n];
          origin.IdTo =
            origin.companyId == "null" ? origin.bankIdTo : origin.companyIdTo;
          this.receiptData.push(origin);
          this.receiptData[n].maturityDate = this.timeFormat(
            res.data[n].maturityDate
          );
        }
        this.loading = false;
      }
    },
    timeFormat(timeStamp) {
      let date = new Date(timeStamp);
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let dd = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hh = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let mm =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let ss =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      return year + "." + month + "." + dd + " " + hh + ":" + mm + ":" + ss;
    },
  },
  components: {},
};
</script>

<style>
</style>
