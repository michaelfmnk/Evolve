import { takeEvery, put, select } from 'redux-saga/effects'
import axiosInstance from 'constants/axios/instance'
import { push } from 'connected-react-router'
import { saveAuthIdentifiersToStorage, clearLocalStorage } from 'helpers/localStorage'
import { success } from 'helpers/actionsProcessTemplaters'
import { currentPathSelector } from 'selectors/router'
import { welcome, signIn, signUp, home } from 'constants/routes/ui'
import * as types from 'constants/actionTypes/auth'

function * authHandler (action) {
  const { token, user } = action.payload
  saveAuthIdentifiersToStorage(token, user.id)

  axiosInstance.defaults.headers.Authorization = token

  yield put(push(home))
}

function * signUpHandler (action) {
  yield saveAuthIdentifiersToStorage(null, action.payload.id)
}

function * logout (action) {
  clearLocalStorage()
  const currentPath = yield select(currentPathSelector)
  if ([signIn, signUp ].every(path => currentPath !== path)) {
    yield put(push(welcome))
  }
}

export default function * AuthSaga () {
  yield takeEvery(success(types.SIGN_UP), signUpHandler)
  yield takeEvery([ success(types.VERIFY_ACCOUNT), success(types.SIGN_IN)], authHandler)
  yield takeEvery(types.LOGOUT, logout)
}
