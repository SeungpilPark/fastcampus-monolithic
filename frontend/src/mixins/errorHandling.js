export const errorHandling = {
  methods: {
    errorHandle: function (error) {
      console.log(error.response)
      if (error.response && error.response.data && error.response.data.message) {
        this.$fail(error.response.data.message)
        return
      }
      this.$fail('시스템 오류입니다')
    }
  }
}
