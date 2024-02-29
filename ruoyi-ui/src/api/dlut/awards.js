import request from '@/utils/request'


// 删除指标管理
export function delAwards(id) {
  return request({
    url: '/awards/delete/' + id,
    method: 'delete'
  })
}

export function editAwards(data) {
  return request({
    url: '/awards/edit',
    method: 'put',
    data: data
  })
}
export function findPage(queryPageBean,sort) {
  return request({
    url: '/awards/findPage/' + sort,
    method: 'post',
    data: queryPageBean
  })
}
export function handleAdd(awards) {
  return request({
    url: '/awards/add',
    method: 'post',
    data: awards
  })
}
export function findById(id) {
  return request({
    url: '/awards/findById/' + id,
    method: 'get'
  })
}
export function findByKind(kind,sort) {
  return request({
    url: '/awards/findByKind/' + kind +'/'+sort,
    method: 'get'
  })
}
export function findByKindUnique(kind,sort) {
  return request({
    url: '/awards/findByKindUnique/'  + kind +'/'+sort,
    method: 'get'
  })
}
