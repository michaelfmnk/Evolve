import React from 'react'
import { goToAnchor } from 'react-scrollable-anchor'
import { NavHashLink as NavLink } from 'react-router-hash-link'
import PropTypes from 'prop-types'
import './NavHeader.css'

class NavHeader extends React.Component {
  isHashActive = (hash) => {
    return hash === this.props.location.hash
  }

  render () {
    const {onLoginClick, onRegisterClick} = this.props
    return (
      <nav id="nav-header">
        <button className="brand" href="#" onClick={onLoginClick} >Login</button>
        <button className="link-reg" href="#" onClick={onRegisterClick}>Registration</button>

        <ul className='hash-nav-bar'>
          <li>
            <NavLink
              isActive={() => this.isHashActive('#about')}
              onClick={() => goToAnchor('about', true)}
              className="link"
              to="/welcome#about"
              activeClassName="selected_link"

            >About</NavLink>
          </li>
          <li>
            <NavLink
              className="link"
              to="/welcome#projects"
              activeClassName="selected_link"
              isActive={() => this.isHashActive('#projects')}
              onClick={() => goToAnchor('projects', true)}
            >Projects
            </NavLink>
          </li>
          <li>
            <NavLink
              className="link"
              to="#contacts"
              activeClassName="selected_link"
              isActive={() => this.isHashActive('#contacts')}
              onClick={(event) => { goToAnchor('contacts', true) }}
          >
            Contacts
            </NavLink>
          </li>
        </ul>
      </nav>
    )
  }
}

NavHeader.propTypes = {
  onLoginClick: PropTypes.func.isRequired,
  onRegisterClick: PropTypes.func.isRequired,
  location: PropTypes.object.isRequired
}

export default NavHeader
