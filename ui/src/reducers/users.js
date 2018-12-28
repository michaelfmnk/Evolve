import { success } from 'helpers/actionsProcessTemplaters'
import { combineReducers } from 'redux'
import * as types from 'constants/actionTypes/users'

const initialState = {
  byId: {},
  current: null
}

function byId (state = initialState.byId, action) {
  switch (action.type) {
   case success(types.GET_AUTH_USER_DATA): {
    return action.payload.collaborators
   }

    default: return state
  }
}

function current (state = initialState.current, action) {
  switch (action.type) {
    default: return state
  }
}

export default combineReducers({
  byId,
  current
})
