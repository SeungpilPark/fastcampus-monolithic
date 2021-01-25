<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row
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
        <v-card-text
          class="pr-12 pl-12 black--text"
        >
          <v-row>
            <v-col cols="12" class="text-center">
              <h2>패스트캠퍼스모빌리티</h2>
              <h3>접근 금지 안내</h3>
            </v-col>
            <v-divider />
            <v-col
              cols="12"
              class="text-center">
              <p>
                <span>해당 페이지에 접근하실 권한이 없습니다.</span>
              </p>
              <p class="mt-3">
                <Goback />
              </p>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-row>
  </v-container>
</template>
<script>
  import { mapState } from 'vuex'

  export default {
    name: 'Deny',
    components: {
      Goback: () => import('@/components/auth/Goback'),
    },
    mixins: [],
    data () {
      return {}
    },
    mounted () {
    },
    computed: {
      ...mapState('user', ['loginInformation'])
    },
    watch: {},
    methods: {},
  }
</script>
<style lang="sass">
</style>
