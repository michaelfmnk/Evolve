import React, { Component } from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import { ConnectedRouter } from 'connected-react-router'
import HomePage from 'containers/HomePage'
import BoardPage from 'containers/BoardPage'
import AppHeader from 'containers/AppHeader'
import history from './history.js'
import { connect } from 'react-redux'
import { getAuthUserData } from 'actions/users'
import { createBoardRequest } from 'actions/boards'
import { authUserIdSelector } from 'selectors/auth'
import Modal from 'components/Modal'
import BoardCreation from 'components/BoardCreation'
import './App.css'

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isCreationModalOpen: false
    }
  }
  componentDidMount () {
    this.props.actions.getAuthUserDataRequest()
  }

  toggleModal = () => {
    this.setState({
      isCreationModalOpen: !this.state.isCreationModalOpen
    })
  }

  handleBoardSubmit = (board) => {
    this.props.actions.createBoardRequest(board);
  }

  render () {
    const { isCreationModalOpen } = this.state
    return (
      <React.Fragment>
        <AppHeader history={history} openCreationModal={this.toggleModal}/>
        <ConnectedRouter history={history}>
          <Switch>
            <Route path='/' exact render={() => <Redirect to='/home' />} />
            <Route path='/home' render={ () => <HomePage openCreationModal={this.toggleModal} />}/>
            <Route path='/boards/:board_id' component={BoardPage} />
            {/* <Route path='/users/:user_id/profile' component={ProfilePage} />  */}

          </Switch>
        </ConnectedRouter>
       {
         isCreationModalOpen && (
          <Modal onClose={this.toggleModal}>
            <BoardCreation onBoardSubmit={this.handleBoardSubmit} />
          </Modal>
         )
       } 
      </React.Fragment>
    )
  }
}

const mapStateToProps = (state) => ({
  authUserId: authUserIdSelector(state)
})

const mergeProps = (stateProps, {dispatch}) => {
  return {
    ...stateProps,
    actions: {
      getAuthUserDataRequest: () => {
        dispatch(getAuthUserData(stateProps.authUserId))
      },

      createBoardRequest
    }
  }
}

export default connect(mapStateToProps, null, mergeProps)(App)
