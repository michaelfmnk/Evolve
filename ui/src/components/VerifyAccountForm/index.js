import React from 'react'
import FormHeader from 'components/FormHeader'
import ApplyFormBtn from 'components/buttons/ApplyFormBtn'
import PropTypes from 'prop-types'
import './VerifyAccountForm.css'

class VerifyAccountForm extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      code: ''
    }
  }

  handleInput = (input) => {
    this.setState({
      [input.name]: input.value
    })
  }

  handleSubmit = (event) => {
    event.preventDefault()
    this.props.handleSubmit && this.props.handleSubmit(this.state.code)
  }

  render () {
    return (
      <form className='verify_account_form'>
        <FormHeader text="Account verifying" />

        <p className='explanatory_text'>
            Check your email for secred code, we've just send it to you
        </p>

        <input
          required
          type="text"
          placeholder="Enter your secret code"
          name='code'
          id="secret_code"
          onInput={ ({target}) => this.handleInput(target) }
          />

        <ApplyFormBtn
          text='Verify'
          onClick={ (event) => this.handleSubmit(event) }
          />

      </form>
    )
  }
}

VerifyAccountForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired
}

export default VerifyAccountForm
