import configureMockStore from 'redux-mock-store'
import configureApiCallerMiddleware from 'middlewares/apiCaller'
import instance from 'constants/axios/instance'

export const apiCallerMiddleware = configureApiCallerMiddleware(instance)

const middlewares = [ apiCallerMiddleware ]
const mockStore = configureMockStore(middlewares)

export default ( initialState = {} ) => mockStore(initialState)