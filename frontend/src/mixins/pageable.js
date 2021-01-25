import { objectFilter } from '@/util'

export const pageable = {
  data () {
    return {
      itemsPerPageList: [20, 50, 100, 200],
    }
  },
  methods: {
    buildSearchParam: function (form, options) {
      const filteredForm = objectFilter(form, (o) => {
        if (typeof o === 'number') {
          return o > 0
        }
        if (typeof o === 'boolean') {
          return true
        }
        return !this._.isEmpty(o)
      })
      for (const key in filteredForm) {
        const value = filteredForm[key]
        if (value instanceof Array && value.length > 0) {
          filteredForm[key] = value.join(',')
        }
      }

      const param = {
        ...filteredForm,
        page: options.page - 1,
        size: options.itemsPerPage,
      }

      if (options.sortBy.length > 0) {
        const direction = options.sortDesc[0] ? 'DESC' : 'ASC'
        param.sort = options.sortBy[0] + ',' + direction
      }
      return param
    }
  }
}
