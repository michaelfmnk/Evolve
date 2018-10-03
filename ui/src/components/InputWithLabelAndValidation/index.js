import React from 'react'
import PropTypes from 'prop-types'
import './InputWithLabelAndValidation.css'

const InputWithLabelAndValidation = (props) => {
  const { text, id, pattern, onInput, onBlur, errorText, inputName, type } = props
  return (
    <label htmlFor={id || inputName} className='label_with_input'>
      {text}
      <input
        required
        type={type}
        placeholder=" "
        id={id || inputName}
        name={inputName}
        pattern={pattern}
        onInput={onInput}
        onBlur={onBlur}
      />
      <p className="error_text unvisible att">
        {`${errorText}`}
      </p>
    </label>
  )
}

InputWithLabelAndValidation.propTypes = {
  id: PropTypes.string,
  type: PropTypes.string,
  onBlur: PropTypes.func,
  pattern: PropTypes.string,
  errorText: PropTypes.string,
  text: PropTypes.string.isRequired,
  onInput: PropTypes.func.isRequired,
  inputName: PropTypes.string.isRequired
}

InputWithLabelAndValidation.defaultProps = {
  onInput: () => null,
  onBlur: () => null,
  type: 'text'
}

export default InputWithLabelAndValidation
