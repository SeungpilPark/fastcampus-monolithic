<template>
  <v-btn
    :disabled="disabled"
    class="mr-1"
    color="secondary"
    depressed
    @click="$emit('onClick')"
  >
    {{ title }}
  </v-btn>
</template>

<script>
  export default {
    name: 'ButtonRemove',
    props: {
      title: {
        type: String,
        default: '삭제'
      },
      disabled: {
        type: Boolean,
        default: false
      }
    }
  }
</script>
