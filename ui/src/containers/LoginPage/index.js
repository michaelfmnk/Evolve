import React from 'react'
import './LoginPage.css'
import LoginForm from 'components/LoginForm'

class LoginPage extends React.Component {
  render () {
    return (
      <div className='login_page' >
        <LoginForm />
        <div className='login_page_background' />
      </div>
    )
  }
}

export default LoginPage
