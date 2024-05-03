// 查询列表数据
const getSetmealPage = (params) => {
  return $axios({
    url: '/parking/page',
    method: 'get',
    params
  })
}


const getCategoryList = (params) => {
  return $axios({
    url: '/category/list',
    method: 'get',
    params
  })
}
// 删除数据接口
const deleteParking = (id) => {
  return $axios({
    url: '/parking',
    method: 'delete',
    params: { id }
  })
}

// 修改数据接口
const editParking = (params) => {
  return $axios({
    url: '/parking',
    method: 'put',
    data: { ...params }
  })
}

// 新增数据接口
const addParking = (params) => {
  return $axios({
    url: '/parking',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情接口
const queryParkingById = (id) => {
  return $axios({
    url: `/parking/${id}`,
    method: 'get'
  })
}


// 批量起售禁售
const parkingStatusByStatus = (params) => {
  return $axios({
    url: `/parking/status`,
    method: 'put',
    params: {
      ids: params.ids,
      status: params.status
    }
  });
};
