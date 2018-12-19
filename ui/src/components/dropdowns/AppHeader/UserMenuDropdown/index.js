
import React from 'react'
import Dropdown from 'components/Dropdown'
import PropTypes from 'prop-types'
import { Link } from 'react-router-dom'
import './UserMenuDropdown.css'

class UserMenuDropdown extends React.Component {
  animation = () => {
    const component = this
    setTimeout(() => {
      if (component.dropdown) {
        component.dropdown.classList += ' animated'
      }
    }, 100)
  }

  renderTrigger = () => (
    <i className="fas fa-user trigger" />
  )

  renderDropdown = () => (
    <div className='creation-menu-dropdown-wrp' ref={(elem) => { this.dropdown = elem }}>
      <div className="usinfo">
        <img src={this.props.user.avatar_url || '/img/avatar.jpeg'} className="useravatar" />
        <p>{`${this.props.user.first_name} ${this.props.user.last_name}`}</p>
      </div>
      <div className="divideline" />
      <ul>
        <li> <Link to={this.props.profileLink}>  <span >  Profile </span> </Link> </li>
        <li><span >User boards</span></li>
        <div className="divideline" />
        <li><span >Settings</span></li>
        <li onClick={this.props.handleExitClick}><span >Exit</span></li>
      </ul>
    </div>
  )

  render () {
    return (
      <Dropdown
        renderDropdown={this.renderDropdown}
        renderTrigger={this.renderTrigger}
        animation={this.animation}
      />
    )
  }
}

UserMenuDropdown.propTypes = {
  handleExitClick: PropTypes.func.isRequired
}

export default UserMenuDropdown
