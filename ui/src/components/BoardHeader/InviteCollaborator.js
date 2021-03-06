import React, { PureComponent, Fragment } from 'react'
import Modal from 'components/Modal'
import './InviteCollaborators.css'

const emailReg = new RegExp(/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/, "i")

class InviteCollaborator extends PureComponent {
  state = {
    isAdding: false,
    error: false,
    email: '',
    isSent: false
  }

  handleChange = ({target: {name, value}}) => {
    let error = emailReg.test(value)

    this.setState({
      [name]: value,
      error,
    }) 
  }

  handleInviteClick = () => {
    const {email} = this.state;
    const { inviteCollaborator } = this.props
    inviteCollaborator(email)
    this.setState({
      isSent: true,
      email: ''
    })

  }

  toggleAdding = () => this.setState({ isAdding: !this.state.isAdding})


  render() {
    const { isAdding , email, error, isSent } = this.state;
    return (
      <Fragment>
        <span className='add-member-btn' onClick={this.toggleAdding}> 
          <i className='fa fa-plus'/> 
        </span>
        <Modal onClose={this.toggleAdding} isOpen={isAdding}>
          <div className='invite-form'>
            <h3>
              { isSent
                ? 'Invation has been sent. Another one?'
                : 'Invite collaborator to this board'
              }
            </h3>
            <input 
              value={email} 
              type='email' 
              onChange={this.handleChange} 
              name='email' 
              placeholder='Enter email for inviting'
            />
            <div className="buttons-group"> 
                <span 
                  className={`btn submit ${!!error ? 'active' : ''}`} 
                  onClick={ !!error ? this.handleInviteClick : undefined}
                > 
                  Invite
                </span>
                <span className='btn cancel' onClick={this.toggleAdding}> 
                  Cancel 
                </span>
              </div> 
          </div>
        </Modal>
      </Fragment>
    )
  }
}

export default InviteCollaborator