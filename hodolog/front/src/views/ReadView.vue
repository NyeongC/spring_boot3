<script setup lang="ts">



import {onMounted, defineProps, ref} from "vue";
import axios from "axios";
import Router from "@/router";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type:[Number,String],
    require: true,

  }
})

const post = ref({
  id : 0,
  title : "",
  content : "",
});

onMounted(()=>{

  axios.get(`/api/posts/${props.postId}`)
      .then((response)=>{
        post.value = response.data;

      })

});

const router = useRouter();

const moveToEdit = () =>{
  router.push({name:"edit"})
}

</script>

<template>
  <div>{{post.title}}</div>
  <div>{{ post.content }}</div>

  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</template>

<style scoped>

</style>