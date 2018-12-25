import configureMockStore from 'redux-mock-store'
import apiCallerMiddleware from 'middlewares/apiCaller'
import instance from 'constants/axios/instance'

const middlewares = [ apiCallerMiddleware(instance) ]
const mockStore = configureMockStore(middlewares)

export default ( initialState = {} ) => mockStore(initialState)