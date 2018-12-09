export const boardColumnsIds = (state) => {
  return columnsIds = state.boards.byId[state.boards.current].culumns || []
}

export const boardColumnsAndCards = (state) => {
  const columnsIds = boardColumnsIds(state)

  const res = {
    columns: [],
    cards: []
  }

  columnsIds.forEach( id => {
    let col = columns.push( state.columns.byId[id]);
    if( col.cards ) {
      col.cards.forEach( cardId => {
        cards.push(state.cards.byId[cardId])
      })
    }

  })

  return res
}
