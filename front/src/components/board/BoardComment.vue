<template>
  <v-list two-line v-if="show">
    <template v-if="userInfo != null">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-list-item>
          <v-header>{{userInfo.name}}</v-header>
          <v-textarea
            class="mx-2 mt-6"
            label="내용"
            ref="comment"
            v-model="content"
            :rules="contentRules"
            outlined
            rows="3"
            required
          ></v-textarea>
          <v-btn :disabled="!valid" color="primary" @click="validate">작성</v-btn>
        </v-list-item>
      </v-form>
    </template>
    <template v-for="(comment, index) in comments">
      <v-list-item :key="index+'list'">
        <v-list-item-content>
          <v-list-item-title class="mb-3" v-text="comment.comment_writer"></v-list-item-title>

          <v-list-item-subtitle class="mb-3" v-text="comment.comment_content"></v-list-item-subtitle>

          <v-list-item-subtitle v-text="comment.comment_date"></v-list-item-subtitle>
        </v-list-item-content>

        <template v-if="userInfo != null ? true : false">
          <v-list-item-action v-if="comment.comment_user_idx === userInfo.idx ">
            <v-flex>
              <v-btn
                class="mr-1"
                color="primary"
                depressed
                small
                @click="comment.isActive ? modifyCommentInfo(comment) : isActiveToggle(comment)"
              >{{comment.isActive ? '완료' : '수정'}}</v-btn>
              <v-btn
                color="primary"
                depressed
                small
                @click="comment.isActive ? isActiveToggle(comment) : deleteCommentInfo(comment)"
              >{{comment.isActive ? '취소' : '삭제'}}</v-btn>
            </v-flex>
          </v-list-item-action>
        </template>
      </v-list-item>

      <v-text-field
        v-if="comment.isActive"
        v-model="comment.modifyContent"
        outlined
        clearable
        label="수정 내용"
        :rules="contentRules"
        type="text"
        :key="index + 'text'"
        required
      ></v-text-field>


      <v-divider v-if="index < comments.length - 1" :key="index + 'divider'"></v-divider>
    </template>
  </v-list>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  props: {
    idx: {
      type: Number,
      required: true
    },
    show: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      valid: true,
      content: "",
      contentRules: [v => !!v.trim() || "내용을 입력해주세요"]
    };
  },
  computed: {
    ...mapState(["userInfo", "comments"])
  },
  created() {
    this.getCommentList(this.idx);
  },
  methods: {
    ...mapActions(["getCommentList", "writeComment", "isActiveToggle", "modifyCommentInfo", "deleteCommentInfo"]),
    // 댓글 입력
    validate() {
      // 유효성 검사를 통과하면 true
      if (this.$refs.form.validate()) {
        this.writeComment({
          content: this.content,
          board_idx: this.idx,
          user_idx: this.userInfo.idx

        })
        // 유효성 검사 메세지 초기
        this.$refs.form.resetValidation();
        this.content = ""
        
      }
    }
  }
};
</script>

<style>
</style>