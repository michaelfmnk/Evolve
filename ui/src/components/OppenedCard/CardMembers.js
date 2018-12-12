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

  adduserToCard = (user) => {}

  render() {
    const { card, card: { users }, boardUsers, updateCard} = this.props;
    const { isMemberAdding, activeMember } = this.state;

    return (
      <div className='members-section'>
          Members:
          <div className="members">
            {
              users && users.map( user => (
                <img src={user.avatar_utl} alt='' onClick={this.handleMemberClick}/>
              ))
            }

            <span className='add-member-btn' onClick={this.toggleAdding}> <i className='fa fa-plus'></i> </span>

            {
              isMemberAdding && (
                <div className='add-member-wrp'>
                  
                   <div className='users-list'>
                     <span className='title'> BOARD MEMBERS
                        <i className='close fas fa-times' onClick={this.toggleAdding}/>
                      </span>
                     
                      {
                        boardUsers && boardUsers.map( user => (
                          <div className='users-list-item'>
                            <div className='user-info'>
                              <img src={user.avatar_url} alt=''/>
                              <span> {user.first_name + ' ' + user.last_name} </span>
                            </div>
                            { card.users && card.users.includes(user) && <i className='fas fa-check '></i> }
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