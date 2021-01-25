<template>
  <v-row no-gutters class="my-3">
    <v-col cols="12" class="py-0">
      <v-card-title class="pl-0 font-weight-bold">
        {{ text }}
      </v-card-title>
    </v-col>
  </v-row>
</template>

<script>
  export default {
    name: 'Title',
    props: {
      text: {
        type: String,
        default: '',
      },
    }
  }
</script>
