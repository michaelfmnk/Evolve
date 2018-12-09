import {start, fail, success} from 'helpers/actionsProcessTemplaters'
import {GET_BOARD_BY_ID}  from 'constants/actionTypes/boards'
import { combineReducers } from 'redux'

const initialState = {
  byId: {},
  current: null
}

function byId (state = initialState.byId, action) {
  switch (action.type) {

    case success(GET_BOARD_BY_ID): {
      return {
        ...state,
        ...action.payload.columnsByIds
      }
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

