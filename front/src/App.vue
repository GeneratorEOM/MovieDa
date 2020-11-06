<template>
  <v-app id="inspire">
    <Header />
    <router-view/>
    <Footer />
  </v-app>
</template>

<script>
import Footer from "@/views/include/Footer";
import Header from "@/views/include/Header";

export default {
  components: {
    Header,
    Footer
  },
  data: () => ({

  })
};
</script>