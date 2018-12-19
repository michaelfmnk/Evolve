import React from 'react'
import VerifyAccountForm from 'components/VerifyAccountForm'
import RegisterForm from 'components/RegisterForm'
import { isVerifyingSelector, authUserIdSelector, errorMessageSelector } from 'selectors/auth'
import { signUpRequest, verifyAccountRequest, clearAuthError } from 'actions/auth'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import './RegisterPage.css'

class RegisterPage extends React.Component {

  componentWillUnmount(){
    this.props.actions.clearAuthError()
  }

  render () {
    const { isVerifying, actions, errorMessage } = this.props
    console.log(this.props)
    return (
      <div className='register_page' >
        {
          isVerifying
            ? <VerifyAccountForm handleSubmit={actions.verifyAccount} errorMessage={errorMessage} />
            : <RegisterForm handleSubmit={actions.signUp} errorMessage={errorMessage}/>
        }
        <div className='register_page_background' />
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  isVerifying: isVerifyingSelector(state),
  authUserId: authUserIdSelector(state),
  errorMessage: errorMessageSelector(state)
})

const mergeProps = (stateProps, dispatchProps) => {
  const { authUserId } = stateProps
  const { dispatch } = dispatchProps

  return {
      ...stateProps,
      actions: {
        verifyAccount: (secredCode) => {
          dispatch(verifyAccountRequest(authUserId, secredCode))
        },
        signUp: (userInfo) => dispatch(signUpRequest(userInfo)),
        clearAuthError: () => dispatch(clearAuthError()),
      }
  }
}

RegisterPage.propTypes = {
  actions: PropTypes.object,
  isVerifying: PropTypes.bool.isRequired
}

export default connect(mapStateToProps, null, mergeProps)(RegisterPage)
