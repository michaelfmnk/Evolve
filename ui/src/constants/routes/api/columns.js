export const columns = (boardId) => 
  `/api/boards/${boardId}/columns`

export const column = (boardId, columnId) => 
  columns(boardId) + '/' + columnId