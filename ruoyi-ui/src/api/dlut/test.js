import request from '@/utils/request'

// 查询指标管理列表
export function listAwards(query) {
  return request({
    url: '/dlut/awards/list',
    method: 'get',
    params: query
  })
}

// 查询指标管理详细
export function getAwards(id) {
  return request({
    url: '/dlut/awards/' + id,
    method: 'get'
  })
}

// 新增指标管理
export function addAwards(data) {
  return request({
    url: '/dlut/awards',
    method: 'post',
    data: data
  })
}

// 修改指标管理
export function updateAwards(data) {
  return request({
    url: '/dlut/awards',
    method: 'put',
    data: data
  })
}

// 删除指标管理
export function delAwards(id) {
  return request({
    url: '/dlut/awards/' + id,
    method: 'delete'
  })
}
