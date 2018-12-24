import React from 'react'
import Avatar from 'components/Avatar'
import Popup from 'components/Popup'
import { Link } from 'react-router-dom'
import { getProfileActivityRoute } from "constants/routes/ui"
import { fullNameOf } from 'helpers/stringFormatting'
import './ToggleableMember.css'

const ToggleableMember = ({ user, title, popupButtons, withoutProfileLink, profileLinkText }) =>
 (
  <div className='toggleable-member-wrp'>
    <Popup 
      trigger={
        <Avatar 
          src={user.avatar_url} 
          title={title ? title : fullNameOf(user)}
        />
      }
    >
      <div className='popup'>
        <div className='popup-header'>
          <Avatar src={user.avatar_url} />

          <div className='user-info'>
            <div className='user-name'>
              {fullNameOf(user)}
            </div>
            <span className='user-email'>
                {user.email || 'fake@email.com'}
            </span>
          </div>
          <Popup.CloseTrigger> 
            < i className='fas fa-times' />
          </Popup.CloseTrigger> 
        </div>
        
        {!withoutProfileLink && (
            <Link to={getProfileActivityRoute(user.id)}> 
              <span className='btn action'> {profileLinkText} </span>
            </Link>
        )}

        {popupButtons.map( ({onClick, link, content}) => {
          let res = <span className='btn action' onClick={onClick}> {content} </span>
          if (link) {
            res = <Link to={link}> {res} </Link>
          }
          return res
        })}
      </div>
    </Popup>
  </div>
)

ToggleableMember.defaultProps = {
  profileLinkText: 'View profile',
  withoutProfileLink: false
}

export default ToggleableMember