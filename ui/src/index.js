import React from 'react'
import ReactDOM from 'react-dom'
import registerServiceWorker from 'registerServiceWorker'
import { Provider } from 'react-redux'
import { Route, Switch } from 'react-router-dom'
import { ConnectedRouter } from 'connected-react-router'
import createBrowserHistory from 'history/createBrowserHistory'
import LoginPage from 'containers/LoginPage'
import RegisterPage from 'containers/RegisterPage'
import configureStore from 'store'
import App from 'App'
import WelcomePage from 'containers/WelcomePage'
import RequireAuth from 'containers/RequireAuth'
import refreshAuthFromStorage from 'helpers/refreshAuthFromStorage'
import 'index.css'

const history = createBrowserHistory()
const store = configureStore(history)

refreshAuthFromStorage(store)

ReactDOM.render(
  <Provider store={store}>
    <ConnectedRouter history={history}>
      <Switch>
        <Route path='/welcome' component={WelcomePage} />
        <Route path='/login' component={LoginPage} />
        <Route path='/register' component={RegisterPage} />
        <Route path="/" render={() => <RequireAuth Component={App} />} />
      </Switch>
    </ConnectedRouter>
  </Provider>,
  document.getElementById('root')
)

registerServiceWorker()
