<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row
      no-gutters
      align="center"
      justify="center"
    >
      <v-card class="elevation-12" max-width="400">
        <v-img
          class="mt-3"
          contain
          max-height="48"
          src="/logo.png"
        ></v-img>
        <v-card-text class="pr-12 pl-12 black--text">
          <v-row>
            <v-col cols="12" class="text-center">
              <h2>패스트캠퍼스모빌리티</h2>
            </v-col>
            <v-col
              cols="12"
              class="text-center"
            >
              <v-form
                ref="form"
                v-model="valid"
                :lazy-validation="true"
              >
                <v-text-field
                  autofocus
                  v-model="form.username"
                  :rules="[rules.required, v => rules.maxLength(v, 100)]"
                  dense
                  outlined
                  label="ID"
                  placeholder="아이디를 입력해주세요"
                  @keypress.enter="login"
                />
                <v-text-field
                  type="password"
                  v-model="form.password"
                  :rules="[rules.required, v => rules.maxLength(v, 100)]"
                  dense
                  outlined
                  label="PW"
                  placeholder="비밀번호를 입력해주세요"
                  @keypress.enter="login"
                  hide-details
                />
                <div
                  v-if="failed"
                  class="red--text mb-5 mt-2"
                >
                  <span>로그인에 실패했습니다.</span>
                  <br>
                  <span>정확한 계정 정보를 입력해 주세요.</span>
                </div>
              </v-form>

              <v-btn
                class="mt-6"
                depressed
                color="primary"
                @click="login"
                min-width="200"
                max-width="200"
              >
                로그인
              </v-btn>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-row>
  </v-container>
</template>
<script>
  import securityAPI from '@/api/security'
  import { mapState } from 'vuex'
  import { errorHandling } from '@/mixins/errorHandling'
  import { formValidator } from '@/mixins/formValidator'

  export default {
    name: 'Login',
    components: {},
    mixins: [errorHandling, formValidator],
    props: {
      token: {
        type: [String],
        default: null
      }
    },
    data () {
      return {
        valid: false,
        form: {
          username: null,
          password: null
        },
        failed: false
      }
    },
    computed: {
      ...mapState('user', ['session', 'beforeRoutePath']),
    },
    watch: {},
    mounted () {
    },
    beforeDestroy () {
    },
    methods: {
      async login () {
        try {
          this.validateForm()
          if (!this.valid) {
            return
          }
          const formData = new FormData()
          formData.append('username', this.form.username)
          formData.append('password', this.form.password)
          const response = await securityAPI.login(formData)
          if (!response.authenticated) {
            this.failed = true
          } else {
            this.failed = false
            await this.$router.push(this.beforeRoutePath)
          }
        } catch (error) {
          this.errorHandle(error)
        }
      }
    },
  }
</script>
<style lang="sass">
</style>
