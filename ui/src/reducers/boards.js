import {start, fail, success} from 'helpers/actionsProcessTemplaters'
import { combineReducers } from 'redux'
import { GET_AUTH_USER_DATA } from 'constants/actionTypes/users'
import * as types from 'constants/actionTypes/boards'


const initialState = {
  byId: {},
  current: null
}

function byId(state = initialState.byId, action){
  switch(action.type){
    case success(GET_AUTH_USER_DATA): {
      return action.payload.boards
    }      

    default: return state
  }
}

function current(state = initialState.current, action){
  switch(action.type){
    default: return state
  }
}

export default combineReducers({
  byId,
  current
})
