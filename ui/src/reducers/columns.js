import { success } from 'helpers/actionsProcessTemplaters'
import { GET_BOARD_BY_ID }  from 'constants/actionTypes/boards'
import { CREATE_CARD, MOVE_CARD, DELETE_CARD } from 'constants/actionTypes/cards'
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

    case success(types.UPDATE_COLUMN): {
      return {
        ...state,
        [action.payload.id]: {
          ...state[action.payload.id],
          ...action.payload
        }
      }
    }


    case success(types.DELETE_COLUMN): {
      let newState = { ...state }
      delete newState[action.columnId]
      return newState
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

    case success(DELETE_CARD): {
      return {
        ...state,
        [action.card.column_id]: {
          ...state[action.card.column_id],
          cards: state[action.card.column_id].cards.filter( id => id !== action.card.id )
        }
      }
    }

    case success(MOVE_CARD): {
      const { card: {id, column_id }, targetColumn } = action
      if( column_id === targetColumn.id) {
        return {
            ...state,
          [column_id]: {
            ...state[column_id],
            cards: [ ...state[column_id].cards.filter( cardId => cardId !== id), id ]
          },
        }
        
      }
      return {
        ...state,
        [column_id]: {
          ...state[column_id],
          cards: state[column_id].cards.filter( cardId => cardId !== id)
        },

        [targetColumn.id]: {
          ...state[targetColumn.id],
          cards: [ ...state[targetColumn.id].cards, id ]
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

