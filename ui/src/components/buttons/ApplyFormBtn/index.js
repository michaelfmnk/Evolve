import React from 'react'
import PropTypes from 'prop-types'
import './ApplyFormBtn.css'

const ApplyFormBtn = ({ text, onClick, id, classNames, type = 'submit' }) => (
  <button
    className={`apply_button ${classNames}`}
    type={type}
    id={id}
    onClick={onClick}
  >
    {text}
  </button>
)

ApplyFormBtn.propTypes = {
  id: PropTypes.string,
  text: PropTypes.string,
  type: PropTypes.string,
  classNames: PropTypes.string,
  onClick: PropTypes.func.isRequired
}

export default ApplyFormBtn
