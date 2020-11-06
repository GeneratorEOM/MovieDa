<template>
  <div>
    <v-main>
      <v-container class="gray lighten-5">
        <v-row>
          <v-col v-for="movie in movies" :key="movie.id" cols="6" sm="4" md="4" lg="3" xl="2">
            <router-link id="rl" :to="{name: 'Detail', params: {id: movie.id}}" exact>
              <v-card
                id="card"
                class="pa-2"
                elevation="2"
                tile
                height="350"
                style="hover: red;"
                hover
              >
                <v-img
                  contain
                  height="250"
                  :src="'https://image.tmdb.org/t/p/original'+movie.poster_path"
                ></v-img>
                <v-card-title class="justify-center">{{movie.title}}</v-card-title>
              </v-card>
            </router-link>
          </v-col>
        </v-row>
      </v-container>
    </v-main>

    <pagination :pageCnt="pageCnt" :visibleCnt="visibleCnt" @changePage="changePage" />
  </div>
</template>

<script>
import axios from "axios";
import pagination from "@/components/movie/Pagination";

export default {
  components: {
    pagination
  },
  data() {
    return {
      movies: [],
      pageCnt: null,
      visibleCnt: 7
    };
  },
  created() {
    axios
      .get(
        "https://api.themoviedb.org/3/discover/movie?api_key=4738775b2c15a47761f19db1576589c8&language=ko-KR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1"
      )
      .then(res => {
        this.movies = res.data.results;
        this.pageCnt = res.data.total_pages;
      });
  },
  methods: {
    changePage(pageNum) {
      axios
        .get(
          "https://api.themoviedb.org/3/discover/movie?api_key=4738775b2c15a47761f19db1576589c8&language=ko-KR&sort_by=popularity.desc&include_adult=false&include_video=false&page=" +
            pageNum
        )
        .then(res => {
          this.movies = res.data.results;
        });
    }
  }
};
</script>

<style>
#rl {
  text-decoration: none;
}
.router-link-active {
  color: black;
  background-color: black;
  text-decoration: none;
}

.router-link-exact-active {
  color: black;
  background-color: black;
  text-decoration: none;
}
#card {
	text-decoration: none;
}
#card:hover {
  background-color: lightgray;
}
</style>