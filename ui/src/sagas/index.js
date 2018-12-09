import { fork, all } from 'redux-saga/effects'
import AuthSaga from 'sagas/auth.js'

function * rootSaga () {
  yield all([
    fork(AuthSaga)
  ])
}

export default rootSaga
