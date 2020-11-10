<template>
<div>
        <v-row> 
        <v-col>
          <v-card>
            <v-subheader>한줄평</v-subheader>

            <v-list two-line>
              <template v-for="review in reviews">
                <v-list-item :key="review.movie_id">
                  <v-rating
				  class="mr-5"
            center
            hover
      :value="review.rating"
      background-color="red lighten-2"
      color="red"
      size="20"
	  readonly
    ></v-rating>

                  <v-list-item-content>
                    <v-list-item-title>{{ review.user_id }}</v-list-item-title>

                    <v-list-item-subtitle>{{review.context}}</v-list-item-subtitle>
					<v-list-item-subtitle>{{review.created_at}}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </template>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    <div class="text-center">
            <v-rating
            center
            hover
      v-model="rating"
      background-color="red lighten-2"
      color="red"
      medium
    ></v-rating>
    </div>

    <v-row>

      <v-col cols="2">
        <v-subheader>Name</v-subheader>
      </v-col>
      <v-col cols="10">
        <v-text-field
          label="한줄평을 입력해주세요~"
          v-model="content"
          suffix=""
        ></v-text-field>
      </v-col>
    </v-row>

    <div class="text-center">
        <v-btn @click="createReview">작성</v-btn>
    </div>
</div>
</template>

<script>
import data from '@/data'
export default {
	props: {
		reviews: {
			type: Array,
			required: true
		}
	},
	data() {
		return {
			content: "",    		
			rating: 3
		}
	},
	methods: {
		createReview() {
			const reviewId = data.Review[data.Review.length - 1].review_id + 1;
			data.Review.push({
				review_id: reviewId,
				user_id: 1,
				movie_id: this.id,
				context: this.content,
				created_at: "2019-04-19 14:11:11",
				updated_at: null,
				rating: this.rating
		});

		this.content = "";
		
		this.reviews = data.Review.filter(item => item.movie_id === this.id)
    	}
	}
}
</script>

<style>

</style>