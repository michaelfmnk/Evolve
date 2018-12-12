import * as types from 'constants/actionTypes/cards'
import * as endpoints from 'constants/routes/api/cards'

export const createCard = (boardId, columnId, card) => ({
  type: types.CREATE_CARD,
  REQUEST: {
    method: 'POST',
    url: endpoints.cards(boardId, columnId),
    data: card
  }
})



export const moveCard = (boardId, card, targetColumn) => ({
  type: types.MOVE_CARD,
  REQUEST: {
    method: 'PATCH',
    url: `/api/boards/${boardId}/columns/${card.column_id}/cards/${card.id}/move/${targetColumn.id}`
  },
  boardId, 
  card, 
  targetColumn
})

export const updateCard = (boardId, card) => ({
  type: types.UPDATE_CARD,
  REQUEST: {
    method: 'PUT',
    data: card,
    url: `/api/boards/${boardId}/columns/${card.column_id}/cards/${card.id}`
  },
  boardId,
  card
})