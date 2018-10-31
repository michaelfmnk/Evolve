import React, { Component } from 'react'
import { Route, Switch } from 'react-router-dom'
import HomePage from 'containers/HomePage'
import AppHeader from 'containers/AppHeader'
import './App.css'

class App extends Component {
  render () {
    return (
      <React.Fragment>
        <AppHeader />

        <Switch>
          <Route path='/home' component={HomePage} />
      {/* 
          <Route path='/boards/:board_id' component={BoardPage} />
          <Route path='/users/:user_id/profile' component={ProfilePage} /> 
      */}
        </Switch>
      </React.Fragment>
    )
  }
}

export default App
