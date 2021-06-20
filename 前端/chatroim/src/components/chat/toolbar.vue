<template>
  <div id="toolbar">
    <el-image
      class="imgProfile"
      :src="user.userProfile"
      :preview-src-list="[user.userProfile]"
      :alt="user.nickname"
    >
      <div slot="error" class="image-slot">
        <i class="el-icon-picture-outline"></i>
      </div>
    </el-image>
    <div id="btnBar">
      <div class="topBtnBar">
        <el-tooltip
          class="item"
          effect="dark"
          content="进入群聊"
          placement="right"
        >
          <el-button
            @click="chooseChatList('群聊')"
            class="toolBtn"
            size="small"
            ><i class="fa fa-comments fa-2x" aria-hidden="true"></i
          ></el-button>
        </el-tooltip>
        <el-tooltip
          class="item"
          effect="dark"
          content="用户列表"
          placement="right"
        >
          <el-button
            @click="chooseChatList('私聊')"
            class="toolBtn"
            size="small"
            ><i class="fa fa-address-book-o fa-2x" aria-hidden="true"></i
          ></el-button>
        </el-tooltip>
        <el-tooltip
          class="item"
          effect="dark"
          content="与机器人聊天"
          placement="right"
        >
          <el-button
            @click="chooseChatList('机器人')"
            class="toolBtn"
            size="small"
            ><i class="fa fa-android fa-2x" aria-hidden="true"></i
          ></el-button>
        </el-tooltip>
      </div>
      <div class="bottomBtnBar">
        <el-tooltip
          class="item"
          effect="dark"
          content="个人中心"
          placement="right"
        >
          <el-button @click="showUserInfoDialog" class="toolBtn" size="small"
            ><i class="fa fa-user fa-2x" aria-hidden="true"></i
          ></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="更多" placement="right">
          <el-popover
            placement="right"
            width="180"
            trigger="click"
            popper-class="moreListPopoverClass"
          >
            <ul id="moreList">
              <li @click="showFeedbackDialog">意见反馈</li>
              <li @click="clearChatHistory">清空聊天记录</li>
            </ul>
            <el-button slot="reference" class="toolBtn" size="small"
              ><i class="fa fa-bars fa-2x" aria-hidden="true"></i
            ></el-button>
          </el-popover>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="退出" placement="right">
          <el-button @click="exitSystem" class="toolBtn" size="small"
            ><i class="fa fa-sign-out fa-2x" aria-hidden="true"></i
          ></el-button>
        </el-tooltip>
      </div>
    </div>
    <el-dialog
      title="意见反馈"
      :visible.sync="feedBackDialogVisible"
      class="feedbackDialog"
    >
      <textarea class="feedbackInput" v-model="feedBackContent"></textarea>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleFeedbackSend">确 定</el-button>
        <el-button @click="feedBackDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="用户信息"
      :visible.sync="userInfoDialog"
      class="userInfoDialog"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="用户昵称">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            class="el-upload avatar-uploader"
            action="/file"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="form.user_profile" :src="form.user_profile" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyUserInfo">确 定</el-button>
        <el-button @click="userInfoDialog = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getRequest } from "../../utils/api";

export default {
  name: "toolbar",
  data() {
    const user = JSON.parse(window.sessionStorage.getItem("user"));
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
      feedBackDialogVisible: false,
      userInfoDialog: false,
      feedBackContent: "",
      form: {
        nickname: user.nickname,
        user_profile: user.userProfile
      }
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log(res);
      this.form.user_profile = res;
    },
    beforeAvatarUpload(file) {
      const isJPG =
        ["image/jpeg", "image/jpg", "image/png"].indexOf(file.type) > -1;
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG、JPEG、PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    async modifyUserInfo(){
      const res = await getRequest('/chat/update', this.form)
      console.log('res', res);
    },
    //退出系统
    exitSystem() {
      this.$confirm("你是否要退出系统吗?", "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.getRequest("/logout");
          sessionStorage.removeItem("user");
          //清除SessionStorage中保存的state
          if (sessionStorage.getItem("state")) {
            sessionStorage.removeItem("state");
          }
          //关闭连接
          this.$store.dispatch("disconnect");
          this.$router.replace("/");
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消操作"
          });
        });
    },
    //选择聊天列表
    chooseChatList(listName) {
      this.$store.commit("changeCurrentList", listName);
    },
    //打开意见反馈对话框
    showFeedbackDialog() {
      this.feedBackContent = "";
      this.feedBackDialogVisible = true;
    },
    showUserInfoDialog() {
      this.userInfoDialog = true;
    },
    //处理反馈消息邮件发送
    handleFeedbackSend() {
      let msgObj = {};
      msgObj.userId = this.user.id;
      msgObj.nickname = this.user.nickname;
      msgObj.username = this.user.username;
      msgObj.content = this.feedBackContent;
      console.log(msgObj);
      this.postRequest("/mail/feedback", msgObj).then(resp => {
        if (resp) {
          this.feedBackDialogVisible = false;
        }
      });
    },
    //清空聊天记录
    clearChatHistory() {
      this.$confirm(
        "此操作将永久删除本地聊天记录(群聊记录会在下次登录时恢复), 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          //清除本地的localStorage中的聊天记录
          if (localStorage.getItem("chat-session")) {
            localStorage.removeItem("chat-session");
          }
          //清除Vuex中保存的记录
          this.$store.state.sessionStorage = {};
          //清除SessionStorage中保存的state
          if (sessionStorage.getItem("state")) {
            sessionStorage.removeItem("state");
          }
          this.$message({
            type: "success",
            message: "删除成功"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  }
};
</script>

<style lang="scss" scoped>
#toolbar {
  width: 100%;
  height: 100%;
  #btnBar {
    width: 100%;
    height: 82%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .imgProfile {
    width: 40px;
    height: 40px;
    horiz-align: center;
    margin: 25px 10px;
  }
  .toolBtn {
    background-color: #2e3238;
    border: 0;
    margin: 5px 5px;
  }
  .feedbackDialog {
    width: 1000px;
    height: 800px;
    margin: 10px auto;
    //background-color: #ECEAE8;
  }
  .feedbackInput {
    width: 450px;
    height: 200px;
    resize: none;
    padding: 0;
    margin: 0;
  }
}

#moreList {
  margin: 0px;
  padding: 0px;
  background-color: #2e3238;
  li {
    padding-top: 14px;
    padding-bottom: 14px;
    padding-left: 5px;
    //padding-right: 40px;
    //border-bottom: 1px solid #292C33;
    list-style: none;
    cursor: pointer;
    &:hover {
      background-color: #abaaaa;
    }
  }
}
.el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
<style lang="scss">
/* el-popover是和app同级的，所以scoped的局部属性设置了无效 */
/* 需要设置全局style */
.el-popover.moreListPopoverClass {
  height: 150px;
  width: 150px;
  // margin: 0px;
  margin-left: 10px;
  padding: 0px;
  overflow-x: hidden;
  overflow-y: hidden;
  background-color: #2e3238;
  border: none;
}
</style>
