<template>
  <v-container mt-10 max-width="800">
    <v-layout align-center>
      <v-flex>
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
            @click="boardDetail(board.board_idx)"
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
      <pagination :pageCnt="pageCnt" :visibleCnt="visibleCnt" @change="getBoardList" />
    </div>
    <v-btn router :to="{name: 'BoardWrite'}">글쓰기</v-btn>
    </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import Pagination from "@/components/Pagination"
import {mapState, mapActions} from 'vuex'
export default {
  components: {
    Pagination
  },
  data() {
    return {}
  },
  computed: {
    ...mapState(['boards', 'pageCnt', 'visibleCnt'])
  },
  created() {
    this.getBoardList(1)
  },
  methods: {

    ...mapActions(["getBoardList"]),

    boardDetail(board_idx) {
      this.$router.push({
        name: 'BoardDetail',
        params: {
          idx: board_idx
        }
      })
    }

  }
};
</script>

<style>
</style>