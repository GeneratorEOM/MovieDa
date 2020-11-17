<template>
  <v-container max-width="800">
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">번호</th>
            <th class="text-center">제목</th>
            <th class="text-center">작성자</th>
            <th class="text-center">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr
            style="text-align: center;"
            v-for="board in boards"
            :key="board.board_idx"
            @click="detail(board.board_idx)"
          >
            <td>{{ board.board_idx }}</td>
            <td>{{ board.board_subject }}</td>
            <td>{{ board.board_writer }}</td>
            <td>{{ board.board_date }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <div class="text-center mt-5 mb-5">
      <v-pagination v-model="page" :length="pageCnt" :total-visible="7" @input="getBoardList"></v-pagination>
    </div>
    <v-btn router :to="{name: 'BoardWrite'}">글쓰기</v-btn>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      boards: [],
      page: 1,
      pageCnt: null
    };
  },
  created() {
    axios
      .get("http://localhost:8080/api/board/list", {
        params: { page: this.page }
      })
      .then(res => {
        console.log(res);
        this.boards = res.data.boardList
       
        this.pageCnt = res.data.pageBean.pageCnt;
      })
      .catch(err => {
        alert("ERROR" + err);
      });
  },
  methods: {
    getBoardList() {
      axios
        .get("http://localhost:8080/api/board/list", {
          params: { page: this.page }
        })
        .then(res => {
          console.log(res);
          this.boards = res.data.boardList;
          this.pageCnt = res.data.pageBean.pageCnt;
        })
        .catch(err => {
          alert("ERROR" + err);
        });
    },
    write() {
      axios
        .post("http://localhost:8080/api/board/write")
        .then(res => {
          console.log(res);
          this.boards = res.data;
        })
        .catch(err => {
          alert("ERROR" + err);
        });
    },
    detail(idx) {
      this.$router.push({
        name: "BoardDetail",
        params: {
          idx: idx
        }
      });
    }
  }
};
</script>

<style>
</style>