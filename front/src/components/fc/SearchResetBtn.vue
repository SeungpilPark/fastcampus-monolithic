<template>
  <v-btn
    depressed
    color="secondary"
    @click="$emit('onClickReset')"
  >
    <v-icon left>
      mdi-refresh
    </v-icon>
    {{title}}
  </v-btn>
</template>

<script>
  export default {
    name: 'SearchResetBtn',
    props: {
      title: {
        type: String,
        default: '초기화'
      },
    }
  }
</script>
