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
    url: endpoints.column(boardId, columnId),
  },
  columnId,
  boardId
})

export const updateColumn = (boardId, column) => ({
  type: types.UPDATE_COLUMN,
  REQUEST: {
    method: 'PUT',
    data: {name : column.name},
    url: endpoints.column(boardId, column.id),
  },
  column,
  boardId
})
