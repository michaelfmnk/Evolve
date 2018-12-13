import {start, fail, success} from 'helpers/actionsProcessTemplaters'
import {GET_BOARD_BY_ID}  from 'constants/actionTypes/boards'
import * as types from 'constants/actionTypes/cards'
import { combineReducers } from 'redux'

const initialState = {
  byId: {},
  opened: null
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

    case success(types.UPDATE_CARD): {
      return {
        ...state,
        [action.payload.id]: {
          ...state[action.payload.id],
          ...action.payload
        }
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

    case success(types.ASSIGN_USERS_TO_CARD): {
      return {
        ...state,
        [action.payload.id]: {
          ...state[action.payload.id],
          users: [
            ...action.payload.users
          ]
        }
      }
    }

    case success(types.UNNASSIGN_USER_FROM_CARD): {
      const { deletedUserId , card } = action;
      return {
        ...state,
        [card.id]: {
          ...state[card.id],
          users: state[card.id]
                 .users
                 .filter( user => user.id !== deletedUserId)
        }
      }
    }

    default: return state
  }
}

function opened (state = initialState.opened, action) {
  switch (action.type) {
    case types.OPEN_CARD: 
      return action.payload.id
    
    case types.CLOSE_CARD:
      return null

    default: return state
  }
}

export default combineReducers({
  byId,
  opened
})

