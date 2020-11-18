<template>
  <v-container fill-height mt-10 style="max-width: 800px">
    <v-layout align-center>
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

          <v-file-input type="file" label="파일 첨부" outlined dense @change="changeFile"></v-file-input>
          <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">완료</v-btn>
          <v-btn color="error" router :to="{name: 'BoardList'}">취소</v-btn>
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
      subject: "",
      subjectRules: [v => !!v.trim() || "제목을 입력해주세요"],
      content: "",
      contentRules: [v => !!v.trim() || "내용을 입력해주세요"],
    
    };
  },

  computed: {
    ...mapState(["userInfo", "file"])
  },
  methods: {
    ...mapActions(['writeBoard', 'changeFile']),

    validate() {
      if (this.$refs.form.validate()) {
        this.writeBoard({
          idx: this.userInfo.idx,
          subject: this.subject,
          content: this.content,
          file: this.file
        })
      }
    },

  }
};
</script>

<style>
</style>