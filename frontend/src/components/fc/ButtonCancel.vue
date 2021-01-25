<template>
  <v-btn
    depressed
    color="secondary"
    class="mr-1"
    :disabled="disabled"
    @click="$emit('onClick')"
  >
    {{ title }}
  </v-btn>
</template>

<script>
  export default {
    name: 'ButtonCancel',
    props: {
      title: {
        type: String,
        default: '취소'
      },
      disabled: {
        type: Boolean,
        default: false
      },
    }
  }
</script>
