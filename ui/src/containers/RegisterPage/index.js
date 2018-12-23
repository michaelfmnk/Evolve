import React from 'react'
import VerifyAccountForm from 'components/VerifyAccountForm'
import RegisterForm from 'components/RegisterForm'
import { isVerifyingSelector, authUserIdSelector } from 'selectors/auth'
import { signUpRequest, verifyAccountRequest } from 'actions/auth'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import './RegisterPage.css'

class RegisterPage extends React.Component {
  render () {
    const { isVerifying, actions } = this.props
    console.log(this.props)
    return (
      <div className='register_page' >
        {
            isVerifying
          ? <VerifyAccountForm handleSubmit={actions.verifyAccount} />
          : <RegisterForm handleSubmit={actions.signUp} />
        }
        <div className='register_page_background' />
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  isVerifying: isVerifyingSelector(state),
  authUserId: authUserIdSelector(state)
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
        signUp: (userInfo) => dispatch(signUpRequest(userInfo))
      }
  }
}

RegisterPage.propTypes = {
  actions: PropTypes.object,
  isVerifying: PropTypes.bool.isRequired
}

export default connect(mapStateToProps, null, mergeProps)(RegisterPage)
