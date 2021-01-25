<template>
  <v-app>
    <v-app-bar
        app
        color="blue darken-3"
        dark
        dense
        :clipped-left="$vuetify.breakpoint.lgAndUp"
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title
          style="width: 204px"
          class="ml-0 pl-0"
      >
        <span
            class="hidden-sm-and-down pointer-cursor"
            @click="$router.push({path: '/'})"
        >
          패스트캠퍼스 모빌리티
        </span>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <span class="mt-1 body-2">
        {{session.name}} 님 / {{session.role}}
      </span>
      <v-btn
          text
          class="ml-2"
          @click="logout"
          outlined
      >
        로그아웃
      </v-btn>
    </v-app-bar>

    <v-navigation-drawer
        v-model="drawer"
        :clipped="$vuetify.breakpoint.lgAndUp"
        app
    >
      <v-list dense>
        <v-list-item
            :to="{name: 'drivingList'}"
        >
          <v-list-item-icon class="mr-3">
            <v-icon small>mdi-view-dashboard-outline</v-icon>
          </v-list-item-icon>
          <v-list-item-title>
            <span>운행관리</span>
          </v-list-item-title>
        </v-list-item>
        <v-list-item
            :to="{name: 'vehicleList'}"
        >
          <v-list-item-icon class="mr-3">
            <v-icon small>mdi-view-dashboard-outline</v-icon>
          </v-list-item-icon>
          <v-list-item-title>
            <span>차량관리</span>
          </v-list-item-title>
        </v-list-item>
        <v-list-item
            :to="{name: 'userList'}"
        >
          <v-list-item-icon class="mr-3">
            <v-icon small>mdi-account-outline</v-icon>
          </v-list-item-icon>
          <v-list-item-title>
            <span>사용자관리</span>
          </v-list-item-title>
        </v-list-item>
      </v-list>
      <v-footer
          class="my-4 ml-4"
          absolute
          padless
          style="background-color: white"
          width="90%">
        <span style="font-size: small; color: #6b6a6a">본 사이트는 패스트캠퍼스 <br/> DevOps 과정 예제 어플리케이션 입니다.</span>
      </v-footer>
    </v-navigation-drawer>

    <KeepAliveView/>
  </v-app>
</template>

<script>
  import {mapState} from 'vuex'
  import securityAPI from '@/api/security'

  export default {
    name: 'Index',
    components: {
      KeepAliveView: () => import('@/components/KeepAliveView')
    },
    computed: {
      ...mapState('user', ['session'])
    },
    created() {
    },
    watch: {},
    data() {
      return {
        drawer: true,
      }
    },
    methods: {
      logout() {
        securityAPI.logout()
        .then(() => {
          location.href = '/'
        })
        .catch(error => {
          console.log(error)
          location.href = '/'
        })
      }
    }
  }
</script>
<style lang="sass">
</style>
