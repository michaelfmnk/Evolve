import axiosInstance from 'constants/axios/instance'
import { successActionWithType, failActionWithType } from 'helpers/actionsProcessTemplaters'
import { logout } from 'actions/auth'
import { start } from '../helpers/actionsProcessTemplaters'

const startAction = (action) => {
  let res = {
    ...action,
    payload: action.REQUEST.data || null,
    type: start(action.type)
  }

  delete res.REQUEST
  return res
}

const apiCaller = store => next => action => {
  const request = action.REQUEST

  if (!request) return next(action)

  const { url, method = 'GET', data, responseDataConverter = (data) => data} = request
  const type = action.type

  store.dispatch(startAction(action))

  axiosInstance.request({ url, method, data })
    .then(res => {
      const data = responseDataConverter(res.data)
      store.dispatch(successActionWithType(type, data))
    })
    .catch(err => {
      console.error(err)
      const { data, status } = err.response
      store.dispatch(failActionWithType(type, { errorData: data, status }))

      switch (status) {
        case 401:
        case 403: {
          store.dispatch(logout())
          break
        }
      }
    })
}

export default apiCaller
