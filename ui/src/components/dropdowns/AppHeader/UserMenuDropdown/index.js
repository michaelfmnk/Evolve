
import React from 'react'
import Dropdown from 'components/Dropdown'
import PropTypes from 'prop-types'
import Avatar from 'components/Avatar'
import { Link } from 'react-router-dom'
import { fullNameOf } from 'helpers/stringFormatting'
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

  renderDropdown = () =>{ 
    const { user, profileLink, handleExitClick } = this.props;
    return (
      <div className='creation-menu-dropdown-wrp' ref={(elem) => { this.dropdown = elem }}>
        <div className="usinfo">
          <Avatar src={user.avatar_url} className="useravatar" />
          <p>{fullNameOf(user)}</p>
        </div>
        <div className="divideline" />
        <ul>
          <li> <Link to={profileLink}>  <span >  Profile </span> </Link> </li>
          <li><span >User boards</span></li>
          <div className="divideline" />
          <li><span >Settings</span></li>
          <li onClick={handleExitClick}><span >Exit</span></li>
        </ul>
      </div>
    )
}

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
