<template>
  <v-container
    id="cancellation"
    tag="section"
    fluid
  >
    <div class="mt-3" @click="pageRefresh">
      <v-icon>mdi-refresh</v-icon>
      Application has been updated. Please refresh browser.
    </div>
  </v-container>
</template>

<script>
  export default {
    name: 'UpdateRefresh',
    data () {
      return {}
    },
    methods: {
      pageRefresh () {
        location.href = window.location.href
      }
    }
  }
</script>
