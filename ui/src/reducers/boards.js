import {startAction, failAction, successAction} from 'helpers/actionsProcessTemplaters'
import { combineReducers } from 'redux'
import * as types from 'actionsTypes/boards'


const initialState = {
  byId: {},
  current: null
}

function byId(state = initialState.byId, action){
  switch(action.type){
    case successAction(types.GET_BOARDS_PREVIEWS): {
      return {
        ...state,
        ...action.payload.boardsByIds
      }
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
