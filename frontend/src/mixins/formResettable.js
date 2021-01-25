export const formResettable = {
  mounted () {
    this.resetForm = { ...this.form }
  },
  data () {
    return {
      resetForm: {}
    }
  },
  methods: {
    reset (form) {
      this[form] = { ...this.resetForm }
    }
  }
}
