import React from 'react'
import BoardsList from 'components/BoardsList'
import { authUserBoards } from 'selectors/boards'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import './HomePage.css'

class HomePage extends React.Component {

  handleBoardClick = (board) => {
    this.props.history.push(`boards/${board.id}`)
  }

  render () {
    console.log(this.props)
    const {ownBoards, joinedBoards} = this.props.boards
    return (
      <main className="home-page-wrp">
        <h2>Personal boards:</h2>
        <BoardsList 
          boards={ownBoards} 
          canAdd
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
    
  }, dispatch)
})


export default connect(mapStateToProps, mapDispatchToProps)(HomePage)

