// 查询列表接口
const getHousePage = (params) => {
  return $axios({
    url: '/house/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteHouse = (id) => {
  return $axios({
    url: '/house',
    method: 'delete',
    params: { id }
  })
}
const addOrder = (id) => {
  return $axios({
    url: '/order',
    method: 'post',
    params: { id }
  })
}
// 修改接口
const editHouse = (params) => {
  return $axios({
    url: '/house',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addHouse = (params) => {
  return $axios({
    url: '/house',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const queryHouseById = (id) => {
  return $axios({
    url: `/house/${id}`,
    method: 'get'
  })
}

// 获取房屋分类列表
const getCategoryList = (params) => {
  return $axios({
    url: '/category/list',
    method: 'get',
    params
  })
}

// 文件down预览
const commonDownload = (params) => {
  return $axios({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口
const houseStatusByStatus = (params) => {
  return $axios({
    url: `/house/status`,
    method: 'put',
    params: {
      ids: params.ids,
      status: params.status
    }
  });
};
