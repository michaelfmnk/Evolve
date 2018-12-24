import React, { Component } from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import { ConnectedRouter } from 'connected-react-router'
import { bindActionCreators } from 'redux'
import HomePage from 'containers/HomePage'
import BoardPage from 'containers/BoardPage'
import UserProfilePage from 'containers/UserProfilePage'
import AppHeader from 'containers/AppHeader'
import history from './history.js'
import { connect } from 'react-redux'
import { getAuthUserData } from 'actions/users'
import { createBoardRequest } from 'actions/boards'
import { authUserIdSelector } from 'selectors/auth'
import { getDefaultBackgrounds } from 'actions/images'
import { home, root, boardRoute, profileRoot } from 'constants/routes/ui'
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
    const { backgrounds, actions } = this.props
    return (
      <React.Fragment>
        <AppHeader history={history} toggleCreationModal={this.toggleModal}/>
        <ConnectedRouter history={history}>
          <Switch>
            <Route path={root} exact render={() => <Redirect to={home} />} />
            <Route path={home} render={ () => <HomePage toggleCreationModal={this.toggleModal} />} />
            <Route path={boardRoute} component={BoardPage} />
            <Route path={profileRoot} component={UserProfilePage} /> 
          </Switch>
        </ConnectedRouter>
       {
         isCreationModalOpen && (
          <Modal onClose={this.toggleModal}>
            <BoardCreation 
              createBoard={this.handleBoardSubmit} 
              toggleModal={this.toggleModal}
              backgrounds={backgrounds}
              getDefaultBackgrounds={actions.getDefaultBackgrounds}
            />
          </Modal>
         )
       } 
      </React.Fragment>
    )
  }
}

const mapStateToProps = (state) => ({
  authUserId: authUserIdSelector(state),
  backgrounds: state.images
})

const mergeProps = (stateProps, dispatchProps, ownProps) => {
  const { dispatch } = dispatchProps;

  return {
    ...stateProps,
    ...dispatchProps,
    ...ownProps,

    actions: {
      getAuthUserDataRequest: () => {
        dispatch(getAuthUserData(stateProps.authUserId))
      },
      ...bindActionCreators({
        createBoardRequest,
        getDefaultBackgrounds
      }, dispatch)
      
    }
  }
}

export default connect(mapStateToProps, null, mergeProps)(App)
