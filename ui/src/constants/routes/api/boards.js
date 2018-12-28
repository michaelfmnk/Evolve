export const boards = '/api/boards'
export const boardById = (id) => `/api/boards/${id}`

export const collaborators = (boardId) => 
  boardById(boardId) + '/collaborators'

export const boardActivities = (boardId) => 
  boardById(boardId) + '/activities'
