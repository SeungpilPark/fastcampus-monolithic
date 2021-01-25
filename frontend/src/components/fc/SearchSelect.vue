<template>
  <v-select
    :menu-props="menuProps"
    dense
    hide-details
    outlined
    v-bind="$attrs"
    @change="$emit('input', $event)"
  />
</template>

<script>
  export default {
    name: 'SearchSelect',
    props: {
      menuProps: {
        type: Object,
        required: false,
        default () {
          return {
            offsetY: true
          }
        }
      }
    },
    data () {
      return {}
    },
  }
</script>
