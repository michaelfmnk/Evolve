import React from 'react'
import PropTypes from 'prop-types'
import './WelcomeSection.css'

const WelcomeSection = ({onGetStartedClick}) => (
  <div className="welcome-section">
    <div className="welcome-section-head-text">
      <h1>Evolve</h1>
      <h2>Multiwork Helper</h2>
      <button  onClick={onGetStartedClick} >
        Get Started
      </button>
    </div>
  </div>
)

WelcomeSection.propTypes = {
  onGetStartedClick: PropTypes.func.isRequired
}

export default WelcomeSection 