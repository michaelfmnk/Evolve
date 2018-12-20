import React, { Component } from 'react'
import Avatar from 'components/Avatar'
import ToggleableMember from 'components/ToggleableMember'
import { fullNameOf } from 'helpers/stringFormatting'

class CardMembers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeMember: null,
      isMemberAdding: false
    }
  }

  handleMemberClick = () => (activeMember) => this.setState({activeMember})

  toggleAdding = () => this.setState({isMemberAdding: !this.state.isMemberAdding})

  render() {
    const { card, card: { users }, boardUsers, canEdit , handleUserAssigning, withLabel, authUserId} = this.props;
    const { isMemberAdding } = this.state;

    return (
      <div className='members-section'>
          { withLabel && "Members:" }
          <div className="members">
            {
              users && users.map( user => (
                <ToggleableMember 
                  key={user.id}
                  user={user}
                  onClick={this.handleMemberClick}
                  popupButtons = {[
                    {
                      content: user.id === authUserId ? 'Leave card' : 'Delete from card',
                      onClick: handleUserAssigning ? handleUserAssigning(user) : undefined
                    }
                  ]}
                />
              ))
            }

            {canEdit && (
              <span className='add-member-btn' onClick={this.toggleAdding}> 
                <i className='fa fa-plus'/> 
              </span>
            )}

            {
              isMemberAdding && (
                <div className='add-member-wrp'>
                  
                   <div className='users-list'>
                     <span className='title'> BOARD MEMBERS
                        <i className='close fas fa-times' onClick={this.toggleAdding}/>
                      </span>
                     
                      {
                        boardUsers && boardUsers.map( user => (
                          <div className='users-list-item' onClick={handleUserAssigning(user)}>
                            <div className='user-info'>
                              <Avatar src={user.avatar_url}/>
                              <span> {fullNameOf(user)} </span>
                            </div>
                            {card.users && card.users.some( ({id}) => id === user.id) && 
                                <i className='fas fa-check '></i>}
                          </div>
                        ))
                      }
                   </div>
                </div>
              )
            }
          </div>
      </div>
    )
  }
}

export default CardMembers