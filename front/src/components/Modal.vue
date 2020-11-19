<template>
  <v-flex>
    <v-btn
      color="warning"
      dark
      @click.stop="dialog = true"
    >
      탈퇴
    </v-btn>

    <v-dialog
      v-model="dialog"
      max-width="450"
    >
      <v-card>
        <v-card-title class="pt-8">
          삭제된 회원정보는 복구되지 않습니다
        </v-card-title>
      <v-alert class="mx-5" type="error" :value="isPasswordError" dense>비밀번호가 일치하지 않습니다</v-alert>
      <v-text-field
        class="px-5"
        v-model="password"
        outlined
        clearable
        label="비밀번호"
        type="password"
        required
        dense
      ></v-text-field>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="green darken-1"
            text
            @click="ModalDeleteUserInfo"
          >
            확인
          </v-btn>

          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            취소
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-flex>
</template>

<script>
import { mapState, mapActions } from "vuex"
  export default {
    data () {
      return {
        dialog: false,
        password: "",
        isPasswordError: false
      }
    },
    computed: {
      ...mapState(["userInfo"])
    },
    methods: {
      ...mapActions(["deleteUserInfo"]),

      ModalDeleteUserInfo() {

        if(this.password !== this.userInfo.password) {
          this.isPasswordError = true
          return false
        }

        this.deleteUserInfo(this.userInfo.idx)
        
      }
      
    }
  }
</script>