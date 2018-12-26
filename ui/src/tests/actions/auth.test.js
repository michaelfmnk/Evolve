import configureMockStore from 'redux-mock-store'
import apiCallerMiddleware from 'middlewares/apiCaller'
import moxios from 'moxios'
import instance from 'constants/axios/instance'
import * as ednpoints from 'constants/routes/api/auth'
import * as actions from 'actions/auth'
import * as types from 'constants/actionTypes/auth'
import { start, success, fail } from 'helpers/actionsProcessTemplaters'


describe('Action creators tests', () => {
  const middlewares = [ apiCallerMiddleware(instance) ]
  const mockStore = configureMockStore(middlewares)
  const store = mockStore({})

  describe('Auth action creators', () => {
    beforeEach(() => {
      moxios.install(instance);
      store.clearActions();
    });

    afterEach(() => moxios.uninstall());


    test('refreshAuth -- generates correct action object', () => {
      const tokenWithuser = {
        token: 'token',
        user: { id:  1 } 
      }

      const expectedActions = [{
        type: types.REFRESH_AUTH_FROM_STORE,
        payload: tokenWithuser
      }]

      store.dispatch(actions.refreshAuth(tokenWithuser))
        

      expect(store.getActions()).toEqual(expectedActions)
    })

    test('clearAuthError -- generates correct action object', () => {
      const expectedAction = { type: types.CLEAR_AUTH_ERROR }
      store.dispatch(actions.clearAuthError())
      expect(store.getActions()[0]).toEqual(expectedAction)
    })

    test('logout -- generates correct action object', () => {
      const expectedActions = [ { type: types.LOGOUT } ]

      store.dispatch(actions.logout())
      expect(store.getActions()).toEqual(expectedActions)
    })

    test('signIn -- When response is ok, then generates correct start and success actions', async () => {
      moxios.stubRequest(ednpoints.signIn, {
        status: 200,
        response: {
          token: "token",
          user_id: 5
        }
      });

      const expectedActions = [
        {
          shouldRedirect: true,
          type: start( types.SIGN_IN )
        },
        {
          type: success( types.SIGN_IN ),
          shouldRedirect: true,
          payload: {
            token: 'token',
            user: {
              id: 5
            }
          }
        }
      ]

      await store.dispatch(actions.signInRequest())
      expect(store.getActions()).toEqual(expectedActions)
    })
    
    test('signIn -- When response is bad, then generates correct start and fail actions', async () => {
      moxios.stubRequest(ednpoints.signIn, {
        status: 400,
        response: {
          title: 'bad request'
        }
      });

      const expectedActions = [
        {
          shouldRedirect: true,
          type: start( types.SIGN_IN )
        },
        {
          type: fail( types.SIGN_IN ),
          
          payload: {
            errorData: {
              title: 'bad request'
            },
            status: 400
          }
        }
      ]

      await store.dispatch(actions.signInRequest())
      expect(store.getActions()).toEqual(expectedActions)
    })  

    test('signUp -- When response is ok, then generates correct start and success signUp actions', async () => {
      moxios.stubRequest(ednpoints.signUp, {
        status: 200,
        response: {
          "id":45,
          "email":"test2@test.com",
          "first_name":"ASd",
          "last_name":"asd",
          "own_boards":[],
          "joined_boards":[]
        }
      });

      const expectedActions = [
        {
          type: start( types.SIGN_UP )
        },
        {
          type: success( types.SIGN_UP ),
          payload: {
            "email": "test2@test.com",
            "first_name": "ASd",
            "id": 45,
            "joined_boards":  [],
            "last_name": "asd",
            "own_boards": [],
          }
        }
      ]

      await store.dispatch(actions.signUpRequest())
      expect(store.getActions()).toEqual(expectedActions)
    })


    test('signUp -- When response is bad, then generates correct start and fail signUp actions', async () => {
      moxios.stubRequest(ednpoints.signUp, {
        status: 400,
        response: {
          title: 'bad request'
        }
      });

      const expectedActions = [
        {
          type: start( types.SIGN_UP )
        },
        {
          type: fail( types.SIGN_UP ),
          
          payload: {
            errorData: {
              title: 'bad request'
            },
            status: 400
          }
        }
      ]

      await store.dispatch(actions.signUpRequest())
      expect(store.getActions()).toEqual(expectedActions)
    })
    
    
    test('verifyAccountRequest -- When response is ok, then generates correct start and success signUp actions', async () => {
      moxios.stubRequest(ednpoints.verifyAccount(45), {
        status: 200,
        response: {
          user: {
            "email": "test2@test.com",
            "first_name": "ASd",
            "id": 45,
            "joined_boards":  [],
            "last_name": "asd",
            "own_boards": [],
          },
          token: 'token'
        }
      });

      const expectedActions = [
        {
          type: start( types.VERIFY_ACCOUNT ),
          payload: {
            code: 'code',
            userId: 45,
          },
          shouldRedirect: true,
        },
        {
          type: success( types.VERIFY_ACCOUNT ),
          payload: {
            user: {
              "email": "test2@test.com",
              "first_name": "ASd",
              "id": 45,
              "joined_boards":  [],
              "last_name": "asd",
              "own_boards": [],
            },
            token: 'token'            
          },
          shouldRedirect: true,
        }
      ]

      await store.dispatch(actions.verifyAccountRequest(45, 'code'))
      expect(store.getActions()).toEqual(expectedActions)
    })


    test('activateInvitationLink -- When response is ok, then generates correct start and success actions ', () => {

    })


  })
})
