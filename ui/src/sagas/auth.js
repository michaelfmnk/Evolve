import { takeEvery, put } from 'redux-saga/effects'
import * as actions from 'actions/auth'
import * as actionTypes from 'actionsTypes/auth'
import saveAuthIdentifiersToStorage from 'helpers/saveAuthIdentifiersToStorage'
import AuthService from 'services/auth'

function * signUp (action) {
  try {
    const user = yield AuthService.signUp(action.payload)

    yield put(actions.signUpSuccess(user))
  } catch (e) {
    console.log(e)
    yield put(actions.signUpError(e))
  }
}

function * verifyUserAccount (action) {
  try {
    const authIdentifiers = yield AuthService.verifyUserAccount(action.payload)

    saveAuthIdentifiersToStorage(authIdentifiers)

    yield put(actions.verifyAccountSuccess(authIdentifiers))
  } catch (e) {
    yield put(actions.verifyAccountError(e))
  }
}

function * signIn (action) {
  try {
    const authIdentifiers = yield AuthService.signIn(action.payload)

    saveAuthIdentifiersToStorage(authIdentifiers)

    yield put(actions.signInSuccess(authIdentifiers))
  } catch (e) {
    console.log(e)
    yield put(actions.signUpError(e))
  }
}

export default function * AuthSaga () {
  yield takeEvery(actionTypes.SIGN_UP_REQUEST, signUp)
  yield takeEvery(actionTypes.SIGN_IN_REQUEST, signIn)
  yield takeEvery(actionTypes.VERIFY_ACCOUNT_REQUEST, verifyUserAccount)
}
