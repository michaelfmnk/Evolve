import React from 'react'
import ReactDOM from 'react-dom'
import registerServiceWorker from 'registerServiceWorker'
import { Provider } from 'react-redux'
import { Route, Switch } from 'react-router-dom'
import { ConnectedRouter, routerActions } from 'connected-react-router'
import * as routes from 'constants/routes/ui'
import LoginPage from 'containers/LoginPage'
import RegisterPage from 'containers/RegisterPage'
import store from 'store'
import App from 'App'
import WelcomePage from 'containers/WelcomePage'
import RequireAuth from 'containers/RequireAuth'
import refreshAuthFromStorage from 'helpers/refreshAuthFromStorage'
import ActivateInvatedCollaborator from 'containers/ActivateInvatedCollaborator'
import history from './history.js'
import './index.css'


refreshAuthFromStorage(store)

ReactDOM.render(
  <Provider store={store}>
    <ConnectedRouter history={history}>
      <Switch>
        <Route path={routes.welcome} component={WelcomePage} />
        <Route path={routes.signIn} component={LoginPage} />
        <Route path={routes.signUp} component={RegisterPage} />
        <Route path={routes.invitation} exact component={ActivateInvatedCollaborator} />
        <Route path="/" render={() => <RequireAuth Component={App} />} />
      </Switch>
    </ConnectedRouter>
  </Provider>,
  document.getElementById('root')
)

registerServiceWorker()
