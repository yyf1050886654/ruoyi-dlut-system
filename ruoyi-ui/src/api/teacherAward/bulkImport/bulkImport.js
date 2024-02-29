import request from '@/utils/request'


// 删除指标管理
export function bulkDelete(ids,credits,teachers) {
  return request({
    url: '/teacherAward/bulkDelete/' + ids + '/' + credits + '/' + teachers,
    method: 'delete'
  })
}

export function downLoadExcel(checkedSorts,checkedTeachers) {
  return request({
    url: '/teacherAward/downLoadExcel/' + checkedSorts+'/'+checkedTeachers,
    method: 'get'
  })
}


