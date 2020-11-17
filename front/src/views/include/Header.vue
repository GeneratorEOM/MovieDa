<template>
  <div>
    <v-navigation-drawer v-model="drawer" app>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="title">MovieDa</v-list-item-title>
          <v-list-item-subtitle>:)</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense nav>
        <v-list-item link router :to="{name: 'MovieList'}" exact>
          <v-list-item-icon>
            <v-icon>mdi-view-dashboard</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>메인</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item link router :to="{name: 'BoardList'}" exact>
          <v-list-item-icon>
            <v-icon>mdi-view-dashboard</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>게시판</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <template v-if="isLogin">
          <v-list-item link router :to="{name: 'UserMypage'}" exact>
            <v-list-item-icon>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>마이페이지</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click="$store.dispatch('logout')">
            <v-list-item-icon>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>로그아웃</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>

        <template v-else>
          <v-list-item link router :to="{name: 'UserLogin'}" exact>
            <v-list-item-icon>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>로그인</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{name: 'UserJoin'}" exact>
            <v-list-item-icon>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>회원가입</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>MovieDa</v-toolbar-title>
      <v-spacer></v-spacer>

      <v-menu offset-y v-if="isLogin">
        <template v-slot:activator="{ on, attrs }">
          <v-btn v-bind="attrs" v-on="on" icon>
            <v-icon>more_vert</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item router :to="{name: 'UserMypage'}" exact>
            <v-list-item-title>마이페이지</v-list-item-title>
          </v-list-item>
          <v-list-item @click="$store.dispatch('logout')">
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <template v-else>
        <v-btn color="primary mr-3" router :to="{name: 'UserLogin'}">로그인</v-btn>
        <v-btn color="primary" router :to="{name: 'UserJoin'}">회원가입</v-btn>
      </template>
    </v-app-bar>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      drawer: false,
      showMenu: false
    };
  },
  computed: {
    ...mapState(["isLogin"])
  }
};
</script>

<style>
</style>