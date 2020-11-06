<template>
  <h1>Detail = {{id}}</h1>
</template>

<script>
export default {
	data() {
		return {
			id: this.$route.id
		}
	}
}
</script>

<style>

</style>