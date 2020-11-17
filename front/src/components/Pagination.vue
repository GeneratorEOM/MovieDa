<template>
  <div class="text-center mb-15 mt-8">
    <v-pagination v-model="page" :length="pageCnt" :total-visible="visibleCnt" @input="change"></v-pagination>
  </div>
</template>

<script>
export default {
  props: {
    pageCnt: {
      type: Number,
      required: true
    },
    visibleCnt: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      page: 1
    };
  },
  methods: {
    change(pageNum) {
      this.$emit("change", pageNum);
    }
  }
};
</script>

<style>
</style>