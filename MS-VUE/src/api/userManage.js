import api from './index'
import { axios } from '@/utils/request'

export function getDeptTree () {
  return axios({
    url: api.deptTree,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
