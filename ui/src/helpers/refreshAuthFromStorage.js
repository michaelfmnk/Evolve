
import { refreshAuth } from 'actions/auth'

const refreshAuthFromStorage = (store) => {
  const token = localStorage.getItem('token')
  const userId = localStorage.getItem('userId')

  if (token || userId) {
    store.dispatch(refreshAuth({
      token: token || null,
      userId: userId || null
    }))
  }
}

export default refreshAuthFromStorage
