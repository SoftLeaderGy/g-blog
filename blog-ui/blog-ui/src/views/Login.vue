<template>
  <div>
    <el-container>
      <el-header>
        <router-link :to="{ name: 'Blogs'}">
          <img src="http://yang.gunveda.top:8090/test.png"
               style="height: 180%; margin-top: -25px;">
        </router-link>
      </el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
<!--          <el-form-item label="邮箱" prop="email">-->
<!--            <el-input v-model="ruleForm.email"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item
              prop="email"
              label="邮箱"
              :rules="[
      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
    ]"
          >
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登陆</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-link type="info" :underline="false" href="#/register">没有登陆的账号？</el-link>
          </el-form-item>

        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      ruleForm: {
        email: '',
        password: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        // 将this预存起来，
        // 原因：在调用this.$axios 后 在里边的this就指的是返回的结果，而不是vue本身的this
        const _this = this
        if (valid) {
          this.$axios.post("/login",this.ruleForm)
          .then(res =>{
            // 获取accesstoken
            const jwt = res.headers['accesstoken']
            // 获取用户信息
            const userInfo = res.data.data

            // 把数据共享到全局变量中去
            // 通过_this.$store调用commit 参数一：store定义的方法 。参数二：传入的参数
            _this.$store.commit("SET_TOKEN",jwt)
            _this.$store.commit("SET_USERINFO", JSON.stringify(userInfo))

            // 在全局变量中获取用户信息
            console.log(JSON.parse(_this.$store.getters.getUser))

            if(res.data.code == 200){
              _this.$router.push("/blogs")
            }
            // console.log(jwt)
            // console.log(userInfo)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.demo-ruleForm {
  max-width: 500px;
  margin: auto;
}
</style>
