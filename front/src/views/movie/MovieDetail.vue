<template>
  <v-container class="gray lighten-5">
    <v-card class="mx-auto" max-width="535">

        <v-img
          class="mx-auto"
          contain
          width="600"
          height="800"
          :src="'https://image.tmdb.org/t/p/original'+movieInfo.poster_path"
        ></v-img>


      <div>
        <v-card-title>{{movieInfo.title}}</v-card-title>

        <v-card-subtitle>{{movieInfo.original_title}}, {{movieInfo.release_date}}</v-card-subtitle>

        <v-card-subtitle>
          개요:
          <span v-for="(genre, index) in movieInfo.genres" :key="genre.id">{{ genre.name}}
            <span v-if="index < movieInfo.genres.length - 1" :key="index">, </span>
          </span>
          ㅣ 평점: {{movieInfo.vote_average}}
        </v-card-subtitle>

        <v-card-actions>
          <v-btn color="orange lighten-2" text>줄거리 보기</v-btn>

          <v-spacer></v-spacer>

          <v-btn icon @click="show = !show">
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn>
        </v-card-actions>

        <v-expand-transition>
          <div v-show="show">
            <v-divider></v-divider>
            <v-card-text>{{movieInfo.overview == "" ? '없음' : movieInfo.overview}}</v-card-text>
          </div>
        </v-expand-transition>

        
      </div>
      <v-divider></v-divider>
      <MovieReview :movie_id="movie_id" />
    </v-card>
  </v-container>
</template>

<script>
import MovieReview from "@/components/movie/MovieReview";
import { mapState, mapActions } from 'vuex'
export default {
  components: {
    MovieReview
  },
  data() {
    return {
	  movie_id: Number(this.$route.params.id),
      show: false
    };
  },
  computed: {
	  ...mapState(['movieInfo'])
  },
  created() {
	  this.getMoiveInfo(this.movie_id)
  },
  methods: {
	  ...mapActions(['getMoiveInfo'])
  }
}
</script>

<style>
v-img {
  background-size: cover;
}
</style>