import React from 'react'
import PropTypes from 'prop-types'
import './FormHeader.css'

const FormHeader = ({text}) => (
  <div className="form_header">
    <span> { text } </span>
  </div>
)

FormHeader.propTypes = {
  text: PropTypes.string.isRequired
}

export default FormHeader
