<template>
  <v-card height="60" flat>
  
    <v-footer shaped absolute class="font-weight-medium" >
      <v-col class="text-center" cols="12">
        {{ new Date().getFullYear() }} —
        <strong>MovieDa</strong>
      </v-col>
    </v-footer>
    
  </v-card>
</template>

<script>
export default {
  data() {
    return {};
  }
};
</script>

<style>
</style>