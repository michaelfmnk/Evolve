import React from 'react'
import { signInRequest, clearAuthError } from 'actions/auth'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { errorMessageSelector } from 'selectors/auth'
import PropTypes from 'prop-types'
import LoginForm from 'components/LoginForm'
import './LoginPage.css'

class LoginPage extends React.Component {
  
  componentWillUnmount(){
    this.props.actions.clearAuthError()
  }

  render () {
    const { actions, errorMessage } = this.props
    return (
      <div className='login_page' >
        <LoginForm handleSubmit={actions.signInRequest} errorMessage={errorMessage}/>
        <div className='login_page_background' />
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  errorMessage: errorMessageSelector(state)
})

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({
    signInRequest,
    clearAuthError
  }, dispatch)
})

LoginPage.propTypes = {
  actions: PropTypes.object
}

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage)
