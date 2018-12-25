import { successActionWithType, failActionWithType } from 'helpers/actionsProcessTemplaters'
import { logout } from 'actions/auth'
import { start } from '../helpers/actionsProcessTemplaters'

const clearREQUESTfield = (action) => {
  let res = {
    ...action,
    type: start(action.type)
  }

  if(action.REQUEST.data) { 
    res.payload = { ...action.REQUEST.data } 
  }

  delete res.REQUEST
  return res
}

const apiCaller = (axiosInstance) => store => next => action => {
  const request = action.REQUEST

  if (!request) return next(action)

  const { 
    responseDataConverter = (data) => data,
    url, 
    method = 'GET', 
    data, 
  } = request

  const type = action.type
  const processedAction = clearREQUESTfield(action)

  store.dispatch(processedAction)

  return axiosInstance.request({ url, method, data })
    .then(res => {
      const data = responseDataConverter(res.data)
      store.dispatch( { ...processedAction,  ...successActionWithType(type, data) })
    })
    .catch(err => {
      // console.error(err)
      const { data, status } = err.response
      store.dispatch(failActionWithType(type, { errorData: data, status }))

      switch (status) {
        case 401:
        case 403: {
          store.dispatch(logout())
          break
        }
        default: break
      }
    })
}

export default apiCaller
