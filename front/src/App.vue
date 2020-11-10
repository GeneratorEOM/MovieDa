<template>
  <v-app id="inspire">
    <Header />
    <v-main>     
        <router-view />
    </v-main>
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
  data: () => ({})
};
</script>