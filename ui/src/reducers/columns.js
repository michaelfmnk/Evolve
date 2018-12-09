import { start, fail, success } from 'helpers/actionsProcessTemplaters'
import { GET_BOARD_BY_ID }  from 'constants/actionTypes/boards'
import { CREATE_CARD } from 'constants/actionTypes/cards'
import { combineReducers } from 'redux'
import * as types from 'constants/actionTypes/columns'

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

    case success(types.CREATE_COLUMN): {
      return {
        ...state,
        [action.payload.id]: action.payload
      }
    }

    case success(CREATE_CARD): {
      const { id, column_id } = action.payload
      return {
        ...state,
        [column_id]: {
          ...state[column_id],
          cards: [
            ...state[column_id].cards, id
          ]
        }
      }
    }

    case success(types.DELETE_COLUMN): {
      let newState = { ...state }
      delete newState[action.columnId]
      return newState
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

