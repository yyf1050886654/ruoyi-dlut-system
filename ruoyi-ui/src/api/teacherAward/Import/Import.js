import request from '@/utils/request'


// 删除指标管理
export function delTeacherAward(id,credit,teacherUid) {
  return request({
    url: '/teacherAward/delete/' + id +'/'+ credit + '/' + teacherUid,
    method: 'delete'
  })
}

export function editTeacherAward(data) {
  return request({
    url: '/teacherAward/edit',
    method: 'put',
    data: data
  })
}
export function findPage4All(queryPageBean,sort) {
  return request({
    url: '/teacherAward/findPage4All/' + sort,
    method: 'post',
    data: queryPageBean
  })
}
export function findById(id) {
  return request({
    url: '/teacherAward/findById/' + id,
    method: 'get'
  })
}
export function addTeacherAward(data,sort) {
  return request({
    url: '/teacherAward/add/' + sort,
    method: 'post',
    data: data
  })
}
export function getInfo(kind,sort) {
  return request({
    url: '/info/getInfo/' + kind +'/'+sort,
    method: 'get',
  })
}

