import React from 'react'
import VerifyAccountForm from 'components/VerifyAccountForm'
import RegisterForm from 'components/RegisterForm'
import { isVerifyingSelector } from 'selectors/auth'
import { signUpRequest, verifyAccountRequest } from 'actions/auth'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import PropTypes from 'prop-types'
import './RegisterPage.css'

class RegisterPage extends React.Component {
  render () {
    const { isVerifying, actions } = this.props
    return (
      <div className='register_page' >
        {
            isVerifying
          ? <VerifyAccountForm handleSubmit={actions.verifyAccountRequest} />
          : <RegisterForm handleSubmit={actions.signUpRequest} />
        }
        <div className='register_page_background' />
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  isVerifying: isVerifyingSelector(state)
})

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({
    signUpRequest,
    verifyAccountRequest
  }, dispatch)
})

RegisterPage.propTypes = {
  actions: PropTypes.object,
  isVerifying: PropTypes.bool.isRequired
}

export default connect(mapStateToProps, mapDispatchToProps)(RegisterPage)
