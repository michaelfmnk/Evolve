import React, { Component, Fragment } from 'react'

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

  addUserToCard = (user) => () => {
    const { card, assignUserToCard } = this.props;
  }

  handle

  render() {
    const { card, card: { users }, boardUsers, canEdit , handleUserAssigning, withLabel} = this.props;
    const { isMemberAdding, activeMember } = this.state;

    return (
      <div className='members-section'>
          { withLabel && "Members:" }
          <div className="members">
            {
              users && users.map( user => (
                <img src={user.avatar_url} alt='' onClick={this.handleMemberClick}/>
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
                              <img src={user.avatar_url} alt=''/>
                              <span> {user.first_name + ' ' + user.last_name} </span>
                            </div>
                            { card.users && card.users.some( ({id}) => id === user.id) && <i className='fas fa-check '></i> }
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