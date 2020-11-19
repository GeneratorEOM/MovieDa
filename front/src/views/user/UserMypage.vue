<template>
  <v-container fill-height class="mt-10" style="max-width: 500px">
    <v-layout align-center>
      <v-flex>
    
    <v-form ref="form" v-model="valid" lazy-validation>
      <template v-if="toggle">
      <v-text-field v-model="userInfo.email" :rules="emailRules" label="이메일" readonly filled rounded></v-text-field>

      <v-text-field v-model="userInfo.name" :rules="nameRules" label="이름" readonly filled rounded></v-text-field>

      <v-radio-group v-model="userInfo.gender" row readonly>
        <span class="mr-3">성별</span>
        <v-radio label="남" value="남"></v-radio>
        <v-radio label="여" value="여"></v-radio>
      </v-radio-group>
      
            <v-btn style="float: left;"
        :disabled="!valid"
        color="success"
        class="mr-4"
        @click="modifyValidate"
      >수정</v-btn>
      <Modal style="float: left; margin-right: 15px"/>
     <v-btn style="clear: both;" color="error" router :to="{name: 'MovieList'}">취소</v-btn>
  </template>
        <template v-else>
              <v-alert class="mb-3" type="error" :value="isPasswordError">비밀번호가 일치하지 않습니다</v-alert>
      <v-text-field v-model="userInfo.email" :rules="emailRules" label="이메일" readonly ></v-text-field>

      <v-text-field v-model="password" type="password" :rules="passwordRules" label="비밀번호" required ></v-text-field>

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
        @click="modifyValidate"
      >수정</v-btn>
    <v-btn color="error" @click="toggleAction">취소</v-btn>
  </template>
      



     
    </v-form>
    </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import { mapState, mapActions } from "vuex";
import Modal from "@/components/Modal"
export default {
  components: {
    Modal
  },
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
      isPasswordError: false
    };
  },
  computed: {
    ...mapState(["userInfo", "toggle"])
  },
  methods: {
    ...mapActions(["modifyUserInfo", "deleteUserInfo", "toggleAction"]),

      modifyValidate() {

        if(this.toggle) {
          this.toggleAction()
          return false
        }
          
          if (this.$refs.form.validate()) {
            
            if(this.password !== this.userInfo.password) {
              this.isPasswordError = true
              return false
            }
  
            this.modifyUserInfo({
              idx: this.userInfo.idx,
              password: this.password,
              name: this.userInfo.name,
              gender: this.userInfo.gender
            })
          }

      },
  }
};
</script>