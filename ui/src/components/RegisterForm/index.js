import React from 'react'
import FormHeader from 'components/FormHeader'
import ApplyFormBtn from 'components/buttons/ApplyFormBtn'
import InputWithLabelAndValidation from 'components/InputWithLabelAndValidation'
import PropTypes from 'prop-types'
import './RegisterForm.css'

class RegisterForm extends React.Component {
	constructor (props) {
		super(props)
		this.state = {
			email: '',
			first_name: '',
			last_name: '',
			password: '',
			password_confirm: ''
		}
	}

	handleInput = (input) => {
		this.setState({
			[input.nane]: input.value
		})
	}

	handleRegisterFormSubmit = () => {
		this.props.handleSubmit && this.props.handleSubmit(this.state)
	}

  // TODO : 1. Rewrite error validation from css rules to react way validation
  //        2. Add validation for password confirmation ( should match with password input )
	checkForError = (target, type) => {
    console.log('CHECKING FOR ERRORS')
		if (!target.value) return
		if (new RegExp(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/).test(target.value)) {
			return false
		}
	}

  render () {
    return (
      <form className='regform'>
        <FormHeader text="Registration" />

        <div className="regform_content">
          <InputWithLabelAndValidation
            text='Email'
            inputName='email'
            pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
            onInput={({target}) => this.handleInput(target)}
            errorText='* e-mail should look like this: example@gmail.com'
          />

          <InputWithLabelAndValidation
            text='First name'
            inputName='first_name'
            onInput={({target}) => this.handleInput(target)}
          />

          <InputWithLabelAndValidation
            text='Last name'
            inputName='last_name'
            onInput={({target}) => this.handleInput(target)}
          />

          <InputWithLabelAndValidation
            text='Password'
            inputName='password'
            type='password'
            pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
            onInput={({target}) => this.handleInput(target)}
            onBlur={({target}) => this.checkForError(target, 'password')}
            errorText={
              `* password must be at least 8 characters long 
               * password must contain at least 1 number, 1 uppercase and lowercase letter`
            }
          />

          <InputWithLabelAndValidation
            text='Password confirmation'
            inputName='password_confirm'
            type='password'
            pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
            onInput={({target}) => this.handleInput(target)}
            errorText={
              `* password must be at least 8 characters long 
               * password must contain at least 1 number, 1 uppercase and lowercase letter`
            }
          />

          <ApplyFormBtn
            text='Register'
            classNames='margin-top-2-rem'
            onClick={this.handleRegisterFormSubmit}
					/>

        </div>
      </form>
		)
  }
}

RegisterForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired
}

export default RegisterForm
