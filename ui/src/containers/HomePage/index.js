import React from 'react'
import BoardsList from 'components/BoardsList'
import { withRouter } from 'react-router-dom'
import { authUserBoards } from 'selectors/boards'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { setCurrentBoard } from 'actions/boards'
import './HomePage.css'
import Modal from 'components/Modal'
import BoardCreation from 'components/BoardCreation'

class HomePage extends React.Component {

  handleBoardClick = (board) => {
    console.log(this.props)
    this.props.actions.setCurrentBoard(board.id)
    this.props.history.push(`boards/${board.id}`)
  }


  render () {
    const { ownBoards, joinedBoards } = this.props.boards
    const { openCreationModal } = this.props

    return (
      <main className="home-page-wrp">
        <h2>Personal boards:</h2>
        <BoardsList
          boards={ownBoards}
          canAdd
          onAddBoard={openCreationModal}
          handleBoardClick={this.handleBoardClick}
        />
        
        <h2>Joined boards:</h2>
        <BoardsList
          boards={joinedBoards}
          handleBoardClick={this.handleBoardClick}
        />
      </main>
    )
  }
}

const mapStateToProps = (state) => ({
  boards: authUserBoards(state)
})

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({
    setCurrentBoard,
  }, dispatch)
})

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(HomePage))
