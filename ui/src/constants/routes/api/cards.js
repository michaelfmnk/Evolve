export const cards = (boardId, columnId) => 
  `/api/boards/${boardId}/columns/${columnId}/cards`

export const card = (boardId, columnId, cardId) => 
  cards(boardId, columnId) + "/" + cardId

export const moveCard = (boardId, columnId, cardId, targetColumnId) => 
  card(boardId, columnId, cardId) + `/move/${targetColumnId}`

export const cardAssignees = (boardId, columnId, cardId) => 
  card(boardId, columnId, cardId) + '/assignees'

export const concreteCardAssegnee = (boardId, columnId, cardId, userId) => 
  cardAssignees(boardId, columnId, cardId) + '/' + userId