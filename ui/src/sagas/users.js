import { takeEvery, put } from 'redux-saga/effects'
import * as actions from 'actions/users'
import * as actionTypes from 'actionsTypes/users'
import { getBoardsPreviewsSuccess } from 'actions/boards'
import { userWithBoards } from 'constants/normalizr_schemas/userWithBoards'
import {startAction} from 'helpers/actionsProcessTemplaters'
import UsersService from 'services/users'
import { normalize } from 'normalizr';

function * getUser (action) {
  try {
    const user = yield UsersService.getUser(action.payload.id)

    if(action.payload.isAuthorizedUser){
      const data = normalize(user, userWithBoards)
      yield put(actions.getAuthorizedUserSuccess(data.entities.authUser[data.result]))
      yield put(actions.getUsersPreviewsSuccess(data.entities.users))
      yield put(getBoardsPreviewsSuccess(data.entities.boards))
    } else {
      yield put(actions.getUserSuccess(user))
    }
  } catch (e) {
    console.log(e)
    yield put(actions.getUserError(e))
  }
}

export default function * AuthSaga () {
  yield takeEvery( startAction(actionTypes.GET_USER), getUser ) 
}
