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