
import React from 'react'
import Popup from 'components/Popup'
import PropTypes from 'prop-types'
import Avatar from 'components/Avatar'
import { Link } from 'react-router-dom'
import { fullNameOf } from 'helpers/stringFormatting'
import './UserMenuPopup.css'

const UserMenuPopup = ({ user, profileLink, handleExitClick}) =>
 (
  <Popup
    closeOnPopupClick
    trigger={<i className="fas fa-user trigger" />}
    animated
  >
    <div className='creation-menu-popup-wrp' >
      <div className="usinfo">
        <Avatar user={user} className="useravatar" />
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
  </Popup>
)


UserMenuPopup.propTypes = {
  handleExitClick: PropTypes.func.isRequired
}

export default UserMenuPopup
