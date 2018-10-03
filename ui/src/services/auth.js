import callApi from 'helpers/callApi'
import * as endpoints from 'api/auth'

class AuthService {
  signIn (credentials) {
    return callApi({
      method: 'POST',
      url: endpoints.signIn,
      body: credentials
    })
  }

  signUp (userInfo) {
    return callApi({
      method: 'POST',
      url: endpoints.signUp,
      body: userInfo
    })
  }

  verifyUserAccount ({userId, code}) {
    return callApi({
      method: 'POST',
      url: endpoints.verifyAccount(userId),
      body: { code }
    })
  }
}

export default new AuthService()
