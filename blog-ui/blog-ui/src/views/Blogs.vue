<template>
  <div>
    <hander></hander>
    <div class="block">
      <el-timeline>
        <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
          <el-card>
            <h4>
              <router-link :to="{
                name: 'BlogDetail',
                params: {
                  blogId: blog.id
                }
              }">
              {{blog.title}}
              </router-link>
            </h4>
            <p>{{blog.description}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <div class="mpage">
      <el-pagination
          layout="prev, pager, next"
          :total="total"
          :current-page="currentPage"
          :page-size="pageSize"
          @current-change=page>
      </el-pagination>
    </div>
  </div>
</template>

<script>
import Hander from "@/components/Hander";
export default{
  name: "Blogs.vue",
  components: {Hander},
  comments: [Hander],
  data(){
    return{
      blogs: [],
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  created() {
    this.page(1)
  },
  methods: {
    page(currentPage){
      debugger
      const _this = this
      _this.$axios.get( "/blog?currentpage=" + currentPage)
          .then(res=>{
            console.log(res)
            _this.blogs = res.data.data.records
            _this.currentPage = res.data.data.current
            _this.total = res.data.data.total
            _this.pageSize = res.data.data.size
          })
    }
  }
}
</script>

<style scoped>
.mpage {
  margin: auto;
  text-align: center;
}
</style>
