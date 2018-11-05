import { fork, all } from 'redux-saga/effects'
import AuthSaga from 'sagas/auth.js'
import UsersSaga from 'sagas/users.js'

function * rootSaga () {
  yield all([
    fork(AuthSaga),
    fork(UsersSaga)
  ])
}

export default rootSaga
