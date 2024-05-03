// 查询列表页接口
const getOrderDetailPage = (params) => {
  return $axios({
    url: '/order/page',
    method: 'get',
    params
  })
}
const getPayOrder = (orderId) => {
  return $axios({
    url: '/alipay/pay',
    method: 'get',
    params: { orderId }
      }
  )
}
