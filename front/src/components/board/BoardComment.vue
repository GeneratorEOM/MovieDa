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
                @click="comment.isActive ? modifyComment(comment) : isActiveToggle(comment)"
              >{{comment.isActive ? '완료' : '수정'}}</v-btn>
              <v-btn
                color="primary"
                depressed
                small
                @click="comment.isActive ? isActiveToggle(comment) : deleteComment(comment)"
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
import axios from "axios";
import { mapState } from "vuex";
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
      comments: [],
      content: "",
      contentRules: [v => !!v.trim() || "내용을 입력해주세요"]
    };
  },
  computed: {
    ...mapState(["userInfo"])
  },
  created() {
    this.getCommentList();
  },
  methods: {
    // 댓글 입력
    validate() {
      // 유효성 검사를 통과하면 true
      if (this.$refs.form.validate()) {
        // 데이터 담아서
        let config = {
          comment_content: this.content,
          comment_board_idx: this.idx,
          comment_user_idx: this.userInfo.idx
        };
        axios
          .post("http://localhost:8080/api/board/comment/write", config)
          .then(res => {
            if (res.status === 200) {
              // 리스트 다시 불러오기
              this.getCommentList();
              // 댓글창 초기화
              this.content = "";
              // 유효성 검사 메세지 초기
              this.$refs.form.resetValidation();
            }
          })
          .catch(err => {
            alert("ERROR" + err);
          });
      }
    },
    // 리스트 가져오기 
    getCommentList() {
      axios
        .get("http://localhost:8080/api/board/comment/list", {
          params: { board_idx: this.idx }
        })
        .then(res => {
          if (res.status === 200) {
            // 서버에서 리스트 가져오기
            this.comments = res.data;
            // 댓글마다 고유의 값을 가져야하기 때문에 각 행 별로 변수 추가
            // 서버에서 가져온 리스트의 각 행 마다 수정폼을 띄우기 위한 액티브 변수와 수정값을 담을 변수 추가
            this.comments = this.comments.map(comment => ({
              ...comment,
              isActive: false,
              modifyContent : comment.comment_content
            }));
          }
        })
        .catch(err => {
          alert("ERROR" + err);
        });
    },
    // 수정폼을 위한 토글 메서드, 클릭하면 폼 toggle
    isActiveToggle(comment) {
      comment.isActive = !comment.isActive;
    },
    // 수정하기
    modifyComment(comment) {
      // 각 댓글마다 폼 validate를 사용할 방법을 모르겠음
      // 공백이면 null 값을 넣어서 강제로 유효성 검사 하게함 
      if(comment.modifyContent.trim() == "" || comment.modifyContent == null) {
        comment.modifyContent = null
        return false    
      }
      // 수정할 데이터 담아서
      let config = {
        comment_idx: comment.comment_idx,
        comment_content: comment.modifyContent
      };
      axios
        .put("http://localhost:8080/api/board/comment/modify", config)
        .then(res => {
          if (res.status === 200) {
            // 리스트 다시 불러오기
            this.getCommentList();
            // 수정 폼 닫기
            this.isActiveToggle(comment);
            // 입력폼 초기화 
            comment.modifyContent = "";
  
          }
        })
        .catch(err => {
          alert("ERROR123" + err);
        });
    },
    // 댓글 삭제
    deleteComment(comment) {
      axios
        .delete("http://localhost:8080/api/board/comment/delete", {
          params: { comment_idx: comment.comment_idx }
        })
        .then(res => {
          if (res.status === 200) {
            // 리스트 다시 불러오기
            this.getCommentList();
          }
        })
        .catch(err => {
          alert("ERROR" + err);
        });
    }
  }
};
</script>

<style>
</style>