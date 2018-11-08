import React from 'react'
import { signInRequest } from 'actions/auth'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import PropTypes from 'prop-types'
import LoginForm from 'components/LoginForm'
import './LoginPage.css'
 
class LoginPage extends React.Component {
  render () {
    return (
      <div className='login_page' >
        <LoginForm handleSubmit={this.props.actions.signInRequest} />
        <div className='login_page_background' />
      </div>
    )
  }
}

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({
    signInRequest
  }, dispatch)
})

LoginPage.propTypes = {
  actions: PropTypes.object
}

export default connect(null, mapDispatchToProps)(LoginPage)
