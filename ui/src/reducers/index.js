import { combineReducers } from 'redux'
import auth from 'reducers/auth'
import users from 'reducers/users'
import boards from 'reducers/boards'

export default combineReducers({
  auth,
  users,
  boards
})
