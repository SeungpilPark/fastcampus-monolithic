<template>
  <v-container
    id="DrivingList"
    tag="section"
    fluid
    class="py-0"
  >
    <fc-title :text="'운행 관리'" />
    <v-card outlined class="mt-0 mb-2">
      <v-card-text>
        <v-row>
          <v-col cols="12" class="d-flex align-center mb-5 pb-0 pr-0">
            <fc-search-text
              v-model="form.id"
              label="운행ID"
              placeholder="운행ID"
              class="mr-3 max-100"
              @onEnter="onClickSearch"
            />
            <fc-search-select
              v-model="form.status"
              :items="drivingStatus"
              label="운행상태"
              class="mr-3 max-150"
            />
            <fc-search-text
              v-model="form.license"
              label="라이선스"
              placeholder="라이선스"
              class="mr-3 max-150"
              maxlength="100"
              @onEnter="onClickSearch"
            />
            <fc-search-btn
              class="ml-4 mr-4"
              @onClickSearch="onClickSearch"
            />
            <fc-search-reset-btn
              @onClickReset="onClickReset"
            />
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
    <v-row>
      <v-col cols="12" class="d-flex pb-0">
        <v-select
          v-model="searchOptions.itemsPerPage"
          :items="itemsPerPageList"
          label="리스트 수"
          dense
          outlined
          hide-details
          class="max-120"
        />
        <v-spacer />
        <fc-button-add
          class="ml-2 mr-2"
          :title="'운행 등록'"
          @onClick="$refs.DrivingAddDialog.open()"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="pb-0">
        <v-data-table
          :headers="headers"
          :items="items"
          :options.sync="searchOptions"
          :server-items-length="totalItems"
          :loading="loading"
          class="fc"
          no-data-text="검색결과가 없습니다."
          fixed-header
          hide-default-footer
          @page-count="pageCount = $event"
        >
          <template v-slot:top>
            <FcTableCounter v-model="totalItems" />
          </template>
          <template v-if="items.length > 0" v-slot:body="{ items }">
            <tbody>
              <tr v-for="(item, index) in items" :key="item.id">
                <td class="text-center">
                  {{ (searchOptions.page - 1) * searchOptions.itemsPerPage + index + 1 }}
                </td>
                <td
                  @click="$router.push({ name: 'drivingInfo', params: { id: item.id.toString() }})"
                  class="text-center"
                >
                  <a>{{ item.id }}</a>
                </td>
                <td
                  @click="$router.push({ name: 'drivingInfo', params: { id: item.id.toString() }})"
                  class="text-center"
                >
                  <a>{{ item.status }}</a>
                </td>
                <td class="text-center">{{ item.vehicle ? item.vehicle.license : '' }}</td>
                <td class="text-center">{{ item.dispatchAttempts }}</td>
                <td class="text-center">{{ item.dispatchVehicleCount }}</td>
                <td class="text-center">{{ $moment(item.createDate).format('YYYY-MM-DD HH:mm:ss') }}</td>
              </tr>
            </tbody>
          </template>
        </v-data-table>
        <div class="text-center pt-2">
          <v-pagination
            v-show="items.length > 0"
            v-model="searchOptions.page"
            :length="pageCount"
            :total-visible="7"
          />
        </div>
      </v-col>
    </v-row>
    <DrivingAddDialog
      ref="DrivingAddDialog"
      @updated="search"
    ></DrivingAddDialog>
  </v-container>
</template>
<script>

  import drivingAPI from '@/api/driving'
  import { formResettable } from '@/mixins/formResettable'
  import { pageable } from '@/mixins/pageable'
  import { errorHandling } from '@/mixins/errorHandling'
  import { mapState } from 'vuex'
  import _ from 'lodash'

  export default {
    name: 'DrivingList',
    components: {
      DrivingAddDialog: () => import('@/components/dialog/DrivingAddDialog'),
    },
    mixins: [formResettable, pageable, errorHandling],
    data: () => ({
      loading: false,
      searchOptions: {
        page: 1,
        itemsPerPage: 50,
        sortBy: [],
        sortDesc: [],
        groupBy: [],
        groupDesc: [],
        multiSort: false,
        mustSort: false
      },
      form: {
        role: '',
        loginId: '',
        name: ''
      },
      headers: [
        {
          text: '번호',
          value: 'number',
          align: 'center',
          width: 80,
          sortable: false
        },
        {
          text: '운행ID',
          value: 'id',
          align: 'center',
          width: 150,
          sortable: false
        },
        {
          text: '운행상태',
          value: 'status',
          align: 'center',
          sortable: false
        },
        {
          text: '라이선스',
          value: 'license',
          align: 'center',
          sortable: false
        },
        {
          text: '배차요청회차',
          value: 'dispatchAttempts',
          align: 'center',
          width: 120,
          sortable: false
        },
        {
          text: '배차요청차량수',
          value: 'dispatchVehicleCount',
          align: 'center',
          width: 120,
          sortable: false
        },
        {
          text: '등록일',
          value: 'createDate',
          align: 'center',
          sortable: false
        }
      ],
      items: [],
      totalItems: 0,
      pageCount: 0,
    }),
    computed: {
      ...mapState('state', ['drivingStatus'])
    },
    watch: {
      searchOptions: {
        handler () {
          this.debounceSearch(this)
        },
        deep: true
      },
      $route: {
        handler () {
          if (!this._inactive) {
            this.onClickReset()
            this.debounceSearch(this)
          }
        },
        deep: true
      }
    },
    mounted () {
    },
    activated () {
      this.debounceSearch(this)
    },
    methods: {
      debounceSearch: _.debounce((vm) => vm.search(), 50),
      onClickReset () {
        this.reset('form')
      },
      onClickSearch () {
        this.searchOptions.page = 1
        this.debounceSearch(this, this.searchOptions)
      },
      async search () {
        const data = this.buildSearchParam(this.form, this.searchOptions)
        try {
          this.loading = true
          const response = await drivingAPI.search(data)
          this.items = response.content
          this.totalItems = response.totalElements
          this.pageCount = response.totalPages
        } catch (error) {
          this.errorHandle(error)
        } finally {
          this.loading = false
        }
      }
    }
  }
</script>
<style lang="sass">
</style>
