import {start, fail, success} from 'helpers/actionsProcessTemplaters'
import { combineReducers } from 'redux'
import { GET_AUTH_USER_DATA } from 'constants/actionTypes/users'
import * as types from 'constants/actionTypes/boards'

const initialState = {
  byId: {},
  current: null
}

function byId (state = initialState.byId, action) {
  switch (action.type) {
    case success(GET_AUTH_USER_DATA): {
      return action.payload.boards
    }

    case success(types.GET_BOARD_BY_ID): {
      return {
        ...state,
        [action.payload.boardId]: {
          ...state[action.payload.boardId],
          ...action.payload.boardById
        }
      }
    }

    default: return state
  }
}

function current (state = initialState.current, action) {
  switch (action.type) {
    case types.SET_CURRENT_BOARD:
      return action.payload.boardId
      
    default: return state
  }
}

export default combineReducers({
  byId,
  current
})
