import {startAction, failAction, successAction} from 'helpers/actionsProcessTemplaters'
import * as types from 'actionsTypes/auth'


// SIGN UP
export const signUpRequest = (userInfo) => ({
  type: startAction(types.SIGN_UP),
  payload: userInfo
})

export const signUpSuccess = (user) => ({
  type: successAction(types.SIGN_UP),
  payload: user
})

export const signUpError = (error) => ({
  type: failAction(types.SIGN_UP),
  payload: error
})

export const verifyAccountRequest = (secretCode) => ({
  type: startAction(types.VERIFY_ACCOUNT),
  payload: secretCode
})

export const verifyAccountSuccess = (tokenAndUserId) => ({
  type: successAction(types.VERIFY_ACCOUNT),
  payload: tokenAndUserId
})

export const verifyAccountError = (error) => ({
  type: failAction(types.VERIFY_ACCOUNT),
  payload: error
})

// SIGN IN
export const signInRequest = (userInfo) => ({
  type: startAction(types.SIGN_IN),
  payload: userInfo
})

export const signInSuccess = (authIdentifiers) => ({
  type: successAction(types.SIGN_IN),
  payload: {
    token: authIdentifiers.token,
    user: { id: authIdentifiers.user_id }
  }
})

export const signInError = (error) => ({
  type: failAction(types.SIGN_IN),
  payload: error
})

// FROM STORE
export const refreshAuth = (tokenWithUserIdObj) => ({
  type: types.REFRESH_AUTH_FROM_STORE,
  payload: tokenWithUserIdObj
})

// LOGOUT
export const logout = () => ({
  type: types.LOGOUT
})
