import React from 'react'
import ReactDOM from 'react-dom'
import registerServiceWorker from 'registerServiceWorker'
import { Provider } from 'react-redux'
import { Route, Switch } from 'react-router-dom'
import { ConnectedRouter } from 'connected-react-router'
import { root, welcome, signIn, signUp, invitation } from 'constants/routes/ui'
import LoginPage from 'containers/LoginPage'
import RegisterPage from 'containers/RegisterPage'
import store from 'store'
import App from 'App'
import WelcomePage from 'components/WelcomePage'
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
        <Route path={welcome} component={WelcomePage} />
        <Route path={signIn} component={LoginPage} />
        <Route path={signUp} component={RegisterPage} />
        <Route path={invitation} exact component={ActivateInvatedCollaborator} />
        <Route path={root} render={() => <RequireAuth Component={App} />} />
      </Switch>
    </ConnectedRouter>
  </Provider>,
  document.getElementById('root')
)

registerServiceWorker()
