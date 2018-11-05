import { takeEvery, put, select } from 'redux-saga/effects'
import { push } from 'connected-react-router'
import * as actions from 'actions/auth'
import * as actionTypes from 'actionsTypes/auth'
import { userIdSelector } from 'selectors/auth'
import saveAuthIdentifiersToStorage from 'helpers/saveAuthIdentifiersToStorage'
import {startAction} from 'helpers/actionsProcessTemplaters'
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
    const userId = yield select(userIdSelector)
    const authIdentifiers = yield AuthService.verifyUserAccount({ code: action.payload, userId })

    saveAuthIdentifiersToStorage(authIdentifiers)

    yield put(actions.verifyAccountSuccess(authIdentifiers))
    yield put(push('/home'))
  } catch (e) {
    console.log(e)
    yield put(actions.verifyAccountError(e))
  }
}

function * signIn (action) {
  try {
    const authIdentifiers = yield AuthService.signIn(action.payload)

    saveAuthIdentifiersToStorage(authIdentifiers)

    yield put(actions.signInSuccess(authIdentifiers))
    yield put(push('/home'))
  } catch (e) {
    console.log(e)
    yield put(actions.signUpError(e))
  }
}

function * logout (action) {
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
}

export default function * AuthSaga () {
  yield takeEvery( startAction(actionTypes.SIGN_UP), signUp ) 
  yield takeEvery( startAction(actionTypes.SIGN_IN), signIn )
  yield takeEvery( startAction(actionTypes.VERIFY_ACCOUNT), verifyUserAccount )
  yield takeEvery( actionTypes.LOGOUT, logout )
}
