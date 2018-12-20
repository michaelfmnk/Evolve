import React from 'react'
import InviteCollaborator from './InviteCollaborator'
import ToggleableMember from 'components/ToggleableMember'
import './BoardHeader.css'


const BoardHeader = ({boardName, collaborators, owner, isBoardPersonal, inviteCollaborator}) => (
    <div className="board-menu">
      <div className="boardmenul">
        <div className='header'>
          <div className="caption"> <span> {boardName}</span></div>
          <nav className="mainoptions">

          <p>
            <span ><i className="far fa-star"></i></span>
          </p>

          <p className='ownerhip'> 
            {
              isBoardPersonal 
                ? <span > <i className="fas fa-lock"/> Personal </span> 
                : <span > <i className="fas fa-users"/> Joined </span>
            }
          </p>
            
            
          </nav>
        </div>
        
        <div className='members'>
          { owner && ( 
            <ToggleableMember 
              user={owner} 
              title={`${owner.first_name} ${owner.last_name} | board admin`}
              popupButtons = {[
                {
                  content: 'leave board',
                  onClick: () => null
                }
              ]}
            /> 
          )
          }
          {
            collaborators && collaborators.map(user => (
              <ToggleableMember 
                key={user.id}
                user={user}
                popupButtons = {[
                  {
                    content: 'Delete from board',
                    onClick: () => null
                  }
                ]}
              />
            ))
          }

          <InviteCollaborator inviteCollaborator={inviteCollaborator}/>

        </div>

      </div>
      <i className="menu-icon fas fa-sliders-h"></i>  
    </div>
)

export default BoardHeader