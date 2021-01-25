<template>
  <v-btn
    depressed
    color="primary"
    class="mr-0"
    :disabled="disabled"
    @click="$emit('onClick')"
  >
    {{ title }}
  </v-btn>
</template>

<script>
  export default {
    name: 'ButtonAdd',
    props: {
      title: {
        type: String,
        default: '추가'
      },
      disabled: {
        type: Boolean,
        default: false
      },
    }
  }
</script>
