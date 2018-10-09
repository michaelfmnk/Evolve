import { applyMiddleware, compose, createStore } from 'redux'
import createSagaMiddleware from 'redux-saga'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import rootReducer from 'reducers'
import rootSaga from 'sagas'

export default function configureStore (history) {
    const sagaMiddleware = createSagaMiddleware()

    const enhancers = [

    ]

    const middleware = [
        routerMiddleware(history),
        sagaMiddleware
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
