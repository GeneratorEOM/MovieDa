<template>
  <v-container fill-heigth mt-10 max-width="500" style="max-width: 800px">
    <v-layout align-center>
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

          <v-file-input :label="board.board_file == null ? '파일 없음' : board.board_file" outlined dense @change="changeFile"></v-file-input>
          <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">완료</v-btn>
          <v-btn color="error" @click="cancel">취소</v-btn>
        </v-form>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>

import {mapState, mapActions} from 'vuex'
export default {
  data() {
    return {
      board: this.$route.params.board,
      valid: true,
      subjectRules: [v => !!v.trim() || "제목을 입력해주세요"],
      contentRules: [v => !!v.trim() || "내용을 입력해주세요"],
    };
  },
  computed: {
    ...mapState(["file"])
  },
  methods: {
    ...mapActions(["modifyBoardInfo", "changeFile"]),

    validate() {
      if (this.$refs.form.validate()) {
        this.modifyBoardInfo({
          idx: this.board.board_idx,
          subject: this.board.board_subject,
          content: this.board.board_content,
          file: this.file
        })
      }
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