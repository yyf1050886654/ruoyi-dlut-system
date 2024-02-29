import request from '@/utils/request'


// 删除指标管理
export function delTeacher(id) {
  return request({
    url: '/teacher/delete/' + id,
    method: 'delete'
  })
}

export function editTeacher(data) {
  return request({
    url: '/teacher/edit',
    method: 'put',
    data: data
  })
}
export function registerTeacher(roleId,user) {
  return request({
    url: '/user/register/' + roleId,
    method: 'post',
    data: user
  })
}
export function findPage(queryPageBean,roleId) {
  return request({
    url: '/teacher/findPage/' + roleId,
    method: 'post',
    data: queryPageBean
  })
}
export function findById(id) {
  return request({
    url: '/teacher/findById/' + id,
    method: 'get'
  })
}

