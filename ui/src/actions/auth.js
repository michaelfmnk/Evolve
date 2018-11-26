import * as types from 'constants/actionTypes/auth'
import * as endpoints from 'constants/routes/api/auth'

export const signUpRequest = (userInfo) => ({
  type: types.SIGN_UP,
  REQUEST: {
    url: endpoints.signUp,
    data: userInfo,
    method: "POST"
  }
})

export const verifyAccountRequest = (userId, code) => ({
  type: types.VERIFY_ACCOUNT,
  REQUEST: {
    url: endpoints.verifyAccount(userId),
    data: { code, userId },
    method: "POST"
  }
})

export const signInRequest = (userInfo) => ({
  type: types.SIGN_IN,
  REQUEST: {
    url: endpoints.signIn,
    data: userInfo,
    method: "POST",
    responseDataConverter: (authIdentifiers) => ({
      token: authIdentifiers.token,
      user: { id: authIdentifiers.user_id }
    })
  }
})

export const refreshAuth = (tokenWithUserIdObj) => ({
  type: types.REFRESH_AUTH_FROM_STORE,
  payload: tokenWithUserIdObj
})

export const logout = () => ({
  type: types.LOGOUT
})
