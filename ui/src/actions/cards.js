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
    url: endpoints.moveCard(boardId, card.column_id, card.id, targetColumn.id)
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
    url: endpoints.card(boardId, card.column_id, card.id)
  },
  boardId,
  card
})

export const deleteCard = (boardId, card) => ({
  type: types.DELETE_CARD,
  REQUEST: {
    method: 'DELETE',
    url: endpoints.card(boardId, card.column_id, card.id)
  },
  card
})

export const openCard = (id) => ({
  type: types.OPEN_CARD,
  payload: { id }
})

export const closeCard = () => ({
  type: types.CLOSE_CARD,
})

export const assignUsersToCard = (boardId, card,  userIds) => ({
  type: types.ASSIGN_USERS_TO_CARD,
  REQUEST: {
    method: 'POST',
    data: {
      data: userIds
    },
    url: endpoints.cardAssignees(boardId, card.column_id, card.id)
  }
})



export const unassignUserFromCard = (boardId, card,  userId) => ({
  type: types.UNNASSIGN_USER_FROM_CARD,
  REQUEST: {
    method: 'DELETE',
    url: endpoints.concreteCardAssegnee(boardId, card.column_id, card.id, userId)
  },
  card,
  deletedUserId: userId
})
