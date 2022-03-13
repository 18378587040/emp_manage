<template>
  <el-card class="main-card">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <!-- 修改信息 -->
      <el-tab-pane label="修改信息" name="info">
        <div class="info-container">
          <el-upload
            class="avatar-uploader"
            :headers="headers"
            :data="user"
            action="/api/upload"
            :show-file-list="false"
            :on-success="updateAvatar"
          >
            <img v-if="avatar" :src="avatar" class="avatar" style="border-radius: 100%" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-form
            label-width="70px"
            :model="infoForm"
            style="width:320px;margin-left:3rem"
          >
            <el-form-item label="用户名">
              <el-input v-model="infoForm.name" size="small" />
            </el-form-item>
            <el-form-item label="手机号码">
              <el-input v-model="infoForm.phone" size="small" />
            </el-form-item>
            <el-form-item label="个人邮箱">
              <el-input v-model="infoForm.email" size="small" />
            </el-form-item>
            <el-form-item label="居住地址">
              <el-input v-model="infoForm.address" size="small" />
            </el-form-item>
            <el-button
              @click="updateInfo"
              type="primary"
              size="medium"
              style="margin-left:4.375rem"
            >
              修改
            </el-button>
          </el-form>
        </div>
      </el-tab-pane>
      <!-- 修改密码 -->
      <el-tab-pane label="修改密码" name="password">
        <el-form label-width="70px" :model="passwordForm" style="width:320px">
          <el-form-item label="旧密码">
            <el-input
              @keyup.enter.native="updatePassword"
              v-model="passwordForm.oldPass"
              size="small"
              show-password
            />
          </el-form-item>
          <el-form-item label="新密码">
            <el-input
              @keyup.enter.native="updatePassword"
              v-model="passwordForm.pass"
              size="small"
              show-password
            />
          </el-form-item>
          <el-form-item label="确认密码">
            <el-input
              @keyup.enter.native="updatePassword"
              v-model="passwordForm.checkPass"
              size="small"
              show-password
            />
          </el-form-item>
          <el-button
            type="primary"
            size="medium"
            style="margin-left:4.4rem"
            @click="updatePassword"
          >
            修改
          </el-button>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script>
import {putRequest} from "../../utils/api";

export default {
  data: function() {
    return {
      headers: {
        Authorization: window.sessionStorage.getItem('tokenStr')
      },
      user:this.$store.state.admins,
      infoForm: {
        id: this.$store.state.userId,
        name: this.$store.state.name,
        phone: this.$store.state.phone,
        email: this.$store.state.email,
        address: this.$store.state.address
      },
      passwordForm: {
        userId: "",
        oldPass: "",
        pass: "",
        checkPass: ""
      },
      activeName: "info"
    };
  },
  methods: {
    handleClick(tab) {
      if (tab.index == 2 && this.notice == "") {
        this.axios.get("/api/admin/notice").then(({ data }) => {
          this.notice = data.data;
        });
      }
    },
    updateAvatar(response) {
      if (response) {
        this.$message.success(response.message);
        this.$store.commit("updateAvatar", response.obj);
      } else {
        this.$message.error(response.message);
      }
    },
    updateInfo() {
      if (this.infoForm.name.trim() == "") {
        this.$message.error("用户名不能为空");
        return false;
      }
      putRequest("/api/user/info", this.infoForm).then(resp => {
        if (resp) {
          this.$store.commit("updateUserInfo", this.infoForm);
        }
      });
    },
    updatePassword() {
      if (this.passwordForm.oldPass.trim() == "") {
        this.$message.error("旧密码不能为空");
        return false;
      }
      if (this.passwordForm.pass.trim() == "") {
        this.$message.error("新密码不能为空");
        return false;
      }
      if (this.passwordForm.pass.length < 6) {
        this.$message.error("新密码不能少于6位");
        return false;
      }
      if (this.passwordForm.pass != this.passwordForm.checkPass) {
        this.$message.error("两次密码输入不一致");
        return false;
      }
      this.passwordForm.userId = this.$store.state.userId;
      putRequest("/api/user/pass", this.passwordForm).then(resp => {
          if (resp) {
            // 更新密码成功后退出登录
            this.postRequest('/api/logout');
            window.sessionStorage.removeItem('user');
            window.sessionStorage.removeItem('tokenStr');
            this.$router.replace('/login');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    updateNotice() {
      if (this.notice.trim() == "") {
        this.$message.error("公告不能为空");
        return false;
      }
      let param = new URLSearchParams();
      param.append("notice", this.notice);
      this.axios.put("/api/admin/notice", param).then(({ data }) => {
        if (data.flag) {
          this.$message.success(data.message);
        } else {
          this.$message.error(data.message);
        }
      });
    }
  },
  computed: {
    avatar() {
      return this.$store.state.avatar;
    }
  }
};
</script>

<style scoped>
.avatar-container {
  text-align: center;
}
.el-icon-message-solid {
  color: #f56c6c;
  margin-right: 0.3rem;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
.info-container {
  display: flex;
  align-items: center;
  margin-left: 20%;
  margin-top: 5rem;
}
</style>
