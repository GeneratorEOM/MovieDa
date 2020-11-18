<template>
  <v-container fill-height class="mt-10" style="max-width: 500px">
    <v-layout align-center>
      <v-flex>
    <v-alert class="mb-3" type="error" :value="isEmailError">이메일이 중복되었습니다</v-alert>
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-text-field v-model="email" :rules="emailRules" label="이메일" required></v-text-field>

      <v-text-field v-model="password" type="password" :rules="passwordRules" label="비밀번호" required></v-text-field>

      <v-text-field v-model="name" :rules="nameRules" label="이름" required></v-text-field>

      <v-radio-group v-model="gender" row required>
        <span class="mr-3">성별</span>
        <v-radio label="남" value="남"></v-radio>
        <v-radio label="여" value="여"></v-radio>
      </v-radio-group>

      <v-checkbox
        class="mb-5"
        v-model="checkbox"
        :rules="[v => !!v || '회원가입에 동의해주세요!']"
        label="회원가입에 동의하시겠습니까?"
        required
      ></v-checkbox>

      <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">완료</v-btn>

      <v-btn color="error" router :to="{name: 'MovieList'}">취소</v-btn>
    </v-form>
    </v-flex>
      </v-layout>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      valid: true,
      gender: "남",
      name: "",
      nameRules: [v => !!v || "이름을 입력해주세요"],
      password: "",
      passwordRules: [v => !!v || "비밀번호를 입력해주세요"],
      email: "",
      emailRules: [
        v => !!v || "이메일을 입력해주세요",
        v => /.+@.+\..+/.test(v) || "이메일 형식으로 입력해주세요"
      ],
      checkbox: false
    };
  },
  methods: {
    ...mapActions(["join"]),

    validate() {
      if (this.$refs.form.validate()) {
        this.join({
          user_email: this.email,
          user_password: this.password,
          user_name: this.name,
          user_gender: this.gender
        });
      }
    }
  },
  computed: {
    ...mapState(["isEmailError"])
  }
};
</script>

<style>
</style>