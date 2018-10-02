import * as types from 'actionsTypes/auth'

// SIGN UP
export const signUpRequest = (userInfo) => ({
  type: types.SIGN_UP_REQUEST,
  payload: userInfo
})

export const signUpSuccess = (user) => ({
  type: types.SIGN_UP_SUCCESS,
  payload: user
})

export const signUpError = (error) => ({
  type: types.SIGN_UP_ERROR,
  payload: error
})

export const verifyAccountRequest = (codeAndUserId) => ({
  type: types.VERIFY_ACCOUNT_REQUEST,
  payload: codeAndUserId
})

export const verifyAccountSuccess = (tokenAndUserId) => ({
  type: types.VERIFY_ACCOUNT_SUCCESS,
  payload: tokenAndUserId
})

export const verifyAccountError = (error) => ({
  type: types.VERIFY_ACCOUNT_ERROR,
  payload: error
})

// SIGN IN
export const signInRequest = (userInfo) => ({
  type: types.SIGN_IN_REQUEST,
  payload: userInfo
})

export const signInSuccess = (authIdentifiers) => ({
  type: types.SIGN_IN_SUCCESS,
  payload: {
    token: authIdentifiers.token,
    userId: authIdentifiers.user_id
  }
})

export const signInError = (error) => ({
  type: types.SIGN_IN_ERROR,
  payload: error
})
