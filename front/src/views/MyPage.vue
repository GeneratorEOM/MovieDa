<template>
	<h1>{{userInfo.name }}반갑습니다</h1>
</template>
<script>
import { mapState } from 'vuex'
export default {
	computed: {
		...mapState(["userInfo"])
	}
}
</script>