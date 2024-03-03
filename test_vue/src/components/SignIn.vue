<template>
  <div>
    <h2>로그인</h2>
    <el-form ref="loginForm" label-width="80px">
      <el-form-item label="이메일" prop="email">
        <el-input v-model="email" />
      </el-form-item>
      <el-form-item label="비밀번호" prop="password">
        <el-input type="password" v-model="password" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">로그인</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ref} from "vue"
import axios from 'axios';
//import {useRouter} from "vue-router";
//const router = useRouter()

const email = ref("")
const password = ref("")
const login = function (){
  //alert(password.value)

  // FormData 생성
  const formData = new FormData();
  formData.append("username", email.value);
  formData.append("password", password.value);

  axios.post('http://localhost:8080/login',
  formData, {
    withCredentials: true, // 필요에 따라 설정
    headers: {
        'Content-Type': 'multipart/form-data', // FormData를 사용하므로 Content-Type을 multipart/form-data로 설정
        'Access-Control-Allow-Origin': 'http://localhost:8081' // 프론트엔드 주소
      }
})
      .then(()=>{
        //router.replace({ name : "home"});
        alert("로그인 완료")

      })
      .catch(()=>{
        alert("로그인 실패")
      })
}

</script>

<style scoped>
/* 스타일 */
.el-form {
  max-width: 400px; /* 폼의 최대 너비 설정 */
  margin: auto; /* 가운데 정렬을 위해 */
}

.el-button {
  width: 100%; /* 버튼을 폼의 너비에 맞게 설정 */
  margin-top: 10px; /* 간격 추가 */
}
</style>

