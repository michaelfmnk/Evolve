import refreshAuthFromStorage from 'helpers/refreshAuthFromStorage'
import configureMockStore from 'tests/mocks/store'
import { REFRESH_AUTH_FROM_STORE } from 'constants/actionTypes/auth'


describe('helpers tests', () => {

  const store = configureMockStore()
  const token = 'some_token'
  const userId = 1
  
  describe('refreshAuthFromStorage', () => {

    afterEach(() => {
      localStorage.getItem = jest.fn( () => null )
      store.clearActions();
    })  

    test('calls refreshAuth when token is present in localStorage', () => {
      localStorage.getItem = jest.fn( (key) => key === 'token' ? token : null )
      const expectedActions = [{
        type: REFRESH_AUTH_FROM_STORE,
        payload: {
          token: token,
          user: { id: null} 
        }
      }]

      refreshAuthFromStorage(store)
      expect(store.getActions()).toEqual(expectedActions)
    })

    test('calls refreshAuth when userId is present in localStorage', () => {
      localStorage.getItem = jest.fn( (key) => key === 'userId' ? userId : null )
      const expectedActions = [{
        type: REFRESH_AUTH_FROM_STORE,
        payload: {
          token: null,
          user: { id: userId} 
        }
      }]

      refreshAuthFromStorage(store)
      expect(store.getActions()).toEqual(expectedActions)   
    })

    test('calls refreshAuth when userId and token is presents in localStorage', () => {
      localStorage.getItem = jest.fn( (key) => key === 'userId' ? userId : key == 'token' ? token : null )

      const expectedActions = [{
        type: REFRESH_AUTH_FROM_STORE,
        payload: {
          token: token,
          user: { id: userId} 
        }
      }]

      refreshAuthFromStorage(store)
      expect(store.getActions()).toEqual(expectedActions)   
    })

    test('Does not call refreshAuth when userId and token is absents in localStorage', () => {      
      refreshAuthFromStorage(store)
      expect(store.getActions()).toEqual([])   
    })

  })
})