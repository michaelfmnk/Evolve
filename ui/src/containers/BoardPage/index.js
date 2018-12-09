import React, { Component } from 'react'
import { connect } from 'react-redux'
import { boardByIdFromRoute, currentBoardId } from 'selectors/boards'
import { boardColumns } from 'selectors/columns'
import { authUserIdSelector } from 'selectors/auth'
import { getBoardById } from 'actions/boards'
import { createColumn, deleteColumn } from 'actions/columns'
import { createCard } from 'actions/cards'
import { bindActionCreators } from 'redux'
import { setCurrentBoard } from 'actions/boards'
import BoardHeader from 'components/BoardHeader'
import ColumnsList from 'components/ColumnsList'
import './BoardPage.css'

class BoardPage extends Component {
  componentDidMount(){
    console.log(this.props)
    const { match, actions, board, currentBoardId} = this.props;
    const boardId = Number(match.params.board_id)
    if( boardId !== currentBoardId)  {
      actions.setCurrentBoard(boardId )
    }

    match && actions.getBoardById(boardId)
  }

  render () {
    const { board, authUserId, actions, boardColumnsWithCards } = this.props;

    console.log(boardColumnsWithCards)
    
    if (!board) return null
    return (
      <main>
        <div className='wrp' style={{backgroundImage: `url(${board.background_url})`, height: '100vh'}} > 
          <BoardHeader 
            boardName={board.name} 
            owner={board.owner} 
            collaborators={board.collaborators}
            isBoardPersonal={authUserId === board.owner_id  }
          />
          <ColumnsList
            columns={boardColumnsWithCards} 
            actions={{
              createColumn: actions.createColumn,
              deleteColumn: actions.deleteColumn,
              createCard: actions.createCard,
            }}
          />
         
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
      ...bindActionCreators({
        getBoardById,
        setCurrentBoard,
      }, dispatch)
  }
  }
}

export default connect(mapStateToProps, null, mergeProps)(BoardPage)
