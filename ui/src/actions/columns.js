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