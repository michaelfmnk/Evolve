import authReducer , { initialState } from 'reducers/auth'
import * as actions from 'actions/auth'
import * as types from 'constants/actionTypes/auth'
import { start, success, fail } from 'helpers/actionsProcessTemplaters'

describe('reducers tests', () => {
  describe('auth reducer', () => {

    const successSignIn = {
      type: success( types.SIGN_IN ),
      shouldRedirect: true,
      payload: {
        token: 'token',
        user: {
          id: 5
        }
      }
    }

    const failSignIn = {
      type: fail( types.SIGN_IN ),
      
      payload: {
        errorData: {
          title: 'bad request'
        },
        status: 400
      }
    }

    test('initial state is correct', () => {
      expect(authReducer(undefined, {})).toEqual(initialState)
    })

    test('correct value for success sign in action', () => {
      expect(authReducer(undefined, successSignIn)).toEqual(successSignIn.payload)
    })

    test('correct value for fail sign in action', () => {
      expect(authReducer(initialState, failSignIn)).toEqual( {
         ...initialState,
         error: failSignIn.payload 
      })
    })
  })
})