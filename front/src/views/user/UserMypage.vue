<template>
  <v-container class="mt-10" style="max-width: 500px">
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-text-field v-model="userInfo.email" :rules="emailRules" label="이메일" required readonly></v-text-field>

      <v-text-field v-model="password" type="password" :rules="passwordRules" label="비밀번호" required></v-text-field>

      <v-text-field v-model="userInfo.name" :rules="nameRules" label="이름" required></v-text-field>

      <v-radio-group v-model="userInfo.gender" row required>
        <span class="mr-3">성별</span>
        <v-radio label="남" value="남"></v-radio>
        <v-radio label="여" value="여"></v-radio>
      </v-radio-group>

      <v-btn
        :disabled="!valid"
        color="success"
        class="mr-4"
        @click="modifyUserInfo({
		  email: userInfo.email,
		  password: userInfo.password,
		  name: userInfo.name,
		  gender: userInfo.gender
	  })"
      >수정</v-btn>
      <v-btn
        :disabled="!valid"
        color="warning"
        class="mr-4"
        @click="deleteUserInfo({email: userInfo.email})"
      >탈퇴</v-btn>

      <v-btn color="error" router :to="{name: 'MovieList'}">취소</v-btn>
    </v-form>
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
      nameRules: [
        v => !!v || "이름을 입력해주세요"
      ],
      passwordRules: [
        v => !!v || "비밀번호를 입력해주세요"
      ],
      email: "",
      emailRules: [
        v => !!v || "이메일을 입력해주세요",
        v => /.+@.+\..+/.test(v) || "이메일 형식으로 입력해주세요"
      ]
    };
  },
  computed: {
    ...mapState(["userInfo"])
  },
  methods: {
    ...mapActions(["modifyUserInfo", "deleteUserInfo"])
  }
};
</script>