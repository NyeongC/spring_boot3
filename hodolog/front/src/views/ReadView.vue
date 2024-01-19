<script setup lang="ts">



import {onMounted, defineProps, ref} from "vue";
import axios from "axios";

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
</script>

<template>
  <div>{{post.title}}</div>
  <div>{{ post.content }}</div>
</template>

<style scoped>

</style>