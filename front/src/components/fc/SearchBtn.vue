<template>
  <v-btn
    depressed
    color="primary"
    @click="$emit('onClickSearch')"
  >
    <v-icon left>
      mdi-magnify
    </v-icon>
    {{ name }}
  </v-btn>
</template>

<script>
  export default {
    name: 'SearchBtn',
    props: {
      name: {
        type: String,
        default: '검색'
      }
    }
  }
</script>
