import { combineReducers } from 'redux'
import auth from 'reducers/auth'
import users from 'reducers/users'
import boards from 'reducers/boards'
import columns from 'reducers/columns'
import cards from 'reducers/cards'
import images from 'reducers/images'


export default combineReducers({
  auth,
  users,
  boards,
  columns,
  cards,
  images
})
