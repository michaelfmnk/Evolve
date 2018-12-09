import * as types from 'constants/actionTypes/columns'
import * as endpoints from 'constants/routes/api/columns'

export const createColumn = (boardId, column) => ({
  type: types.CREATE_COLUMN,
  REQUEST: {
    method: 'POST',
    url: endpoints.columns(boardId),
    data: column
  }
})

export const deleteColumn = (boardId, columnId) => ({
  type: types.DELETE_COLUMN,
  REQUEST: {
    method: 'DELETE',
    url: endpoints.columns(boardId) + "/" + columnId,
  },
  columnId,
  boardId
})