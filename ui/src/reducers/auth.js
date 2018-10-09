import * as types from 'actionsTypes/auth'

const initialState = {
  userId: null,
  token: null
}

export default function authReducer (state = initialState, action) {
  switch (action.type) {
    
    case types.REFRESH_AUTH_FROM_STORE:
    case types.SIGN_IN_SUCCESS:
      return action.payload

      case types.SIGN_UP_SUCCESS: {
        return {
          userId: action.payload.id,
          token: null
        }
      }

      case types.VERIFY_ACCOUNT_SUCCESS: {
        return {
          ...state,
          token: action.payload.token
        }
      }

        
      default: return state
  }
}
