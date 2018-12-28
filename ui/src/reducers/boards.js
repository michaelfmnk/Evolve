import { success } from 'helpers/actionsProcessTemplaters'
import { GET_AUTH_USER_DATA } from 'constants/actionTypes/users'
import { CREATE_COLUMN, DELETE_COLUMN } from 'constants/actionTypes/columns'
import * as types from 'constants/actionTypes/boards'

const initialState = {
  byId: {},
  current: null
}

export default function (state = initialState, action) {
  switch (action.type) {

    case success(GET_AUTH_USER_DATA): {
      if( (Object.keys(state.byId) > 0) && state.current) {
        return {
          ...state,
          byId: {
            ...action.payload.boards,
            [state.current]: {
              ...action.payload.boards[state.current],
              ...state.byId[state.current]
            }
          }
        }
      }
      return { 
        ...state,
        byId: action.payload.boards
      }
    }

    case success(types.CREATE_BOARD): {
      return {
        ...state,
        byId: {
          ...state.byId,
          [action.payload.id]: action.payload
        }
      }
    }


    case success(types.GET_BOARD_BY_ID): {
      const { boardById, boardId } = action.payload
      return {
        ...state,
        byId: {
          ...state.byId,
          [boardId]: {
            ...state.byId[boardId],
            ...boardById[boardId]
          }
        }
      }
    }

    case success(CREATE_COLUMN): {
      const { id, board_id } = action.payload
      return {
        ...state,
        byId: {
          ...state.byId,
          [board_id]: {
            ...state.byId[board_id],
            columns: [
              ...state.byId[board_id].columns, id
            ]
          }
        }
      }
    }

    case success(DELETE_COLUMN): {
      const { columnId, boardId } = action
      return {
        ...state,
        byId: {
          ...state.byId,
          [boardId]: {
            ...state.byId[boardId],
            columns: state.byId[boardId]
                      .columns
                      .filter( id => id !== columnId)
          }
        }
      }
    }

    case types.SET_CURRENT_BOARD:
      return { 
        ...state,
        current: action.payload.boardId
      }

    default: return state
  }
}

