import React from 'react'
import BoardsList from 'components/BoardsList'
import { authUserBoards } from 'selectors/boards'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import './HomePage.css'
import Modal from 'components/Modal';
import BoardCreation from 'components/BoardCreation';

class HomePage extends React.Component {

  state = {
    boardCreationModalShown: false,
  };

  handleBoardClick = (board) => {
    this.props.history.push(`boards/${board.id}`)
  }

  setBoardCreationModalVisibility = visible => () => {
      this.setState({
          boardCreationModalShown: visible,
      });
  };

  handleBoardSubmit = (boardInfo) => {
      console.log(boardInfo);
  };

  render () {
    const { ownBoards, joinedBoards } = this.props.boards;
    const { boardCreationModalShown } = this.state;
    return (
      <main className="home-page-wrp">
        <h2>Personal boards:</h2>
        <BoardsList
          boards={ownBoards}
          canAdd
          onAddBoard={this.setBoardCreationModalVisibility(true)}
          handleBoardClick={this.handleBoardClick}
        />
        <h2>Joined boards:</h2>
          {
              boardCreationModalShown && (
                  <Modal
                      onClose={this.setBoardCreationModalVisibility(false)}
                  >
                      <BoardCreation
                          onBoardSubmit={this.handleBoardSubmit}
                      />
                  </Modal>
              )
          }
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

