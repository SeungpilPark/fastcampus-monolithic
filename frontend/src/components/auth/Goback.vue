<template>
  <a v-on:click="onCLick">돌아가기</a>
</template>
<script>
  import { mapState } from 'vuex'

  export default {
    name: 'Goback',
    components: {},
    mixins: [],
    props: {},
    computed: {
      ...mapState('user', ['beforeRoutePath']),
    },
    data () {
      return {}
    },
    methods: {
      onCLick () {
        this.$router.push(this.beforeRoutePath)
      }
    }
  }
</script>
