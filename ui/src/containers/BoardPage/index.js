import React, { Component } from 'react'
import { connect } from 'react-redux'
import { boardByIdFromRoute, currentBoardId } from 'selectors/boards'
import { authUserIdSelector } from 'selectors/auth'
import { getBoardById } from 'actions/boards'
import { createColumn } from 'actions/columns'
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
    const { board, authUserId, actions } = this.props
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
            columns={board.columns} 
            actions={{createColumn: actions.createColumn}}
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
})

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({
    getBoardById,
    createColumn,
    setCurrentBoard
  }, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(BoardPage)
