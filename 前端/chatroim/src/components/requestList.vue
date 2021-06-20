<template>
  <div class="container">
    <div>
      <div class="title">
        <span>好友请求列表</span>
        <el-button
          @click="reload"
          icon="el-icon-refresh-right"
          circle
        ></el-button>
      </div>
    </div>
    <ul class="list">
      <li v-for="item in list" :key="item.id" class="item">
        <span class="info">
          <el-avatar
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          ></el-avatar>
          {{ item.username }}
        </span>
        <span>
          <el-button
            type="success"
            icon="el-icon-check"
            circle
            @click="ok(item.username)"
          ></el-button>
          <el-button type="danger" icon="el-icon-close" circle></el-button>
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
import { getRequest } from "../utils/api";
export default {
  name: "requestList",
  data() {
    return {
      list: []
    };
  },
  async mounted() {
    const res = await getRequest("/chat/getrequest");
    if (Array.isArray(res)) {
      this.list = res;
    } else {
      this.list = [];
    }
  },
  methods: {
    async ok(username) {
      const res = await getRequest("/chat/accept", { from_id: username });
      console.log(res);
    },
    async reload() {
      const res = await getRequest("/chat/getrequest");
      if (Array.isArray(res)) {
        this.list = res;
      } else {
        this.list = [];
      }
    }
  },
  created() {},
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
    list-style-type: none;
    padding: 0;
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
