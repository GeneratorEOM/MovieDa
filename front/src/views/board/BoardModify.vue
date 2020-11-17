<template>
  <v-container>
    <v-layout>
      <v-flex>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field v-model="board.board_writer" label="이름" readonly></v-text-field>

          <v-text-field
            class="mb-5"
            v-model="board.board_subject"
            :rules="subjectRules"
            label="제목"
            required
          ></v-text-field>

          <v-textarea
            outlined
            v-model="board.board_content"
            :rules="contentRules"
            label="내용"
            required
            no-resize
            rows="10"
          ></v-textarea>

          <v-file-input :label="board.board_file == null ? '파일 없음' : board.board_file" outlined dense @change="addFile"></v-file-input>
          <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">완료</v-btn>
          <v-btn color="error" @click="cancel">취소</v-btn>
        </v-form>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      board: this.$route.params.board,
      valid: true,
      subjectRules: [v => !!v.trim() || "제목을 입력해주세요"],
      contentRules: [v => !!v.trim() || "내용을 입력해주세요"],
      isFileChange: false
    };
  },
  methods: {
    validate() {
      if (this.$refs.form.validate()) {
        // 입력 내용을 폼 객체로 전송
        var config = new FormData();
        config.append("board_idx", this.board.board_idx);
        config.append("board_subject", this.board.board_subject);
        config.append("board_content", this.board.board_content);
        config.append("upload_file", this.isFileChange ? this.board.board_file : null);

        axios
          .put("http://localhost:8080/api/board/modify", config, {
            headers: { "Content-Type": "multipart/form-data" }
          })
          .then(res => {
            if (res.status === 200) {
              this.$router.push({
                name: "BoardDetail",
                params: {
                  idx: this.board.board_idx
                }
              });
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    // 입력되는 파일의 정보를 파일 변수에 저장
    addFile(e) {
      this.isFileChange = true
      this.board.board_file = e;
    },
    cancel() {
      this.$router.push({
        name: "BoardDetail",
        params: {
          idx: this.board.board_idx
        }
      });
    }
  }
};
</script>

<style>
</style>