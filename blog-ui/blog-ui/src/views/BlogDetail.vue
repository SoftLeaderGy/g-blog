<template>
  <div>
    <Hander></Hander>
    <div class="dea-content">
      <h2>{{blog.title}}</h2>

        <el-link icon="el-icon-edit" v-if="hasAuth"><router-link :to="{name: 'BlogEdit',params:{
          blogId: this.blog.id
        }}">编辑</router-link>
        </el-link>


      <el-divider content-position="left">{{this.blog.author}}</el-divider>
<!--      使用markdown-body 央视渲染 md文件格式-->
<!--      使用之前需要先导入import 'github-markdown-css/github-markdown.css'-->
      <div class="markdown-body" v-html="blog.content"></div>
    </div>
  </div>
</template>

<script>
import Hander from "@/components/Hander";
import 'github-markdown-css/github-markdown.css'
export default {
  name: "BlogDetail.vue",
  components: {Hander},
  data(){
    return {
      blog:{
        userId: '',
        title: '',
        id: '',
        description: '',
        content: '',
        author: ''
      },
      userName: '',
      hasAuth: false
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
            _this.blog.title = res.data.data.title
            _this.blog.description = res.data.data.description
            _this.blog.userId = res.data.data.userId
            // 引入mardownIt 来渲染博客内容
            // 因为博客内容的 md文件形式
            var MardownIt = require("markdown-it")
            var md = new MardownIt()
            let content = md.render(res.data.data.content)
            _this.blog.content = content
            _this.blog.id = res.data.data.id+ ''
            _this.blog.author = res.data.data.author
            debugger

             // 验证如果博客的userId和登陆的账号的id不同的话，则不显示编辑按钮
            _this.hasAuth = res.data.data.userId === JSON.parse(sessionStorage.getItem('userInfo')).id
            console.log(res)
          })
      debugger
      console.log(_this.blog)
    }
    _this.userName = JSON.parse(sessionStorage.getItem('userInfo')).username
    debugger
    // _this.hasAuth = _this.blog.userId === JSON.parse(_this.$store.getters.getUser).userId

  }
}
</script>

<style scoped>
  .dea-content {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 100%;
    min-height: 700px;
    padding: 20px 30px;
  }
</style>
