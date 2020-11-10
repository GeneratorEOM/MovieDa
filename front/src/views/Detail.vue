<template>
<div>
  <MovieDetail :id="id"/>
</div>
</template>

<script>
import MovieDetail from '@/components/movie/MovieDetail'
export default {
	components: {
		MovieDetail
	},
	data() {
		return {
			id: this.$route.params.id
		}
	}
}
</script>

<style>

</style>