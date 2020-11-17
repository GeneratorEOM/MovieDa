<template>
<v-container class="gray lighten-5">
    <v-row>
      <v-col v-for="movie in movies" :key="movie.id" cols="6" sm="4" md="4" lg="4" xl="2">
        <router-link id="rl" :to="{name: 'MovieDetail', params: {id: movie.id}}" exact>
          <v-card id="card" elevation="2" tile height="350">
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

    <pagination :pageCnt="pageCnt" :visibleCnt="visibleCnt" @change="getMovieList" />
</v-container>
</template>

<script>
import pagination from "@/components/Pagination";
import { mapState, mapActions } from 'vuex'

export default {
  components: {
    pagination
  },
  computed: {
    ...mapState(['movies', 'pageCnt', 'visibleCnt'])
  },
  created() {
    this.getMovieList(1)
  },
  methods: {
    ...mapActions(['getMovieList'])
  }
};
</script>

<style>
#rl {
  text-decoration: none;
}
#card {
  text-decoration: none;
}
#card:hover {
  background-color: lightgray;
}
</style>