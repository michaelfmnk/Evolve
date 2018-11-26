import React, { Component } from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import { ConnectedRouter } from 'connected-react-router'
import HomePage from 'containers/HomePage'
import BoardPage from 'containers/BoardPage'
import AppHeader from 'containers/AppHeader'
import history from './history.js'
import { connect } from 'react-redux'
import { getAuthUserData } from 'actions/users'
import { authUserIdSelector } from 'selectors/auth'
import './App.css'

class App extends Component {
  componentDidMount () {
    this.props.actions.getAuthUserDataRequest()
  }

  render () {
    return (
      <React.Fragment>
        <AppHeader history={history} />
        <ConnectedRouter history={history}>
          <Switch>
            <Route path='/' exact render={() => <Redirect to='/home' />} />
            <Route path='/home' component={HomePage} />

            <Route path='/boards/:board_id' component={BoardPage} />
            {/* <Route path='/users/:user_id/profile' component={ProfilePage} />  */}

          </Switch>
        </ConnectedRouter>
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
      }
    }
  }
}

export default connect(mapStateToProps, null, mergeProps)(App)
