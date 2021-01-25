<template>
  <v-btn
    outlined
    depressed
    class="mr-0"
    :disabled="disabled"
    @click="$emit('onClick')"
  >
    {{ title }}
  </v-btn>
</template>

<script>
  export default {
    name: 'ButtonInline',
    props: {
      title: {
        type: String,
        default: '인라인동작'
      },
      disabled: {
        type: Boolean,
        default: false
      },
    }
  }
</script>
