<template>
<v-container class="gray lighten-5">
    <v-card max-width="900" style="float: left;">
      <v-img
        width="450"
        height="800"
        style="float: left;"
        :src="'https://image.tmdb.org/t/p/original'+movie.poster_path"
      ></v-img>
      <v-img
        width="450"
        height="800"
        :src="'https://image.tmdb.org/t/p/original'+movie.backdrop_path"
      ></v-img>

      <v-card-title style="clear: both;">{{movie.title}}</v-card-title>

      <v-card-subtitle>{{movie.original_title}}, {{movie.release_date}}</v-card-subtitle>

      <v-card-subtitle>
        개요:
        <span v-for="genre in movie.genres" :key="genre.id">{{ genre.name}}</span>
        ㅣ 평점: {{movie.vote_average}}
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
          <v-card-text>{{movie.overview == "" ? '없음' : movie.overview}}</v-card-text>
        </div>
      </v-expand-transition>
      </v-card>

      <div>
    <Review :reviews="reviews"/>
    </div>
</v-container>
</template>

<script>
import data from '@/data'
import axios from "axios";
import Review from "@/components/movie/Review";
export default {
  components: {
    Review
  },
  props: {
    id: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      show: false,
      movie: [],
      reviews: data.Review.filter(item => item.movie_id === this.id),

    };
  },
  created() {
    axios
      .get(
        "https://api.themoviedb.org/3/movie/"+
          this.id+
          "?api_key=4738775b2c15a47761f19db1576589c8"+
          "&language=ko-KR"
      )
      .then(res => {
        this.movie = res.data;
      });
  }
};
</script>

<style>

v-img {
  background-size: contain;
}
</style>