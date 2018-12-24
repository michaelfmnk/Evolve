import React, { Component } from 'react'
import Avatar from 'components/Avatar'
import { Link } from 'react-router-dom'
import { getProfileActivityRoute } from "constants/routes/ui"
import { fullNameOf } from 'helpers/stringFormatting'
import './ToggleableMember.css'

class ToggleableMember extends Component {
  state = {
    isPopupOpen : false
  }

  componentDidMount () {
    document.addEventListener('mousedown', this.handleClickOutside)
  }

  componentWillUnmount () {
    document.removeEventListener('mousedown', this.handleClickOutside)
  }

  setWrapperRef = (node) => {
    this.wrapperRef = node
  }

  handleClickOutside = (event) => {
    if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
      this.setState({isPopupOpen : false})
    }
  }

  toggleMemberPopup = () => this.setState({isPopupOpen: !this.state.isPopupOpen})

  render() {
    const { user, title, popupButtons, withoutProfileLink, profileLinkText } = this.props;
    const { isPopupOpen } = this.state;

    return (
      <div className='toggleable-member-wrp'>
        <Avatar 
          onClick={this.toggleMemberPopup}
          src={user.avatar_url} 
          title={title ? title : fullNameOf(user)}
        />
        {isPopupOpen && (
          <div className='popup' ref={this.setWrapperRef}>
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
              <i className='fas fa-times' onClick={this.toggleMemberPopup}/>
            </div>
            
            {!withoutProfileLink && (
                <Link to={getProfileActivityRoute(user.id)}> 
                  <span className='btn action'> {profileLinkText} </span>
                </Link>
            )}

            {popupButtons.map( ({onClick, link, content}) => {
              let res = <span className='btn action' onClick={onClick}> {content} </span>
              if(link) {
                res = <Link to={link}> {res} </Link>
              }
              return res
            })}
          </div>
        )}
      </div>
    )
  }
}

ToggleableMember.defaultProps = {
  profileLinkText: 'View profile',
  withoutProfileLink: false
}

export default ToggleableMember