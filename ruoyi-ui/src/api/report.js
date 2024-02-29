import request from '@/utils/request'

// 获取用户详细信息
export function getTeacher() {
  return request({
    url: '/user/getTeacher',
    method: 'get'
  })
}
export function getAllTeacher() {
  return request({
    url: '/user/getAllTeacher',
    method: 'get'
  })
}
export function findRank(credit) {
  return request({
    url: '/teacher/findRank/'+ credit,
    method: 'get'
  })
}
export function getPieReport(teacherUid) {
  return request({
    url: '/report/getPieReport/'+ teacherUid,
    method: 'get'
  })
}
export function getTeacherCreditRank() {
  return request({
    url: '/report/getTeacherCreditRank',
    method: 'get'
  })
}
export function getReportData() {
  return request({
    url: '/report/getReportData',
    method: 'get'
  })
}
export function rankExcel() {
  return request({
    url: '/report/rankExcel',
    method: 'get'
  })
}

