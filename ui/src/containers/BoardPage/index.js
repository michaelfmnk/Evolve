import React, { Component } from 'react'
import { connect } from 'react-redux'
import { boardByIdFromRoute, currentBoardId } from 'selectors/boards'
import { boardColumns } from 'selectors/columns'
import { authUserIdSelector } from 'selectors/auth'
import { openedCardSelector } from 'selectors/cards'
import { getBoardById, inviteCollaborator } from 'actions/boards'
import { createColumn, deleteColumn, updateColumn } from 'actions/columns'
import { 
  openCard, closeCard, deleteCard,
  createCard, moveCard, updateCard, 
  assignUsersToCard , unassignUserFromCard 
} from 'actions/cards'
import { bindActionCreators } from 'redux'
import { setCurrentBoard } from 'actions/boards'
import BoardHeader from 'components/BoardHeader'
import ColumnsList from 'components/ColumnsList'
import OppenedCard from 'components/OppenedCard'
import './BoardPage.css'


import HTML5Backend from 'react-dnd-html5-backend'
import { DragDropContext } from 'react-dnd'


class BoardPage extends Component {
  componentDidMount(){
    const { match, actions, currentBoardId} = this.props;
    const boardId = Number(match.params.board_id)
    if( boardId !== currentBoardId)  {
      actions.setCurrentBoard(boardId )
    }

    match && actions.getBoardById(boardId)
  }

  openCard = (card) => () => this.props.actions.openCard(card.id)
  closeCard = () =>  this.props.actions.closeCard()

  handleUserAssigning = (user) => () => {
    const { actions, openedCard } = this.props;

    openedCard.users && openedCard.users.some(({id}) => id === user.id )
      ? actions.unassignUserFromCard(openedCard, user.id )
      : actions.assignUsersToCard(openedCard, [ user.id ])
  }

  render () {
    const { board, authUserId, actions, boardColumnsWithCards, openedCard } = this.props;

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
            inviteCollaborator={actions.inviteCollaborator}
          />
          <ColumnsList
            columns={boardColumnsWithCards} 
            openCard={this.openCard}
            closeCard={this.closeCard}
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
              assignUserToCard={this.assignUserToCard}

              
              unassignUserFromCard={this.unassignUserFromCard}

              closeCard={this.closeCard}
              deleteCard={actions.deleteCard}
              handleUserAssigning={this.handleUserAssigning}
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
  boardColumnsWithCards: boardColumns(state),
  openedCard: openedCardSelector(state),
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
      assignUsersToCard: (card, usersIds) => dispatch( assignUsersToCard(currentBoardId, card, usersIds) ),
      unassignUserFromCard: (card, userId) => dispatch( unassignUserFromCard(currentBoardId, card, userId)),
      inviteCollaborator: (email) => dispatch( inviteCollaborator(currentBoardId, email)),
      deleteCard: (card) => dispatch( deleteCard(currentBoardId, card) ),
      ...bindActionCreators({
        getBoardById,
        setCurrentBoard,
        openCard, 
        closeCard,
      }, dispatch)
  }
  }
}

BoardPage = DragDropContext(HTML5Backend)(BoardPage)

export default connect(mapStateToProps, null, mergeProps)(BoardPage)
