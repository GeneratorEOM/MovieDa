<template>
  <div no-gutters class="pb-10">

<template v-for="(review, index) in reviews">
      <v-list-item :key="index+'list'">
        <v-list-item-content>
          
                              <v-rating
                  hover
                  v-model="review.review_rating"
                  background-color="red lighten-2"
                  color="red"
                  size="18"
                  readonly
                ></v-rating>
<v-list-item-title class="mb-3">{{review.review_writer}}</v-list-item-title>
          <v-list-item-subtitle class="mb-3" v-text="review.review_content"></v-list-item-subtitle>

          <v-list-item-subtitle v-text="review.review_date"></v-list-item-subtitle>
        </v-list-item-content>

        <template v-if="userInfo != null ? true : false">
          <v-list-item-action v-if="review.review_user_idx === userInfo.idx ">
            <v-flex>
              <v-btn
                class="mr-1"
                color="primary"
                depressed
                small
                @click="review.isActive ? modifyReview(review) : isActiveToggle(review)"
              >{{review.isActive ? '완료' : '수정'}}</v-btn>
              <v-btn
                color="primary"
                depressed
                small
                @click="review.isActive ? isActiveToggle(review) : deleteReview(review)"
              >{{review.isActive ? '취소' : '삭제'}}</v-btn>
            </v-flex>
          </v-list-item-action>
        </template>
      </v-list-item>

       <v-rating
       class="text-center"
       v-if="review.isActive"
                  hover
                  v-model="review.modifyRating"
                  background-color="red lighten-2"
                  color="red"
                  medium
          
                  :key="index + 'rating'"
                ></v-rating>
      <v-text-field
        v-if="review.isActive"
        v-model="review.modifyContent"
        outlined
        clearable
        label="수정 내용"
        :rules="contentRules"
        type="text"
        :key="index + 'text'"
        required
      ></v-text-field>


      <v-divider v-if="index < reviews.length - 1" :key="index + 'divider'"></v-divider>
    </template>
    
        <template v-if="userInfo != null ? true : false">
          <v-form ref="form" v-model="valid" lazy-validation>
          <div class="text-center">
            <v-rating center hover v-model="rating" background-color="red lighten-2" color="red" medium></v-rating>
          </div>
          <v-list-item>      
            <v-header style="width: 100px">{{userInfo.name}}</v-header>
                
          <v-text-field label="한줄평" :rules="contentRules" v-model="content" required></v-text-field>
            <v-btn :disabled="!valid" class="ml-3" color="primary" @click="validate" small>작성</v-btn>
            
          </v-list-item>
          </v-form>
        </template>
    




  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  props: {
    movie_id: {
      type: Number,
      required: true
    },
    
  },
  data() {
    return {
      valid: true,
      content: "",
      contentRules: [v => !!v.trim() || "내용을 입력해주세요"],
      
    };
  },
  computed: {
    ...mapState(["userInfo", "reviews", "rating"])
  },
  created() {
    this.getReviewList(this.movie_id)
  },
  methods: {
    ...mapActions(["getReviewList", "writeReview", "modifyReview", "isActiveToggle", "deleteReview"]),

    validate() {

      if(!this.$refs.form.validate()) return false
      this.writeReview({
        review_movie_id: this.movie_id,
        review_user_idx: this.userInfo.idx,
        review_content: this.content,
        review_rating: this.rating,
        movie_id: this.movie_id
      })
      this.$refs.form.reset()
    }
  }
};
</script>

<style>
</style>