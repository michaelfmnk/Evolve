import {start, fail, success} from 'helpers/actionsProcessTemplaters'
import {GET_BOARD_BY_ID}  from 'constants/actionTypes/boards'
import * as types from 'constants/actionTypes/cards'
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
        ...action.payload.cardsByIds
      }
    }

    case success(types.CREATE_CARD): {
      return {
        ...state,
        [action.payload.id]: action.payload
      }
    }

    case success(types.MOVE_CARD): {
      const { card: { id }, targetColumn } = action
      return {
        ...state,
        [id]: {
          ...state[id],
          column_id: targetColumn.id
        }
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

