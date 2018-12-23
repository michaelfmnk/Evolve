export const boardColumnsIds = (state) => {
  const board = state.boards.byId[state.boards.current] 
  console.log('INSIDE SELECTOR')
  console.log(board)
  console.log(state.boards.current)

  return board && board.columns ? board.columns : []
}

export const boardColumns = (state) => {
  const columnsIds = boardColumnsIds(state)

  return columnsIds.map( id => {
      return {
        ...state.columns.byId[id],
        cards: state.columns.byId[id].cards.map(cardId => {
          return state.cards.byId[cardId]
      }) 
    }
  })
}
