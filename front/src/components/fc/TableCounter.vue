<template>
  <v-sheet
    class="pa-3 base-table-counter"
  >
    <span class="subtitle-2">총 {{ value }} {{ unit }}</span>
    <span v-if="text" class="subtitle-2"> / </span>
    <span v-if="text" class="subtitle-2"> {{text}} </span>
  </v-sheet>
</template>
<script>
  export default {
    name: 'TableCounter',
    props: {
      value: {
        type: Number,
        default: 0,
      },
      unit: {
        type: String,
        default: '개'
      },
      text: {
        type: String,
        default: null
      }
    }
  }
</script>
<style lang="sass" scoped>
  .base-table-counter
    background: #dadada
    border-radius: unset
    border-top-left-radius: 4px
    border-top-right-radius: 4px
    border-top: solid 1px #ddd
    border-left: solid 1px #ddd
    border-right: solid 1px #ddd
</style>
