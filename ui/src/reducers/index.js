import { combineReducers } from 'redux'
import { routerReducer } from 'react-router-redux'
import authReducer from 'reducers/auth'

export default combineReducers({
	rooting: routerReducer,
	auth: authReducer
})
