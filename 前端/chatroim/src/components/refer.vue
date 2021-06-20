<template>
  <div class="container">
    <div class="title">
      <span>推荐好友</span>
      <el-button icon="el-icon-refresh-right" circle></el-button>
    </div>
    <ul class="list">
      <li v-for="item in list" :key="item.id" class="item">
        <span class="info">
          <el-avatar
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          ></el-avatar>
          {{ item.nickname }}
        </span>
        <span>
          <el-button
            type="success"
            @click="ok(item.username)"
            icon="el-icon-check"
            circle
          ></el-button>
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
import { getRequest } from "../utils/api";
export default {
  name: "refer",
  data() {
    return {
      list: []
    };
  },
  async mounted() {
    const res = await getRequest("/chat/recommand");
    if (Array.isArray(res)) {
      this.list = res;
    } else {
      this.list = [];
    }
  },
  created() {},
  methods: {
    async ok(username) {
      const res = await getRequest("/chat/searchuser", {
        username
      });
      if (res.code === 0) {
        this.$message.success("发送好友请求成功");
      } else {
        this.$message.error("发送好友请求失败");
      }
    },
    async reload() {
      const res = await getRequest("/chat/recommand");
      if (Array.isArray(res)) {
        this.list = res;
      } else {
        this.list = [];
      }
    }
  },
  components: {}
};
</script>

<style lang="scss" scoped>
.container {
  .title {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .list {
    height: 300px;
    list-style-type: none;
    padding: 0;
    overflow: auto;
  }
  .item {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 4px 0;
    .info {
      display: flex;
      align-items: center;
    }
  }
}
</style>
