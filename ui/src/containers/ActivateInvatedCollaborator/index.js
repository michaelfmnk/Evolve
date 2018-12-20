import React, { Component } from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { activateInvitationLink } from 'actions/auth'
import { isLoggedInSelector } from 'selectors/auth'
import { signInRequest } from 'actions/auth'
import LoginForm from 'components/LoginForm'


class ActivateInvatedCollaborator extends Component {

  componentDidMount() {
    if(this.props.isLoggedIn) {
      const { match: { params: {boardId, code} }} = this.props;
      console.log(boardId, code)
      this.props.actions.activateInvitationLink(boardId, code)
    }
  }

  componentDidUpdate() {
    if(this.props.isLoggedIn) {
      const { match: { params: {boardId, code} }} = this.props;
      console.log('TOKEN IN UPDATE')
      console.log(localStorage.getItem('token'))
      
      console.log(boardId, code)
      this.props.actions.activateInvitationLink(boardId, code)
    }
  }

  handleSubmit = (userInfo) => {
    console.log('THERE')
    this.props.actions.signInRequest(userInfo , false)
  }

  render() {
    const { isLoggedIn } = this.props

    return (
      <div className='login_page' >
        { !isLoggedIn && <LoginForm handleSubmit={this.handleSubmit} /> }
        <div className='login_page_background' />
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  isLoggedIn: isLoggedInSelector(state)
})

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({
    signInRequest,
    activateInvitationLink
  }, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(ActivateInvatedCollaborator)