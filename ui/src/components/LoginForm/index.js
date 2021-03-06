import React, { PureComponent } from 'react'
import FormHeader from 'components/FormHeader'
import ApplyFormBtn from 'components/buttons/ApplyFormBtn'
import Message from 'components/Message'
import { Link } from 'react-router-dom'
import InputWithLabelAndValidation from 'components/InputWithLabelAndValidation'
import PropTypes from 'prop-types'
import './LoginForm.css'

class LoginForm extends PureComponent {
  state = {
    email: '',
    password: '',
    remember_me: false
  }

  handleInput = (input) => {
    this.setState({
      [input.name]: input.value
    })
  }

  handleLoginFormSubmit = (event) => {
    event.preventDefault()
    this.props.handleSubmit && this.props.handleSubmit(this.state)
  }

  render () {
    return (
      <form className='login_form'>
        <FormHeader text="Log in" />

        <div className="login_form_content">

          <InputWithLabelAndValidation
            text='Email'
            inputName='email'
            onInput={({target}) => this.handleInput(target)}
            errorText='* e-mail should look like this: example@gmail.com'
          />

          <InputWithLabelAndValidation
            inputName='password'
            text='Password'
            type='password'
            pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
            onInput={({target}) => this.handleInput(target)}
            errorText={
              `* password must be at least 8 characters long
               * password must contain at least 1 number, 1 uppercase and lowercase letter`
            }
          />

          <input
            type="checkbox"
            id="remember_me_checkbox"
            onChange={({target}) => this.setState({ remember_me: target.checked })}
          />

          <Message type='error' text={this.props.errorMessage} />

          {/* TODO: separate link and label, fix ui for this element */}
          {/* <label htmlFor="remember_me_checkbox">


          </label> */}
          <Link to="/sign_up" className="have_account">I don't have an account</Link>
          
          <ApplyFormBtn
            text='Login'
            onClick={(event) => this.handleLoginFormSubmit(event)}
          />

        </div>
      </form>
    )
  }
}

LoginForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired
}

export default LoginForm
