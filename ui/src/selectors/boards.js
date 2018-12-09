
const joinedBoardsIds = ({auth}) => (auth.user || {}).joined_boards || []
const ownBoardsIdsIds = ({auth}) => (auth.user || {}).own_boards || []
const userById = ({users}, id) => users.byId[id]

export const boardByIdFromRoute = (state, {match}) => boardById(state, match.params.board_id)

export const boardById = (state, id) => {
  const board = state.boards.byId[id]
  if (!board) return null

  return {
    ...board,
    owner: userById(state, board.owner || board.owner_id),
    collaborators: board.collaborators ? board.collaborators.map(id => userById(state, id)) : []

  }
}

export const authUserBoards = (state) => ({
  ownBoards: ownBoardsIdsIds(state).map(id => boardById(state, id)),
  joinedBoards: joinedBoardsIds(state).map(id => boardById(state, id))
})

export const currentBoardId = ({boards}) => boards.current
