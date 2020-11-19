import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    toggle: true,

    userInfo: null,
    isLogin: false,
    isLoginError: false,
    isEmailError: false,

    movies: [],
    pageCnt: 0,
    visibleCnt: 7,

    movieInfo: [],

    reviews: [],
    rating: 3,

    boards: [],
    boardInfo: [],
    file: null,

    comments: []

  },
  mutations: {
    // toggle
    toggleAction(state) {
      state.toggle = !state.toggle
    },
    // 로그인이 성공했을 때
    loginSuccess(state, payload) {
      state.isLogin = true
      state.isLoginError = false
      state.userInfo = payload
    },
    // 로그인이 실패했을 때
    loginError(state) {
      state.isLogin = false
      state.isLoginError = true
    },
    // 로그아웃 했을 때
    logout(state) {
      state.isLogin = false
      state.isLoginError = false
      state.userInfo = null
    },
    // 유저정보 수정 할 때
    modifyUserInfo(state) {
      state.userInfo
    },
    // 회원가입 실패 - 아이디 중복
    joinEmailError(state) {
      state.isEmailError = true
    },
    // 메인 페이지 생성될 때   
    getMovieList(state, payload) {
      state.movies = payload.movies
      state.pageCnt = payload.pageCnt
    },
    // 영화정보 가져오기
    getMovieInfo(state, payload) {
      state.movieInfo = payload
    },
    // 리뷰 가져오기
    getReviewList(state, payload) {
      state.reviews = payload
      state.reviews = state.reviews.map(review => ({
        ...review,
        isActive: false,
        modifyContent: review.review_content,
        modifyRating: review.review_rating
      }));
    },
    // 게시판 리스트 가져오기
    getBoardList(state, payload) {
      state.boards = payload.boards
      state.pageCnt = payload.pageCnt
    },
    // 게시판 글 가져오기
    getBoardInfo(state, payload) {
      state.boardInfo = payload
    },
    // 파일 변경
    changeFile(state, payload) {
      state.file = payload
    },
    // 댓글 리스트 가져오기
    getCommentList(state, payload) {
      state.comments = payload
      state.comments = state.comments.map(comment => ({
        ...comment,
        isActive: false,
        modifyContent : comment.comment_content
      }));
    }


  },
  actions: {
    // toggle
    toggleAction({commit}) {
      commit("toggleAction")
    },
    // 로그인 액션
    login({ commit }, loginObj) {
      
      // Login.vue 에서 가져온 loginObj 를 파라미터로 서버에 전송
      // 토큰을 이용하여 로그인 처리를 해야 하지만 그건 나중에 ..... 
      let config = {
        user_email: loginObj.email,
        user_password: loginObj.password
      }
      // axios.post 사용해서 서버로 post 방식 요청 content-type: json
      axios.post('http://localhost:8080/api/user/login', config)
        .then(res => {
          // 아이디, 비번이 일치하지 않는다면 서버에서 0 을 리턴
          if (res.data === "") {
            commit('loginError')
          } else {
            let userInfo = {
              idx: res.data.user_idx,
              email: res.data.user_email,
              password: res.data.user_password,
              name: res.data.user_name,
              gender: res.data.user_gender
            }
            commit('loginSuccess', userInfo)
            sessionStorage.setItem("token", res.data)
            // 로그인 처리 후 홈으로 이동
            router.push({
              name: 'MovieList'
            })
          }

        })
        // 요청 실패
        .catch(err => {
          console.log(err)
        })
    },
    // 로그아웃 액션
    logout({ commit }) {
      // 로그아웃
      commit('logout')
      // 세션 삭제
      sessionStorage.removeItem("token")
      // 홈 이동
      router.push({
        name: 'MovieList'
      })
    },
    // 새로고침할때 로그인 유지하기   
    loginCheck({ commit }) {
      // 세션에 있는 토큰 저장
      let email = sessionStorage.getItem('token')
      // 토큰이 존재하면 로그인 됨
      // 로그인 처리
      let config = {
        params: {
          "token": email
        }
      }
      if (email != null) {
        axios.get('http://localhost:8080/api/user/info', config)
          .then(res => {
          if (res.status === 200) {
            let userInfo = {
              idx: res.data.user_idx,
              email: res.data.user_email,
              password: res.data.user_password,
              name: res.data.user_name,
              gender: res.data.user_gender
            }
            commit('loginSuccess', userInfo)
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    // 회원가입 액션
    join({ commit }, joinObj) {
      let config = {
        user_email: joinObj.user_email,
        user_password: joinObj.user_password,
        user_name: joinObj.user_name,
        user_gender: joinObj.user_gender
      }
      axios.post('http://localhost:8080/api/user/join', config)
        .then(res => {
          if (res.data === 1) router.push({ name: 'UserLogin' })
          else if (res.data === -1)commit('joinEmailError')
          else alert("가입 실패")
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 회원정보 수정 액션
    modifyUserInfo({commit}, modifyUserObj) {
       
      let config = {
        user_idx: modifyUserObj.idx,
        user_password: modifyUserObj.password,
        user_name: modifyUserObj.name,
        user_gender: modifyUserObj.gender
      }
      axios.put('http://localhost:8080/api/user/modify', config)
        .then(res => {
          if (res.status === 200) commit("toggleAction")
          else alert("수정 실패")
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 회원정보 삭제 액션
    deleteUserInfo({dispatch }, user_idx) {

      axios.delete('http://localhost:8080/api/user/delete', { params: { user_idx: user_idx } })
        .then(res => {
          if (res.data === true) dispatch('logout')
          else alert("삭제 실패")
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 메인 영화리스트 가져오기
    getMovieList({ commit }, pageNum) {
      axios
        .get(
          "https://api.themoviedb.org/3/discover/movie?api_key=4738775b2c15a47761f19db1576589c8&language=ko-KR&sort_by=popularity.desc&include_adult=false&include_video=false&page=" +
          pageNum
        )
        .then(res => {
          if (res.status === 200) commit('getMovieList', {
            movies: res.data.results,
            pageCnt: res.data.total_pages
          })
        });
    },
    // 영화정보 가져오기
    getMoiveInfo({ commit }, movie_id) {
      axios
        .get(
          "https://api.themoviedb.org/3/movie/" +
          movie_id +
          "?api_key=4738775b2c15a47761f19db1576589c8" +
          "&language=ko-KR"
        )
        .then(res => {
          if (res.status === 200) commit('getMovieInfo', res.data)
        });
    },
    // 리뷰 가져오기
    getReviewList({ commit }, movie_id) {
      axios.get('http://localhost:8080/api/movie/review/list', { params: { review_movie_id: movie_id } })
        .then(res => {
          if (res.status === 200) commit('getReviewList', res.data)
        })
    },
    // 리뷰 작성하기
    writeReview({ dispatch }, reviewObj) {
      let config = {
        review_movie_id: reviewObj.review_movie_id,
        review_user_idx: reviewObj.review_user_idx,
        review_content: reviewObj.review_content,
        review_rating: reviewObj.review_rating
      }
      axios
        .post('http://localhost:8080/api/movie/review/write', config)
        .then(res => {
          if (res.status === 200) dispatch('getReviewList', reviewObj.movie_id)
        })
    },
    // 리뷰에서 수정폼을 위한 토글 메서드, 클릭하면 폼 toggle
    isActiveToggle(context, review) {
      review.isActive = !review.isActive
      review.modifyContent = ""
    },

    // 리뷰 수정하기
    modifyReview({ dispatch }, reviewObj) {

      if (reviewObj.modifyContent.trim() === "" || reviewObj.modifyContent === null) {
        reviewObj.modifyContent = null
        return false
      }

      let config = {
        review_idx: reviewObj.review_idx,
        review_content: reviewObj.modifyContent,
        review_rating: reviewObj.modifyRating
      }
      axios
        .put('http://localhost:8080/api/movie/review/modify', config)
        .then(res => {
          if(res.status === 200) {
            dispatch('getReviewList', reviewObj.review_movie_id)
            dispatch('isActiveToggle', reviewObj)
          }
        })
    },
    // 리뷰 삭제하기
    deleteReview({dispatch}, reviewObj) {
      axios.delete('http://localhost:8080/api/movie/review/delete', { params: { review_idx: reviewObj.review_idx } })
        .then(res => {
          if(res.status === 200) dispatch('getReviewList', reviewObj.review_movie_id)
        })
    },
    // 게시판 리스트 가져오기
    getBoardList({commit}, pageNum) {
      axios
        .get("http://localhost:8080/api/board/list", {
          params: { page: pageNum }
        })
        .then(res => {
          if (res.status === 200) commit('getBoardList', {
            boards: res.data.boardList,
            pageCnt: res.data.pageBean.pageCnt
          })
        })
    },

    // 글쓰기
    writeBoard(context, boardObj) {
      // 입력 내용을 폼 객체로 전송
      var config = new FormData();
      config.append("board_user_idx", boardObj.idx);
      config.append("board_subject", boardObj.subject);
      config.append("board_content", boardObj.content);
      config.append("upload_file", boardObj.file);

      axios
        .post("http://localhost:8080/api/board/write", config, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(res => {        
          if (res.status === 200) router.push({ name: "BoardList" });
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 게시판 글 가져오기
    getBoardInfo({commit}, board_idx) {
      axios
      .get("http://localhost:8080/api/board/detail", {
        params: { board_idx: board_idx }
      })
      .then(res => {
        if (res.status === 200) commit('getBoardInfo', res.data)
      })
    },
    // 게시판 글 삭제하기
    deleteBoardInfo(context, board_idx) {
      let c = confirm("삭제하시겠습니까?")
      if(c === false) return false
      axios
      .delete("http://localhost:8080/api/board/delete", {
        params: { board_idx: board_idx }
      })
      .then(res => {
        if(res.status === 200) router.push({name: 'BoardList'})
      })  
    },
    // 게시판 글 수정하기
    modifyBoardInfo(context, boardInfo) {
      // 입력 내용을 폼 객체로 전송
      var config = new FormData();
      config.append("board_idx", boardInfo.idx);
      config.append("board_subject", boardInfo.subject);
      config.append("board_content", boardInfo.content);
      config.append("upload_file", boardInfo.file);

      axios
        .put("http://localhost:8080/api/board/modify", config, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(res => {
          if (res.status === 200) {
              router.push({
              name: "BoardDetail",
              params: {
              idx: boardInfo.idx
            }
          });
        }
      })
    },
    // 입력되는 파일의 정보를 파일 변수에 저장
    changeFile({commit}, file) {
      commit('changeFile', file)
    },
    // 댓글 리스트 가져오기 
    getCommentList({commit}, board_idx) {
      axios
        .get("http://localhost:8080/api/board/comment/list", {
          params: { board_idx: board_idx }
        })
        .then(res => {
          if (res.status === 200) commit('getCommentList', res.data)
        })  
    },
    writeComment({dispatch}, commentObj) {
      // 데이터 담아서
      let config = {
        comment_content: commentObj.content,
        comment_board_idx: commentObj.board_idx,
        comment_user_idx: commentObj.user_idx
      };
      axios
        .post("http://localhost:8080/api/board/comment/write", config)
        .then(res => {
          if (res.status === 200) {
            dispatch('getCommentList', commentObj.board_idx)
          }
          

        })
    },
    // 댓글 수정하기
    modifyCommentInfo({dispatch}, commentObj) {
      // 각 댓글마다 폼 validate를 사용할 방법을 모르겠음
      // 공백이면 null 값을 넣어서 강제로 유효성 검사 하게함 
      if(commentObj.modifyContent.trim() == "" || commentObj.modifyContent == null) {
        commentObj.modifyContent = null
        return false    
      }
      // 수정할 데이터 담아서
      let config = {
        comment_idx: commentObj.comment_idx,
        comment_content: commentObj.modifyContent
      };
      axios
        .put("http://localhost:8080/api/board/comment/modify", config)
        .then(res => {
          if (res.status === 200) {
            // 리스트 다시 불러오기
            dispatch("getCommentList", commentObj.comment_board_idx)
            // 수정 폼 닫기
            dispatch("isActiveToggle", commentObj)
          }
        })
    },
    // 댓글 삭제
    deleteCommentInfo({dispatch}, comment) {
      axios
        .delete("http://localhost:8080/api/board/comment/delete", {
          params: { comment_idx: comment.comment_idx }
        })
        .then(res => {
          if (res.status === 200) dispatch("getCommentList", comment.comment_board_idx)
        })
    }
  },
  modules: {
    
  }
})
