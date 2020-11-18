<template>
  <v-container mt-10 max-width="500" style="max-width: 800px">
    <v-layout>
      <v-flex>
        <v-form ref="form">
          <v-text-field v-model="boardInfo.board_writer" label="이름" readonly></v-text-field>

          <v-text-field class="mb-5" v-model="boardInfo.board_subject" label="제목" readonly></v-text-field>

          <v-textarea
            outlined
            v-model="boardInfo.board_content"
            label="내용"
            no-resize
            rows="10"
            readonly
          ></v-textarea>
          <v-layout>
          <v-flex xs9>
            <v-file-input class="mr-3" :label="boardInfo.board_file == null ? '파일 없음' : boardInfo.board_file" outlined dense disabled></v-file-input>
          </v-flex>
          <v-flex xs3> 
            <v-btn @click="fileDown">다운로드</v-btn>
          </v-flex>
          </v-layout>
          <template v-if="userInfo != null ? true : false">
            <template v-if="boardInfo.board_user_idx == userInfo.idx">
              <v-btn class="mr-3" color="success" @click="modifyBoardInfo">수정</v-btn>
              <v-btn class="mr-3" color="wa rning" @click="deleteBoardInfo(board_idx)">삭제</v-btn>
            </template>
          </template> 
          <v-btn class="mr-3" color="error" router :to="{name: 'BoardList'}">취소</v-btn>
          <v-btn @click="toggle" color="primary">댓글</v-btn>
        </v-form>
      </v-flex>

    </v-layout>

      <BoardComment :idx="board_idx" :show="show"/>

  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import BoardComment from '@/components/board/BoardComment'
export default {
  components: {
    BoardComment
  },
  data() {
    return {
      board_idx: Number(this.$route.params.idx),
      show: false
    };
  },
  computed: {
    ...mapState(["userInfo", "boardInfo"])
  },
  created() {
    this.getBoardInfo(this.board_idx)
  },
  methods: {
    ...mapActions(['getBoardInfo', 'deleteBoardInfo']),

  modifyBoardInfo() {
    this.$router.push({
      name: "BoardModify",
      params: { board: this.boardInfo }
    });
  },
  fileDown() {
    location.href="http://localhost:8080/api/board/fileDown?file_name="+this.board.board_file
  },
  toggle() {
    this.show = !this.show
  }
    
  }
};
</script>

<style>
</style>