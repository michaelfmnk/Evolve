import * as types from 'constants/actionTypes/auth'
import {start, fail, success} from 'helpers/actionsProcessTemplaters'
import { GET_AUTH_USER_DATA } from 'constants/actionTypes/users'
import { CREATE_BOARD } from 'constants/actionTypes/boards'

const initialState = {
  user: {},
  token: null
}

export default function authReducer (state = initialState, action) {
  switch (action.type) {
    case types.REFRESH_AUTH_FROM_STORE:
    case success(types.SIGN_IN): {
      return action.payload
    }

    case success(types.SIGN_UP): {
      return {
        user: { id: action.payload.id },
        token: null
      }
    }

    case success(types.VERIFY_ACCOUNT): {
      return {
        ...state,
        token: action.payload.token
      }
    }

    case success(GET_AUTH_USER_DATA): {
      return {
        ...state,
        user: action.payload.authUser
      }
    }

    case success(CREATE_BOARD): {
      return {
        ...state,
        user: {
          ...state.user,
          own_boards: [ ...state.user.own_boards, action.payload.id ]
        }
      }
    }

    case types.LOGOUT: {
      return { ...initialState }
    }

    default: return state
  }
}
