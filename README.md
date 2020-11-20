# MovieDa
> 영화 리뷰 사이트
  
## 개발 환경
**Front-end**
  * HTML5
  * CSS3
  * Vue.js
  * Vuetify
  
**Back-end**
  * Java 11
  * MySQL 5.7

**Tools**
  * Eclipse (Spring Framework 5.0)
  * apache/tomcat9.0
  * SQL developer
  * Github
  
## Vue.js + Spring Framework 연동을 위한 설정
  
* 이클립스 프로젝트 만들기
  
![dy](https://user-images.githubusercontent.com/64389409/99224157-21c10480-2829-11eb-8085-2d5c99092084.PNG)
![ma](https://user-images.githubusercontent.com/64389409/99224156-208fd780-2829-11eb-8a1e-1f121485a35d.PNG)  
  
* Vue.js 설치
  
[node.js](https://nodejs.org/kr/) 에서 npm 을 설치한다.
  

```npm
npm install -g @vue/cli
```
  
Vue 프로젝트를 생성하기 위해 vue-cli 를 설치한다.
    
```
vue create front
```
  
스프링 프로젝트 경로에서 vue 프로젝트를 생성한다. front 는 사용할 프로젝트명이다.
  
<img src="https://user-images.githubusercontent.com/64389409/99527372-09471a80-29e0-11eb-9ff3-5c17544434e0.PNG" width="40%" heigth="30%">
  
#### Vue.js + spring 연동을 위해서는 Vue 서버에서 Proxy 설정이 필요하다.
Vue 와 Spring 은 서버가 다르기 때문에 포트도 두개로 나뉜다.  
그렇기 때문에 vue 서버에 proxy 설정으로 Spring 요청을 Vue 서버에서 받고 Spring 으로 보내주도록 설정해준다.
  
```vue.js
// vue.config.js

const path = require('path')

module.exports = {  
	outputDir: path.resolve(__dirname, "../"+"/WebContent/resources"),
	indexPath: path.resolve(__dirname, "../"+"/WebContent/WEB-INF/views/index.html"),
  devServer: {
	  proxy: {
		  '/api': {
			  target: 'http://localhost:8081',
			  ws: true,
			  changeOrigin: true
		  },
	  }
  }

```
  
먼저 front 폴더 아래에 vue.config.js 를 만들어준다.  
outputDir 는 vue 빌드 되는 경로고 indexPath 는 index.html 이 만들어진 경로다.   
그리고 Vue 포트는 8080 이고 Spring 은 8081 포트를 사용하기로 한다.  
그러면 Vue 에서 localhost:8080/api 으로 url 요청을 보내면 Spring 서버 localhost:8081 로 돌려준다. 

## 핵심 기능
  
### 회원가입
  
Vue
```js
// 회원가입 처리
// 입력받는 가입정보를 가지고 axios.post 요청
  let data = {
    email, password, name, gender
  }
  axios.post('http://localhost:8080/api/user/join', data)
  .then(res => {})
  .catch(err => {})
```
  
Spring
```java
// controller
@PostMapping("/api/user/join")
public int user(@RequestBody UserBean userBean) {
	// service -> DAO -> Mapper 순으로 요청을 처리한다.	
	// 서비스단에서 중복 체크까지
	return userService.addUserInfo(userBean);
}
```
  
```java
// service
public int addUserInfo(UserBean userBean) {
	// 아이디 중복 체크
	boolean isEmailExist = checkUserEmailExist(userBean.getUser_email());
	// 아이디 중복이므로 가입실패 - false
	if(isEmailExist == true) return -1;
	// INSERT 성공하면 1
	int isAddSuccess = userDAO.addUserInfo(userBean);
	if(isAddSuccess > 0) return 1;
	else return 0;
}
```
```java
// mapper sql
@Insert("INSERT INTO user(user_email, user_password, user_name, user_gender, user_date) "
	+ "values(#{user_email}, #{user_password}, #{user_name}, #{user_gender}, now())")
int addUserInfo(UserBean userBean);
	
@Select("SELECT user_email "
	+ "FROM user "
	+ "WHERE user_email = #{user_email}")
String checkUserEmailExist(String email);
```


![회원가입](https://user-images.githubusercontent.com/64389409/99695177-9cf81400-2ad0-11eb-9fd4-e818e83dd0ad.gif)
  
### 로그인

Vue 
```js
// 로그인 액션 처리
let data = { email, password }
axios.post('http://localhost:8080/api/user/login', data)
// 세션에 로그인 처리르 위해 토큰 저장
// 일반적으로 토큰을 받아와서 로컬에 저장하고 유효한지 확인하는 방식으로 로그인 처리를 하는듯 JWT???
// 나중에 해보도록 하고 그냥 토큰이 있는지 없는지만 확인하도록 저장
.then(res => { sessionStorage.setItem("token", res.data) }) 
.catch(err => {})
```
  
라우터 가드를 사용해서 접근권한을 체크했다.
  
```js
// router index.js
const rejectAuthUser = (to, from, next) => {
  if(store.state.isLogin === true) {
    // 이미 로그인 된 유저
    alert("이미 로그인 하셨습니다")
    next("/")
  }else {
    next()
  }
}
const onlyAuthUser = (to, from, next) => {
  if(store.state.isLogin === false) {
    // 로그인 되지 않은 유저
    alert("로그인이 필요합니다")
    next("/")
  }else {
    next()
  }
}

const routes = [
  {
    // beforeEnter : rejectAuthUser 로 인하여 이미 로그인한 유저는 로그인 페이지로 접근할 수 없을 것이다.
    path: '/user/login',
    name: 'UserLogin',
    beforeEnter: rejectAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/user/UserLogin.vue')
  },
  {
    // beforeEnter : onlyAuthUser 로 인하여 로그인 하지 않은 사용자는 글쓰기 페이지로 접근할 수 없을 것이다.
    path: '/board/write',
    name: 'BoardWrite',
    beforeEnter: onlyAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/board/BoardWrite.vue')
  }
]
```
  
Spring
```java
// controller
@PostMapping("/api/user/login")
public UserBean login(@RequestBody UserBean userBean) {	
	// service -> DAO -> Mapper 순으로 요청을 처리한다.
	// 리턴데이터가 없으면 아이디비번이 일치하지 않음
	return userService.getLoginUserInfo(userBean);
}
```
```java
// mapper sql
@Select("SELECT * "
	+ "FROM user "
	+ "WHERE user_email = #{user_email} and user_password = #{user_password}")
UserBean getLoginUserInfo(UserBean userBean);
```

  
![로그인 gif](https://user-images.githubusercontent.com/64389409/99694489-d7ad7c80-2acf-11eb-9e8b-95b32cf996e6.gif) 
  
### 회원수정 및 삭제
    
Vue
```js
// 회원정보 수정 처리
// 회원정보의 비밀번호랑 입력한 비밀번호가 일치하면 axios.put 요청      
let data = { idx, password, modify_name, modify_gender }
      axios.put('http://localhost:8080/api/user/modify', data)
      .then(res => {})
      .catch(err => {})
      
// 회원정보 삭제 처리
// 회원정보의 비밀번호랑 입력한 비밀번호가 일치하면 axios.delete 요청      
axios.delete('http://localhost:8080/api/user/delete', { params: { user_idx: user_idx } })
.then(res => {})
.catch(err => {})
```
  
Spring 
```java
// controller
// 수정
@PutMapping("/api/user/modify")
public boolean modify(@RequestBody UserBean userBean) {
	// service -> DAO -> Mapper 순으로 요청을 처리한다.
	return userService.modifyUserInfo(userBean);
}

// 삭제
@DeleteMapping("/delete")
public boolean delete(@RequestParam(name = "user_idx") int user_idx) {
	// service -> DAO -> Mapper 순으로 요청을 처리한다.
	return userService.deleteUserInfo(user_idx);
}
```
  
```java
// 수정 mapper sql
@Update("UPDATE user "
	+ "SET user_password = #{user_password}, user_name = #{user_name}, user_gender = #{user_gender} "
	+ "WHERE user_email = #{user_email}")
int modifyUserInfo(UserBean userBean);

// 삭제 mapper sql
@Delete("DELETE "
	+ "FROM user "
	+ "WHERE user_idx = #{user_idx}")
int deleteUserInfo(int user_idx);
```

![회원수정_1](https://user-images.githubusercontent.com/64389409/99694481-d5e3b900-2acf-11eb-84ce-65918ce095bf.gif)

### TMDB API 영화 리스트 가져오기

[TMDB](https://www.themoviedb.org/) 에서 회원가입하고 API 키를 받는다.
  
[DOCS](https://developers.themoviedb.org/3/getting-started/introduction) 여기서 영화 정보를 JSON 형태로 가져올 수 있다. URL도 만들어준다. 정말 쉽다!

Vue
  
```js
// 한글 인기순으로 가져오는 쿼리다.
// pageNum 에 페이지번호를 넣으면 20개씩 가져온다.
axios
.get("https://api.themoviedb.org/3/discover/movie?api_key=발급받은API키"
    +"&language=ko-KR"
    +"&sort_by=popularity.desc"
    +"&include_adult=false
    +"&include_video=false
    +"&page=" 
    + pageNum )
.then(res => {
    
 });
```
  
응답결과를 가지고 입맛대로 리스트를 구현할 수 있다.
리스트에는 Spring 과의 통신이 따로 없다.
  
TMDB Server
  
```json
{
  "page": 1,
  "total_results": 10000,
  "total_pages": 500,
  "results": [
    {
      "popularity": 1307.787,
      "vote_count": 209,
      "video": false,
      "poster_path": "/hSpm2mMbkd0hLTRWBz0zolnLAyK.jpg",
      "id": 671039,
      "adult": false,
      "backdrop_path": "/gnf4Cb2rms69QbCnGFJyqwBWsxv.jpg",
      "original_language": "fr",
      "original_title": "Bronx",
      "genre_ids": [
        53,
        28,
        18,
        80
      ],
      "title": "로그 시티",
      "vote_average": 6,
      "overview": "부패경찰과 마르세유의 조폭 사이의 문제로 위기의  경찰조직을 보호해야 한다.",
      "release_date": "2020-10-30"
    },
    {
      "popularity": 1232.799,
      "vote_count": 1416,
      "video": false,
      "poster_path": "/jcJpkQWRU8gsZlo830eQ8ryXKNU.jpg",
      "id": 400160,
      "adult": false,
      "backdrop_path": "/wu1uilmhM4TdluKi2ytfz8gidHf.jpg",
      "original_language": "en",
      "original_title": "The SpongeBob Movie: Sponge on the Run",
      "genre_ids": [
        14,
        16,
        12,
        35,
        10751
      ],
      "title": "스폰지밥 무비: 핑핑이 구출 대작전",
      "vote_average": 8.1,
      "overview": "우리 핑핑이가 어디로 갔을까? 설마, 납치당한 건가? 사랑하는 반려 달팽이를 찾아 떠나는 스폰지밥. 뚱이도 함께 가야지. 비키니 시티를 벗어나 미지의 세계로, 출발이다!",
      "release_date": "2020-08-14"
}

```
  
<img src="https://user-images.githubusercontent.com/64389409/99777896-4b926800-2b56-11eb-9adc-a08545328217.gif" width="56%">


  
### 글쓰기
  
Vue
```js
// 파일처리를 위한 Content-Type: multipart/form-data
var data = new FormData();
  data.append("board_user_idx", idx);
  data.append("board_subject", subject);
  data.append("board_content", content);
  data.append("upload_file", file);
axios
.post("http://localhost:8080/api/board/write", data, {
 headers: { "Content-Type": "multipart/form-data" }
})
.then(res => {})
.catch(err => {});
```
```java
// controller
@PostMapping("/api/board/write")
public int write(BoardBean boardBean) {	
	// service -> DAO -> Mapper 순으로 요청을 처리한다.
	return boardService.writeBoard(boardBean);
}
```
  
Spring
```java
// mapper sql
@Insert("INSERT INTO board(board_idx, board_subject, board_content, "
	+ "board_file, board_date, board_user_idx) "
	+ "VALUES(null, #{board_subject}, #{board_content}, #{board_file}, "
	+ "now(), #{board_user_idx})")
int writeBoard(BoardBean boardBean);
```
![글쓰기](https://user-images.githubusercontent.com/64389409/99694486-d714e600-2acf-11eb-9798-cc622b62940b.gif)

### 글수정 및 삭제
      
Vue
```js
// 게시판 글 수정하기
// 수정 내용을 폼 객체로 전송 multipart/form-data
var data = new FormData();
 config.append("board_idx", idx);
 config.append("board_subject", subject);
 config.append("board_content",content);
 config.append("upload_file", file);
axios
.put("http://localhost:8080/api/board/modify", data, {
   headers: { "Content-Type": "multipart/form-data" }
})
.then(res => {})
.catch(err => {})

// 삭제
axios
.delete("http://localhost:8080/api/board/delete", { params: { board_idx: board_idx } })
.then(res => {})  
.catch(err => {})  
```
  
Spring 
```java
// controller
// 수정
@PutMapping("/api/board/modify")
public void modify(BoardBean boardBean) {
	// service -> DAO -> Mapper 순으로 요청을 처리한다.
	boardService.modifyBoard(boardBean);
}

// 삭제
@DeleteMapping("/api/board//delete")
public void delete(@RequestParam(value = "board_idx") int board_idx) {
	// service -> DAO -> Mapper 순으로 요청을 처리한다.
	boardService.deleteBoard(board_idx);
}
```
  
```java
// mapper sql
// 수정
@Update("UPDATE board SET board_subject = #{board_subject}, "
	+ "board_content = #{board_content}, board_file = #{board_file} "
	+ "WHERE board_idx = #{board_idx}")
void modifyBoard(BoardBean boardBean);

// 삭제
@Delete("DELETE "
	+ "FROM board "
	+ "WHERE board_idx = #{board_idx}")
void deleteBoard(int board_idx);
```
![글수정삭제](https://user-images.githubusercontent.com/64389409/99694484-d67c4f80-2acf-11eb-8813-fd292445a692.gif)

### 댓글수정 및 삭제

기본적인 수정과 삭제는 게시판 글과 같다.  
다른점은 여러개의 댓글에서 하나를 선택해서 수정할 수 있어야하기 때문에   
각각의 댓글에 폼을 토글할 수 있는 active 와 수정할 content 를 map 으로 추가한다.  
  
Vue
```js
// 댓글 리스트 가져오기
axios
.get("http://localhost:8080/api/board/comment/list", { params: { board_idx: board_idx } })
.then(comments => {
	// active 와 modifyConent 를 추가한다.
	comments = comments.map(comment => ({
	...comment,
	// 수정 폼 액티브
	isActive: false,
	// 수정 내용
	modifyContent : ""
}));
})  
.catch(res => {})  
```
```js
  
```json
[
  {
	"comment_idx": 1,
	"comment_content": "내용",
	"isActive": false,
	"modifyContent" : "수정내용"
	
  },
  {
	"comment_idx": 2,
	"comment_content": "내용",	
	"isActive": false,
	"modifyContent" : "수정내용"
  }
]
```
<img src="https://user-images.githubusercontent.com/64389409/99762893-de260d80-2b3c-11eb-8c2d-5cecc4dbe34e.gif" width="56%">

### 영화 한줄평

기본적인 쓰기, 수정, 삭제는 게시판 글과 같다.
여기서도 수정을 위해 댓글과 같이 map 을 사용한다.
  
Vue
```js
// 리뷰 리스트 가져오기
axios
.get("http://localhost:8080/api/movie/review/list", { params: { review_idx: review_idx } })
.then(reviews => {
	// active 와 modifyConent 를 추가한다.
	reviews = reviews.map(review => ({
	...review,
	// 수정 폼 액티브
	isActive: false,
	// 수정 내용
	modifyContent : "",
	// 수정 별점
	modifyRating: 3,
}));
})  
.catch(res => {})  
```
```js
  
```json
[
  {
	"review_idx": 1,
	"review_content": "내용",
	"isActive": false,
	"modifyContent": "수정내용",
	"modifyRating": 3
	
  },
  {
	"review_idx": 2,
	"review_content": "내용",	
	"isActive": false,
	"modifyContent" : "수정내용",
	"modifyRating": 3
  }
]
```
<img src="https://user-images.githubusercontent.com/64389409/99762897-dfefd100-2b3c-11eb-9f86-99d355d730cc.gif" width="56%">


  






  

  

