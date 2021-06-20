<template>
  <div class="container">
    <el-tabs
      type="border-card"
      v-model="activeName"
      class="tabs"
      @tab-click="tabClick"
    >
      <el-tab-pane name="all" label="全部">
        <el-collapse>
          <el-collapse-item
            v-for="(item, index) in list"
            :key="index"
            :title="`${item.nickname}-${item.create_time}`"
            :name="index"
          >
            <div>
              {{ item.content }}
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
      <el-tab-pane name="main" label="我的">
        <el-collapse>
          <el-collapse-item
            v-for="(item, index) in myList"
            :key="index"
            :title="`${item.nickname}-${item.create_time}`"
            :name="index"
          >
            <div>
              {{ item.content }}
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getRequest } from "../utils/api";
export default {
  name: "pyq",
  data() {
    return {
      list: [],
      myList: [],
      activeName: "all"
    };
  },
  async mounted() {
    const res = await getRequest("/chat/dynamics");
    this.list = res;
  },
  methods: {
    async tabClick(v) {
      if (v.name === "all") {
        const res = await getRequest("/chat/dynamics");
        this.list = res;
      } else {
        const res = await getRequest("/chat/selfdynamics");
        this.myList = res;
      }
    }
  },
  created() {},
  components: {}
};
</script>

<style lang="scss" scoped>
.container {
  .tabs {
    overflow: auto;
    height: 700px;
  }
}
</style>
