import { success } from 'helpers/actionsProcessTemplaters'
import * as types from 'constants/actionTypes/images'

export default function (state = [], action) {
  switch(action.type) {
    case success(types.GET_BACKGROUNDS): 
      return action.payload.data

    default: return state
  }
}