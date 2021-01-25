<template>
  <v-dialog
    v-model="visible"
    persistent
    max-width="400"
  >
    <v-card>
      <v-card-title class="headline">
        사용자 등록
        <v-spacer />
        <v-icon
          aria-label="Close"
          @click="visible = false"
        >
          mdi-close
        </v-icon>
      </v-card-title>
      <v-card-text>
        <v-form
          ref="form"
          v-model="valid"
          :lazy-validation="true"
        >
          <v-text-field
            v-if="!isUpdate"
            v-model="userInfo.loginId"
            :rules="[rules.required, v => rules.maxLength(v, 20)]"
            dense
            outlined
            label="로그인ID"
          />
          <v-text-field
            v-model="userInfo.loginPassword"
            type="password"
            :rules="isUpdate ? [] : [rules.required, v => rules.maxLength(v, 20)]"
            dense
            outlined
            label="패스워드"
          />
          <v-text-field
            v-model="userInfo.name"
            :rules="[rules.required, v => rules.maxLength(v, 20)]"
            dense
            outlined
            label="이름"
          />
          <v-select
            v-model="userInfo.role"
            :items="userRole"
            outlined
            dense
            color="secondary"
          />
        </v-form>

        <v-row class="mt-3">
          <v-col
            cols="12"
            class="d-flex justify-end pt-0 pb-0">
            <fc-button-add
              :title="'저장'"
              class="mr-3"
              @onClick="update"
            />
            <fc-button-cancel
              @onClick="visible = false"
            />
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>
  import userApi from '@/api/user'
  import { mapState } from 'vuex'
  import { formValidator } from '@/mixins/formValidator'
  import { errorHandling } from '@/mixins/errorHandling'

  export default {
    name: 'UserDialog',
    components: {},
    mixins: [errorHandling, formValidator],
    props: {},
    data () {
      return {
        visible: false,
        valid: false,
        userInfo: this.emptyUserInfo(),
        id: null,
      }
    },
    computed: {
      ...mapState('state', ['userRole']),
      isUpdate () {
        return this.id
      },
    },
    methods: {
      emptyUserInfo () {
        return {
          loginId: null,
          loginPassword: null,
          name: null,
          role: '일반관리'
        }
      },
      open (id) {
        this.id = id
        this.load()
        this.visible = true
      },
      async load () {
        if (!this.isUpdate) {
          this.userInfo = this.emptyUserInfo()
          this.$nextTick(() => {
            this.resetFormValidation()
          })
        } else {
          try {
            this.userInfo = await userApi.get(this.id)
            await this.validateForm()
          } catch (error) {
            this.errorHandle(error)
          }
        }
      },
      async update () {
        try {
          await this.validateForm()
          if (!this.valid) {
            return
          }
          if (this.isUpdate) {
            await userApi.update(this.id, this.userInfo)
            this.$info('사용자 정보가 수정되었습니다.')
            this.$emit('updated')
            this.visible = false
          } else {
            await userApi.add(this.userInfo)
            this.$info('사용자 정보가 등록되었습니다.')
            this.$emit('updated')
            this.visible = false
          }
          await this.load()
        } catch (error) {
          this.errorHandle(error)
        }
      },
    }
  }
</script>
