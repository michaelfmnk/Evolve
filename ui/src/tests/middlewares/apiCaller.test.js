import configureApiCallerMiddleware from 'middlewares/apiCaller'
import instance from 'constants/axios/instance'
import configureMockStore, { apiCallerMiddleware } from 'tests/mocks/store'
import moxios from 'moxios'
import { logout } from 'actions/auth'
import {start, success, fail} from 'helpers/actionsProcessTemplaters'

describe('middlewares tests', () => {
  describe('apiCaller redux middleware', () => {

    const store = configureMockStore()
    const next = jest.fn()

    const fakeUsualAction = {
      type: 'ACTION',
      payload: 'string'
    }  

    const fakeRequestAction = {
      type: 'REQUEST ACTION',
      REQUEST: {
        url: 'some url',
        method: 'post',
        data: 'it is a data',
        responseDataConverter: (resData) => ({ ...resData, someText : resData.someText.toUpperCase() }),
      },
      additionalField: 'some extra field'
    }

    const successServerResponse = {
      status: 200,
      response: {
        someText: 'someText',
        title: 'title'
      }
    }

    const errorServerResponse  = {
      status: 400,
      response: {
        errorText: 'error'
      }
    }

    const expectedStartRequestAction = {
      type: start(fakeRequestAction.type),
      additionalField: fakeRequestAction.additionalField,
      payload: fakeRequestAction.REQUEST.data
    }

    const expectedSuccessRequestAction = {
      type: success(fakeRequestAction.type),
      additionalField: fakeRequestAction.additionalField,
      payload: fakeRequestAction.REQUEST.responseDataConverter(successServerResponse.response)
    }

    const expectedErrorRequestAction = {
      type: fail(fakeRequestAction.type),
      payload: {
        status: errorServerResponse.status,
        errorData: errorServerResponse.response
      }
    }

    afterEach(() => {
      next.mockClear()
      store.clearActions()
    }) 

    describe('action object does not have REQUEST field', () => {   

      test('just calls next on this action', () => {
        apiCallerMiddleware(store)(next)(fakeUsualAction)
        expect(next).toHaveBeenCalledTimes(1)
        expect(next).toHaveBeenCalledWith(fakeUsualAction)
        expect(store.getActions()).toEqual([])
      })
    })

    describe('action object have REQUEST field', () => {

      beforeEach(() => moxios.install(instance))
      afterEach(() => moxios.uninstall()) 

      test('removes REQUEST field from action, dispatches new action with start type and payload aka REQUEST.data', () => {
        apiCallerMiddleware(store)(next)(fakeRequestAction)
        expect(next).toHaveBeenCalledTimes(0)
        expect(store.getActions()).toEqual([expectedStartRequestAction])
      })

      test('dispatches start and success actions when server response is ok', async () => {
        moxios.stubRequest(fakeRequestAction.REQUEST.url, successServerResponse)
        await apiCallerMiddleware(store)(next)(fakeRequestAction)
        expect(store.getActions()).toEqual([expectedStartRequestAction, expectedSuccessRequestAction])
      })

      test('dispatches start and fail actions when server response is bad', async () => {
        moxios.stubRequest(fakeRequestAction.REQUEST.url, errorServerResponse)
        await apiCallerMiddleware(store)(next)(fakeRequestAction)
        expect(store.getActions()).toEqual([expectedStartRequestAction, expectedErrorRequestAction])
      })

      test('dispatches logout action if response status is 401', async () => {
        moxios.stubRequest(fakeRequestAction.REQUEST.url, { status: 401, })
        await apiCallerMiddleware(store)(next)(fakeRequestAction)
        expect(store.getActions()[2]).toEqual(logout())
      })

      test('dispatches logout action if response status is 403', async () => {
        moxios.stubRequest(fakeRequestAction.REQUEST.url, { status: 403, })
        await apiCallerMiddleware(store)(next)(fakeRequestAction)
        expect(store.getActions()[2]).toEqual(logout())
      })


      test('correct method and responseDataConverter default values', async () => {
        const action = {
          type: 'ACTION',
          REQUEST: {
            url: 'some get path'
          }
        }

        const stubRequest = {
          status: 200,
          response: {
            someInfo: [1,2,3,4]
          }
        }

        moxios.stubRequest(action.REQUEST.url, stubRequest)

        const expectedActions = [
          {
            type: start(action.type)
          },
          {
            type: success(action.type),
            payload: stubRequest.response
          }
        ]

        await apiCallerMiddleware(store)(next)(action)
        expect(store.getActions()).toEqual(expectedActions)
      })

    })
  })
})