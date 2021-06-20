<template>
  <div class="container">
    <el-row>
      <el-col :span="24"> <p>发布朋友圈</p> </el-col>
    </el-row>
    <el-row class="action">
      <el-col :span="21">
        <el-input
          type="textarea"
          :autosize="{ minRows: 4, maxRows: 4 }"
          placeholder="请输入内容"
          v-model="value"
        >
        </el-input>
      </el-col>
      <el-col :span="3">
        <el-button @click="onSendPyq" type="primary" class="btn"
          >发布</el-button
        >
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getRequest } from "../utils/api";
import moment from "moment";
export default {
  name: "sendPyq",
  data() {
    return {
      value: "",
    };
  },
  async mounted() {
    const res = await getRequest("/chat/getrequest");
    console.log(res);
  },
  created() {},
  methods: {
    async onSendPyq() {
      const res = await getRequest("/chat/putdynamics", {
        content: this.value,
        create_time: moment().format("YYYY-MM-DD HH:mm:ss"),
      });
      console.log(res);
      this.value = "";
    },
  },
  components: {},
};
</script>

<style lang="scss" scoped>
.container {
  .action {
    display: flex;
    align-items: center;
  }
  .btn {
    margin-left: 10px;
  }
}
</style>
