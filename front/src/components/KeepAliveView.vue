<template>
  <v-content
    :style="{
    'padding-top':'46px',
    'background' : '#f7f8f9'
  }">
    <keep-alive>
      <div class="pl-3 pr-3" style="height: 100%">
        <router-view />
      </div>
    </keep-alive>
  </v-content>
</template>

<script>
  export default {
    name: 'KeepAliveView',
    components: {},
    data () {
      return {
        excludePages: []
      }
    }
  }
</script>
