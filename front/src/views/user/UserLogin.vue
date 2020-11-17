<template>
  <v-container fill-height class="gray lighten-5" style="max-width: 500px">
    <v-layout align-center>
      <v-flex>
        <v-alert class="mb-3" type="error" :value="isLoginError">아이디 또는 비밀번호가 일치하지 않습니다</v-alert>
        <v-card>
          <v-toolbar flat text-center color="gray lighten-5">
            <v-toolbar-title>로그인</v-toolbar-title>
          </v-toolbar>
          <div class="pa-3">
            <v-text-field class="mb-5" v-model="email" label="이메일을 입력하세요" hide-details="auto"></v-text-field>
            <v-text-field v-model="password" type="password" label="패스워드를 입력하세요"></v-text-field>
            <v-btn
              block
              depressed
              color="primary"
              @click="login({ email: email, password: password })"
            >로그인</v-btn>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      email: null,
      password: null
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError", "errorMessage"])
  },
  methods: {
    ...mapActions(["login"])
  }
};
</script>

<style>
</style>