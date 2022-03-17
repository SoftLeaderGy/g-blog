<template>
  <div>
    <hander></hander>
    <div class="m-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Hander from "@/components/Hander";

export default {
  name: "BlogEdit.vue",
  comments: [Hander],
  components: {Hander},
  data() {
    return {
      ruleForm: {
        id: '',
        title: '',
        description: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入摘要', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请填写内容', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将改变数据！, 是否确认发表博客?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            const _this = this
            debugger
            this.$axios.post("/blog/edit",this.ruleForm,{
              headers: {
                "accessToken": localStorage.getItem('token')
              }
            })
              .then(res=>{
                debugger
                if(res.data.code === 200){
                  _this.$message({
                    type: 'success',
                    message: '发布成功!'
                  });
                  // _this.$message.success("发布成功")
                  _this.$router.push("/blogs")
                }
              })
          }).catch(() => {
            _this.$message({
              type: 'info',
              message: '已取消发布'
            });
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    const  blogId = this.$route.params.blogId
    const _this = this
    debugger
    // let bid = parseBi(blogId)
    if(blogId){
      _this.$axios.get('/blogDetail/'+ blogId)
          .then(res=>{
            _this.ruleForm.title = res.data.data.title
            _this.ruleForm.description = res.data.data.description
            _this.ruleForm.content = res.data.data.content
            _this.ruleForm.id = res.data.data.id+ ''
            console.log(res)
          })
    }
  }
}
</script>

<style scoped>
.m-content {
  text-align: center;
}
</style>
