import { applyMiddleware, compose, createStore } from 'redux'
import createSagaMiddleware from 'redux-saga'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import apiCaller from 'middlewares/apiCaller'
import axiosInstance from 'constants/axios/instance'
import rootReducer from 'reducers'
import rootSaga from 'sagas'
import History from '../history'

function configureStore (history) {
    const sagaMiddleware = createSagaMiddleware()

    const enhancers = [

    ]

    const middleware = [
        routerMiddleware(history),
        sagaMiddleware,
        apiCaller(axiosInstance)
    ]

    const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose

    const composedEnhancers = composeEnhancers(
        applyMiddleware(...middleware),
        ...enhancers
    )

    const store = createStore(
        connectRouter(history)(rootReducer),
        composedEnhancers
    )

    sagaMiddleware.run(rootSaga)
    return store
}

const store = configureStore(History)

export default store
