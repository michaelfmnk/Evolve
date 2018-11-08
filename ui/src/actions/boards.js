import {failAction, successAction} from 'helpers/actionsProcessTemplaters'
import * as types from 'actionsTypes/boards'

export const getBoardsPreviewsSuccess = (boardsByIds) => ({
  type: successAction(types.GET_BOARDS_PREVIEWS),
  payload: { boardsByIds }
})