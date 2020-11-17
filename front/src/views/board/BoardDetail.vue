<template>
  <v-container>
    <v-layout>
      <v-flex>
        <v-form ref="form">
          <v-text-field v-model="board.board_writer" label="이름" readonly></v-text-field>

          <v-text-field class="mb-5" v-model="board.board_subject" label="제목" readonly></v-text-field>

          <v-textarea
            outlined
            v-model="board.board_content"
            label="내용"
            no-resize
            rows="10"
            readonly
          ></v-textarea>
          <v-layout>
          <v-flex xs9>
            <v-file-input class="mr-3" :label="board.board_file == null ? '파일 없음' : board.board_file" outlined dense disabled></v-file-input>
          </v-flex>
          <v-flex xs3> 
            <v-btn @click="fileDown">다운로드</v-btn>
          </v-flex>
          </v-layout>
          <template v-if="userInfo != null ? true : false">
            <template v-if="board.board_user_idx == userInfo.idx">
              <v-btn class="mr-3" color="success" @click="modifyBoard">수정</v-btn>
              <v-btn class="mr-3" color="wa rning" @click="deleteBoard">삭제</v-btn>
            </template>
          </template> 
          <v-btn class="mr-3" color="error" router :to="{name: 'BoardList'}">취소</v-btn>
          <v-btn @click="toggle" color="primary">댓글</v-btn>
        </v-form>
      </v-flex>

    </v-layout>

      <BoardComment :idx="idx" :show="show"/>

  </v-container>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import BoardComment from '@/components/board/BoardComment'
export default {
  components: {
    BoardComment
  },
  data() {
    return {
      idx: this.$route.params.idx,
      board: [],
      show: false
    };
  },
  computed: {
    ...mapState(["userInfo"])
  },
  created() {
    axios
      .get("http://localhost:8080/api/board/detail", {
        params: { board_idx: this.idx }
      })
      .then(res => {
        console.log(res)
        this.board = res.data;
      })
      .catch(err => {
        console.log(err);
      });
  },
  methods: {
    modifyBoard() {
      this.$router.push({
        name: "BoardModify",
        params: {
          board: this.board
        }
      });
    },
    deleteBoard() {
      axios
      .delete("http://localhost:8080/api/board/delete", {
        params: { board_idx: this.idx }
      })
      .then(res => {
        if(res.status === 200) this.$router.push({name: 'BoardList'})
      })
      .catch(err => {
        console.log(err);
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