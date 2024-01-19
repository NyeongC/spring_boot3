<script setup lang="ts">
import {onMounted, defineProps, ref} from "vue";
import axios from "axios";
import Router from "@/router";
import {useRouter} from "vue-router";
import router from "@/router";

const props = defineProps({
  postId: {
    type:[Number,String],
    require: true,

  }
})

onMounted(()=>{

  axios.get(`/api/posts/${props.postId}`)
      .then((response)=>{
        post.value = response.data;

      })

});

const post = ref({
  id : 0,
  title : "",
  content : "",
});




const edit = () => {
  axios.patch(`/api/posts/${props.postId}`,post.value)
      .then((response)=>{
        router.replace({name:"home"})

      })
}

</script>

<template>
  <div class="mt-2">
  <el-input v-model="post.title" type="text" placeholder="제목을 입력하세요."/>
  </div>

  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>

</template>

<style scoped>

</style>