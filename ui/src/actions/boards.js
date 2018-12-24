import * as types from 'constants/actionTypes/boards'
import * as endpoints from 'constants/routes/api/boards'
import { board } from 'constants/normalizr_schemas/board'
import { normalize } from 'normalizr'

export const getBoardById = (id) => ({
  type: types.GET_BOARD_BY_ID,
  REQUEST: {
    url: endpoints.boardById(id),
    responseDataConverter: (boardData) => {
      const data = normalize(boardData, board)
      return  {
        boardById: data.entities.board,
        columnsByIds: data.entities.columns,
        cardsByIds: data.entities.cards,
        boardId: data.result
      }
    }
  }
})

export const createBoardRequest = (board) => ({
  type: types.CREATE_BOARD,
  REQUEST: {
    method: 'POST',
    url: endpoints.boards,
    data: board
  }
})

export const inviteCollaborator = (boardId, email) => ({
  type: types.INVITE_COLLABORATOR_TO_BOARD,
  REQUEST: {
    method: 'POST',
    data: {emails : [email]},
    url: endpoints.collaborators(boardId)
  }
})

export const getBoardActivities = (boardId) => ({
  type: types.GET_BOARD_ACTIVITIES,
  REQUEST: {
    url: endpoints.boardActivities(boardId)
  }
})

export const setCurrentBoard = (boardId) => ({
  type: types.SET_CURRENT_BOARD,
  payload: {boardId}
})

