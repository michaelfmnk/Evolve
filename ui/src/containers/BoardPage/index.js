import React, { Component } from 'react'
import { connect } from 'react-redux'
import { boardByIdFromRoute, currentBoardId } from 'selectors/boards'
import { boardColumns } from 'selectors/columns'
import { authUserIdSelector } from 'selectors/auth'
import { getBoardById } from 'actions/boards'
import { createColumn, deleteColumn, updateColumn } from 'actions/columns'
import { createCard, moveCard, updateCard } from 'actions/cards'
import { bindActionCreators } from 'redux'
import { setCurrentBoard } from 'actions/boards'
import BoardHeader from 'components/BoardHeader'
import ColumnsList from 'components/ColumnsList'
import OppenedCard from 'components/OppenedCard'
import './BoardPage.css'


import HTML5Backend from 'react-dnd-html5-backend'
import { DragDropContext } from 'react-dnd'


class BoardPage extends Component {

  state = {
    openedCard: null
  }

  openCard = (openedCard) => () => this.setState({openedCard})
  closeCard = () => this.setState({openedCard: null})

  componentDidMount(){
    const { match, actions, currentBoardId} = this.props;
    const boardId = Number(match.params.board_id)
    if( boardId !== currentBoardId)  {
      actions.setCurrentBoard(boardId )
    }

    match && actions.getBoardById(boardId)
  }

  render () {
    const { board, authUserId, actions, boardColumnsWithCards } = this.props;
    const { openedCard } = this.state

    console.log(boardColumnsWithCards)
    
    if (!board) return null
    return (
      <main>
        <div className='wrp' style={{ height: '100vh', }} > 
        <div className='background' style={{backgroundImage: `url(${board.background_url})`, height: '100vh', position: 'fixed', top: 0, left: 0, right:0}} /> 
          <BoardHeader 
            boardName={board.name} 
            owner={board.owner} 
            collaborators={board.collaborators}
            isBoardPersonal={authUserId === board.owner_id  }
          />
          <ColumnsList
            columns={boardColumnsWithCards} 
            openCard={this.openCard}
            actions={{
              createColumn: actions.createColumn,
              deleteColumn: actions.deleteColumn,
              createCard: actions.createCard,
              moveCard: actions.moveCard,
              updateColumn: actions.updateColumn
            }}
          />
          
         { openedCard && 
            <OppenedCard 
              card={openedCard} 
              column={boardColumnsWithCards.find( col => col.id === openedCard.column_id)}
              boardUsers={[ board.owner, ...board.collaborators ]}
              updateCard={actions.updateCard}
              closeCard={this.closeCard}
            />
         }
        </div>       
      </main>
    )
  }
}

const mapStateToProps = (state, props) => ({
  board: boardByIdFromRoute(state, props),
  authUserId: authUserIdSelector(state),
  currentBoardId: currentBoardId(state),
  boardColumnsWithCards: boardColumns(state)
})

// const mapDispatchToProps = (dispatch) => ({
//   actions: bindActionCreators({
//     getBoardById,
//     createColumn,
//     setCurrentBoard,
//     createCard
//   }, dispatch)
// })



const mergeProps = (stateProps, dispatchProps, ownProps) => {
  const { dispatch } = dispatchProps
  const { currentBoardId } = stateProps

  return {
    ...stateProps,
    ...dispatchProps,
    ...ownProps,

    actions: {
      createCard: (columnId, card) => dispatch( createCard(currentBoardId, columnId, card) ),
      createColumn: (column) => dispatch( createColumn(currentBoardId, column) ),
      deleteColumn: (columnId) => dispatch( deleteColumn(currentBoardId, columnId) ),
      moveCard: (card, targetColumn) => dispatch( moveCard(currentBoardId, card, targetColumn) ),
      updateCard: (card) => dispatch( updateCard(currentBoardId, card) ),
      updateColumn: (column) => dispatch( updateColumn(currentBoardId, column) ),
      ...bindActionCreators({
        getBoardById,
        setCurrentBoard,
      }, dispatch)
  }
  }
}

BoardPage = DragDropContext(HTML5Backend)(BoardPage)

export default connect(mapStateToProps, null, mergeProps)(BoardPage)
