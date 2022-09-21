/*
 * @Author: ldx
 * @Date: 2021-07-08 15:17:34
 * @LastEditTime: 2021-07-14 11:18:27
 * @LastEditors: ldx
 * @Description:需求文档
 * @FilePath: \MS-VUE\src\api\patent\demandList.js
 */

import { axios } from '@/utils/request'
import { getData } from '@/api/utils/getData'

export function getList (params) {
  return getData(axios({
    url: '',
    method: 'get',
    params
  }))
}
export function saveDemand (params) {
  return getData(axios({
    url: '/patentBuying/saveDemand',
    method: 'post',
    data: params
  }))
}

export function upload (params) {
  return getData(axios({
    url: '/serviceLog/upload',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }))
}

export function savePatentSea (params) {
  return getData(axios({
    url: '/patentBuying/savePatentSea',
    method: 'post',
    data: params
  }))
}

export function relatedDemand (params) {
  return getData(axios({
    url: '/patentBuying/relatedDemand',
    method: 'post',
    data: params
  }))
}

export function delPatentSea (params) {
  return getData(axios({
    url: '/patentBuying/delPatentSea',
    method: 'post',
    data: params
  }))
}

export function submitDemand (params) {
  return getData(axios.post('/patentBuying/submitDemand', params))
}
export function buyPatent (params) {
  return getData(axios.post('/patentBuying/buyPatent', params))
}
export function completePurchase (params) {
  return getData(axios.post('/patentBuying/completePurchase', params))
}
export function delDemand (params) {
  return getData(axios.post('/patentBuying/delDemand', params))
}
export function buyBack (params) {
  return getData(axios.post('/patentBuying/buyBack', params))
}
