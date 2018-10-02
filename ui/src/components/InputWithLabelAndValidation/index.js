import React from 'react'
import PropTypes from 'prop-types'
import './InputWithLabelAndValidation.css'

const InputWithLabelAndValidation = ({ text, id, pattern, onInput, errorText, inputName, type = 'text' }) => (
  <label htmlFor={id} className='label_with_input'>
    {text}
    <input
      required
      type={type}
      placeholder=" "
      id={id || inputName}
      name={inputName}
      pattern={pattern}
      onInput={onInput}
    />
    <p className="error_text unvisible att">
      {`${errorText}`}
    </p>
  </label>
)

InputWithLabelAndValidation.propTypes = {
  id: PropTypes.string,
  type: PropTypes.string,
  pattern: PropTypes.string,
  errorText: PropTypes.string,
  text: PropTypes.string.isRequired,
  onInput: PropTypes.func.isRequired,
  inputName: PropTypes.string.isRequired
}

export default InputWithLabelAndValidation
