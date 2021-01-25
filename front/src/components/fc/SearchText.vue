<template>
  <v-text-field
    v-bind="$attrs"
    dense
    :clearable="!readonly"
    :readonly="readonly"
    hide-details
    outlined
    @input="$emit('input', $event)"
    @keyup.native.enter="$emit('onEnter')"
  />
</template>

<script>
  export default {
    name: 'SearchText',
    props: {
      readonly: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {}
    },
  }
</script>
