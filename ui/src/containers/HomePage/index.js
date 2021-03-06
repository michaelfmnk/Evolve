import React from 'react'
import BoardsList from 'components/BoardsList'
import { withRouter } from 'react-router-dom'
import { authUserBoards } from 'selectors/boards'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { setCurrentBoard } from 'actions/boards'
import { getBoardRoute } from 'constants/routes/ui'
import './HomePage.css'

class HomePage extends React.Component {

  handleBoardClick = (board) => {
    this.props.actions.setCurrentBoard(board.id)
    this.props.history.push( getBoardRoute(board ))
  }

  render () {
    const { ownBoards, joinedBoards } = this.props.boards
    const { toggleCreationModal } = this.props

    return (
      <main className="home-page-wrp">
        <h2>Personal boards:</h2>
        <BoardsList
          boards={ownBoards}
          canAdd
          onAddBoard={toggleCreationModal}
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
