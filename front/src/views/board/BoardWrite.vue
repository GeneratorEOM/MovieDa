<template>
  <v-container>
    <v-layout>
      <v-flex>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field v-model="userInfo.name" label="이름" readonly></v-text-field>

          <v-text-field class="mb-5" v-model="subject" :rules="subjectRules" label="제목" required></v-text-field>

          <v-textarea
            outlined
            v-model="content"
            :rules="contentRules"
            label="내용"
            required
            no-resize
            rows="10"
          ></v-textarea>

          <v-file-input type="file" label="파일 첨부" outlined dense @change="addFile"></v-file-input>
          <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">완료</v-btn>
          <v-btn color="error" router :to="{name: 'BoardList'}">취소</v-btn>
        </v-form>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";

export default {
  data() {
    return {
      valid: true,
      subject: "",
      subjectRules: [v => !!v.trim() || "제목을 입력해주세요"],
      content: "",
      contentRules: [v => !!v.trim() || "내용을 입력해주세요"],
      file: null
    };
  },

  computed: {
    ...mapState(["userInfo"])
  },
  methods: {
    validate() {
      if (this.$refs.form.validate()) {
        // 입력 내용을 폼 객체로 전송
        var config = new FormData();
        config.append("User_user_idx", this.userInfo.idx);
        config.append("board_writer", this.userInfo.name);
        config.append("board_subject", this.subject);
        config.append("board_content", this.content);
        config.append("upload_file", this.file);

        axios
          .post("http://localhost:8080/api/board/write", config, {
            headers: { "Content-Type": "multipart/form-data" }
          })
          .then(res => {
           
            if (res.data === 1) this.$router.push({ name: "BoardList" });
            else alert("ERROR")
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    // 입력되는 파일의 정보를 파일 변수에 저장
    addFile(e) {
      this.file = e;
    }
  }
};
</script>

<style>
</style>