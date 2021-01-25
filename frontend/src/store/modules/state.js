const state = {
  alarmType: [],
  userRole: [
    {
      text: '시스템관리',
      value: '시스템관리'
    },
    {
      text: '일반관리',
      value: '일반관리'
    }
  ],
  drivingStatus: [
    {
      text: '배차중',
      value: '배차중'
    },
    {
      text: '배차실패',
      value: '배차실패'
    },
    {
      text: '운행중',
      value: '운행중'
    },
    {
      text: '운행종료',
      value: '운행종료'
    },
    {
      text: '운행중지',
      value: '운행중지'
    },
    {
      text: '운행실패',
      value: '운행실패'
    }
  ],
  booleanCondition: [
    {
      text: '전체',
      value: ''
    },
    {
      text: 'Y',
      value: 'Y'
    },
    {
      text: 'N',
      value: 'N'
    }
  ]
}

export default {
  namespaced: true,
  state,
}
