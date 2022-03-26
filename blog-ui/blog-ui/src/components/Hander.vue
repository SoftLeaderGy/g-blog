<template>
  <div class="m-content">
    <h3>欢迎来到SoftLeaderGy的博客</h3>
    <div class="block">
      <div class="demo-basic--circle">
        <div class="block" @click="upload"><el-avatar :size="50" :src="user.avatar"></el-avatar></div>
      </div>
      <div>{{user.username}}</div>
    </div>
    <div class="maction">
      <span><el-link href="#/blogs">主页</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="#/blog/add">发表文章</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span v-if="hasInfo"><el-link  type="danger" @click="logout">退出</el-link></span>
      <span v-else><el-link  href="#/login" type="primary">登陆</el-link></span>
    </div>
      <div v-show="showUpload && hasInfo" style="width: 50px;height: 50px">
        <el-dialog
            title="上传头像"
            :visible.sync="showUpload"
            width="20%"
           :modal="showUpload && hasInfo"
            :before-close="handleClose">
          <form enctype="multipart/form-data">
            <el-upload
                class="avatar-uploader"
                action="https://jsonplaceholder.typicode.com/posts/"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <span slot="footer" class="dialog-footer">
              <el-button @click="showUpload = false">取 消</el-button>
              <el-button type="primary" @click="upAvatar">确 定</el-button>
            </span>
          </form>
        </el-dialog>
      </div>
  </div>
</template>

<script>
import store from '../store'
export default {
name: "Hander",
  data(){
    return{
      user: {
        username: '请先登陆',
        avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
      },
      hasInfo: false,
      imageUrl: '',
      showUpload: false,
      file: {
        file: null
      }
    }
  },
  methods: {
    logout(){
      const _this = this
      // alert(localStorage.getItem("token"))
      _this.$axios.post("/logout",{},{headers: {
          "accessToken": localStorage.getItem("token")
        }})
      .then(res => {
        // console.log(res)
        _this.$store.commit("REMOVE_INFO")
        _this.$router.push("/login")
      })
    },
    upload(){
      this.showUpload = true
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.file.file = file
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    upAvatar(){
      this.showUpload = false
      return this.$message.warning("功能还未开放哦！")
      // let formFile = new FormData();
      // formFile.append("file", this.file.file);
      // const _this = this
      // // alert(localStorage.getItem("token"))
      // _this.$axios.post("/user/upload",formFile,{headers: {
      //     "accessToken": localStorage.getItem("token"),
      //     "contentType":"multipart/form-data"
      //   }})
      //     .then(res => {
      //       debugger
      //       console.log(res)
      //     })
    }
  },
  created() {

    // let userInfo = JSON.parse(this.$store.getters.getUser)
    let userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
    debugger
    if(!userInfo){
      return
    }
    this.user.username = userInfo.username
    this.user.avatar = userInfo.avatar
    this.hasInfo = true
  }
}
</script>

<style scoped>
.m-content {
  margin: auto;
  max-width: 960px;
  text-align: center;
  margin-bottom: 20px;
}

.maction {
  margin-top: 20px;

}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
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

.box-card {
  width: 480px;
}
</style>
