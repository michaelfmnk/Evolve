import React, { PureComponent } from 'react'
import Popup from 'components/Popup'
import Avatar from 'components/Avatar'
import ToggleableMember from 'components/ToggleableMember'
import { fullNameOf } from 'helpers/stringFormatting'

const CardMembers = (
  {card: { users }, boardUsers, canEdit , handleUserAssigning, withLabel, authUserId
}) => 
 (
  <div className='members-section'>
      { withLabel && "Members:" }

      <div className="members">
        {users && users.map( user => (
          <ToggleableMember 
            key={user.id}
            user={user}
    
            popupButtons = {[
              {
                content: user.id === authUserId ? 'Leave card' : 'Delete from card',
                onClick: handleUserAssigning ? handleUserAssigning(user) : undefined
              }
            ]}
          />
        ))}

        {canEdit && (
          <Popup
            trigger={
              <span className='add-member-btn' > 
                <i className='fa fa-plus'/> 
              </span>
            }
          >
            <div className='add-member-wrp'>
              <div className='users-list'>
                <span className='title'> BOARD MEMBERS
                  <Popup.CloseTrigger> 
                    <i className='close fas fa-times'/>
                  </Popup.CloseTrigger>
                </span>

                {boardUsers && boardUsers.map( user => (
                  <div 
                    onClick={handleUserAssigning(user)}
                    className='users-list-item' 
                    key={user.id}
                  >
                    <div className='user-info'>
                      <Avatar user={user}/>
                      <span> {fullNameOf(user)} </span>
                    </div>
                    {
                      users && users.some( ({id}) => id === user.id) && 
                        <i className='fas fa-check ' />
                    }
                  </div>
                ))}
              </div>
            </div>
          </Popup>
        )}
      </div>
  </div>
)


export default CardMembers