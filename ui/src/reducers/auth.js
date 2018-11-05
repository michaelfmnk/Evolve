import * as types from 'actionsTypes/auth'
import {startAction, failAction, successAction} from 'helpers/actionsProcessTemplaters'
import { GET_AUTHORIZED_USER } from 'actionsTypes/users'

const initialState = {
  user: {},
  token: null
}

export default function authReducer (state = initialState, action) {
  switch (action.type) {
    case types.REFRESH_AUTH_FROM_STORE:
    case successAction(types.SIGN_IN):
      return action.payload

      case successAction(types.SIGN_UP): {
        return {
          user: { id: action.payload.id },
          token: null
        }
      }

      case successAction(types.VERIFY_ACCOUNT): {
        return {
          ...state,
          token: action.payload.token
        }
      }

      case successAction(GET_AUTHORIZED_USER): {
        return {
          ...state,
          user: action.payload
        }
      }

      case types.LOGOUT:
        return { ...initialState }

      default: return state
  }
}
