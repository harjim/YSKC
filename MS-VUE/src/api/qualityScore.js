import { axios } from '@/utils/request'
import { message } from 'ant-design-vue'

export const getWeight = async () => {
  const { data } = await axios.get('/qualityScore/getWeights')
  return data
}

export const saveWeight = async ({ form }) => {
  const list = Array.from(form)
  list.forEach(item => {
    item.weight = +item.weight
  })
  const { data } = await axios.post(
    '/qualityScore/saveWeight',
    { list }
  )
  return data
}

export const getScoreConfig = async (params) => {
  const {
    data,
    success,
    errorMessage
  } = await axios.get('/qualityScore/getScoreConfig', { params })
  if (!success) {
    message.error(errorMessage)
    return false
  }
  if (data.engineerList.length) {
    data.engineerList.forEach(item => {
      item.key = item.id
      item.title = item.realName
    })
  }
  if (data.months && data.months.length) {
    data.months.forEach(item => {
      item.months = JSON.parse(item.months)
    })
  }
  return data
}

export const saveScore = async (form) => {
  const {
    success,
    errorMessage
  } = await axios.post('/qualityScore/saveScore', { ...form })
  if (!success) {
    message.error(errorMessage)
    return false
  }
  return true
}

export const editScore = async (params) => {
  const {
    success,
    errorMessage
  } = await axios.post('/qualityScore/editScore', {
    id: params.id,
    scores: params.scores,
    totalScore: params.totalScore,
    isFinal: params.isFinal,
    passed: params.passed,
    month: params.month,
    weight: params.weight
  })
  if (!success) {
    message.error(errorMessage)
    return false
  }
  return true
}

export const getList = async (params) => {
  const {
    data,
    success,
    errorMessage
  } = await axios.get('/qualityScore/getList', { params })
  if (!success) {
    message.error(errorMessage)
    return []
  }
  return data.data
}
