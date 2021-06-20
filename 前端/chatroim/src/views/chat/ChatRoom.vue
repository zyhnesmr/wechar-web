<template>
  <div id="app">
    <div class="chat-container">
      <div class="toolbar">
        <toolbar></toolbar>
      </div>
      <div class="sidebar">
        <card></card>
        <list></list>
      </div>
      <div class="main">
        <chattitle></chattitle>
        <message></message>
        <usertext></usertext>
      </div>
    </div>

    <div class="pyq">
      <pyq></pyq>
    </div>
    <div class="requestList">
      <requestList></requestList>
    </div>
    <div class="refer">
      <refer></refer>
    </div>
    <div class="sendPyq">
      <sendPyq></sendPyq>
    </div>
  </div>
</template>

<script>
import card from "../../components/chat/card";
import list from "../../components/chat/list.vue";
import message from "../../components/chat/message.vue";
import usertext from "../../components/chat/usertext.vue";
import toolbar from "../../components/chat/toolbar";
import chattitle from "../../components/chat/chattitle";
import pyq from "../../components/pyq";
import requestList from "../../components/requestList";
import refer from "../../components/refer";
import sendPyq from "../../components/sendPyq";

export default {
  name: "ChatRoom",
  data() {
    return {};
  },
  mounted: function() {
    //初始化数据
    this.$store.dispatch("initData");
    //连接WebSocket服务
    this.$store.dispatch("connect");
  },
  created() {
    // //在页面加载时读取sessionStorage里的状态信息
    // if (sessionStorage.getItem("state") ) {
    //   this.$store.replaceState(Object.assign({}, this.$store.state,JSON.parse(sessionStorage.getItem("store"))))
    // }

    //在页面刷新时将vuex里的最新信息保存到sessionStorage里
    window.addEventListener("beforeunload", () => {
      sessionStorage.setItem("state", JSON.stringify(this.$store.state));
    });
  },
  components: {
    toolbar,
    card,
    list,
    message,
    usertext,
    chattitle,
    pyq,
    requestList,
    refer,
    sendPyq
  }
};
</script>

<style lang="scss" scoped>
#app {
  width: 100%;
  height: 100%;
  overflow: hidden;
  border-radius: 10px;
  .chat-container {
    margin: 20px auto;
    width: 60%;
    height: 700px;
  }
  .sidebar,
  .main,
  .toolbar {
    height: 100%;
  }
  .toolbar {
    float: left;
    color: #f4f4f4;
    background-color: #2e3238;
    width: 60px;
  }
  .sidebar {
    float: left;
    color: #000000;
    background-color: #eceae8;
    width: 240px;
  }
  .main {
    position: relative;
    overflow: hidden;
    background-color: #eee;
  }
  .pyq {
    margin: 20px 10px;
    width: 18%;
    height: 690px;
    padding: 10px;
    left: 0;
    position: absolute;
    top: 0;
  }
  .requestList {
    margin: 20px 10px;
    width: 17%;
    height: 330px;
    padding: 10px;
    border-radius: 2px;
    background-color: rgba(0, 0, 0, 0.01);
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12);
    right: 0;
    position: absolute;
    top: 0;
  }
  .refer {
    margin: 20px 10px;
    width: 17%;
    height: 330px;
    padding: 10px;
    border-radius: 2px;
    background-color: rgba(0, 0, 0, 0.01);
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12);
    right: 0;
    position: absolute;
    top: 360px;
  }
  .sendPyq {
    margin: 20px 10px;
    width: calc(100vw - 40px);
    left: 0;
    height: 160px;
    padding: 10px;
    border-radius: 10px;
    background-color: rgba(0, 0, 0, 0.01);
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12);
    bottom: 0;
    position: absolute;
  }
}
</style>
