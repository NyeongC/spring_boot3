<template>
  <div>
    <h2>회원가입</h2>
    <el-form ref="signupForm" label-width="80px">
      <el-form-item label="이메일" prop="email">
        <el-input v-model="email" />
      </el-form-item>
      <el-form-item label="비밀번호" prop="password">
        <el-input type="password" v-model="password" />
      </el-form-item>
      <el-form-item label="비밀번호 확인" prop="passwordConfirm">
        <el-input type="password" v-model="passwordConfirm" />
        <div v-if="passwordMismatch" style="color: red; margin-top: 5px;">비밀번호가 일치하지 않습니다.</div>
      </el-form-item>
      <el-form-item label="이름" prop="name">
        <el-input v-model="name" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="signup" :disabled="passwordMismatch">회원가입</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ref} from "vue"
import axios from 'axios';
// import {useRouter} from "vue-router";
// const router = useRouter()

const email = ref("")
const password = ref("")
const name = ref("")
const passwordConfirm = ref("");
const passwordMismatch = ref(false);


const signup = function (){

  if (password.value !== passwordConfirm.value) {
    alert("비밀번호가 일치하지 않습니다.");
    passwordMismatch.value = true;
    return;
  }
  //alert(password.value)
  axios.post('http://localhost:8080/user',
      {
        email: email.value,
        password: password.value,
        name : name.value
      }, {
    withCredentials: true, // 필요에 따라 설정
    headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:8081' // 프론트엔드 주소
    }
})
      .then((response)=>{
        //router.replace({ name : "home"});
        if (response.status === 200) {
          alert("회원가입 완료!" + JSON.stringify(response.data));
        } else {
          alert("회원가입 실패");
        }

      })
      .catch(()=>{
        alert("회원가입 실패");
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
