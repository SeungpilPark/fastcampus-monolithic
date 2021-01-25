export const formValidator = {
  data () {
    return {
      regExp: {
        empty: /^\s+$/,
      },
      rules: {
        required: v => v !== undefined && v !== null && v !== '',
        maxLength: (v, maxLength) => !this.rules.required(v) || v.toString().length <= maxLength,
        minLength: (v, minLength) => !this.rules.required(v) || v.toString().length >= minLength,
      }
    }
  },
  methods: {
    async validateForm (formName = 'form') {
      const $form = this.$refs[formName]
      if ($form) {
        return this.$refs[formName].validate()
      }
      return false
    },
    resetFormValidation (formName = 'form') {
      const $form = this.$refs[formName]
      if ($form) {
        this.$refs[formName].resetValidation()
      }
    },
  }
}
