import React, { Component } from 'react'
import Avatar from 'components/Avatar'
import { connect } from 'react-redux'
import { userByIdSelector } from 'selectors/user'
import { authUserIdSelector } from 'selectors/auth'
import { Switch, Route, Redirect, NavLink } from 'react-router-dom'
import { ConnectedRouter } from 'connected-react-router'
import { fullNameOf } from 'helpers/stringFormatting'
import { 
  profileRoot, profileCards, profileActivity, editProfile,
  getProfileActivityRoute, getProfileCardsRoute, getEditProfileRoute 
} from 'constants/routes/ui'
import './UserProfile.css'

class UserProfile extends Component {
  render() {
    const { user, authUserId } = this.props;
    const isAuthorizedUser = user.id === authUserId; 

    return (
      <div className='user-profile-wrp' >
          <header className='profile-header'>
            <div className='user-info-wrp'>
              <div className='img-wrp'>
                <Avatar src={user.avatar_url} />
              </div>
              <div className='info'>
                <p className='user-name'> {fullNameOf(user)} </p>
                <p className='email'> {user.email || 'fake@email.com'} </p>
                {/* <span className='btn edit-profile-link'> Edit profile</span> */}
              </div>
            </div>
            <nav className='profile-nav-tabs'>
               <NavLink to={getProfileActivityRoute(user.id)} activeClassName='active-link'>
                   Activity             
               </NavLink>

               <NavLink to={getProfileCardsRoute(user.id)} activeClassName='active-link'>
                  Cards          
               </NavLink>
               {
                 isAuthorizedUser && (
                  <NavLink to={getEditProfileRoute(user.id)} activeClassName='active-link'>
                    Edit profile             
                  </NavLink>
                 )
               }
            </nav>
          </header>

          <main className='profile-content'>
            

            <ConnectedRouter history={this.props.history}>
              <Switch>
                <Route path={profileRoot} exact render={() => <Redirect to='profile/activity' />} />
                <Route path={profileCards} render={() => 'cards'} />
                <Route path={profileActivity} render={() => 'activity'} />
                <Route path={editProfile} render={() => 'edit'} />
              </Switch>
            </ConnectedRouter>

          </main>
          
      </div>
    )
  }
}

UserProfile.defaultProps = {
  user: {
    first_name: 'John',
    last_name: 'Snow',
    email: 'some@email.com',
    avatar_url: null,
  }
}

const mapStateToProps = (state, ownProps) => ({
  user: userByIdSelector(state, ownProps.match.params.user_id),
  authUserId: authUserIdSelector(state)
})

// const mapDispatchToProps = (dispatch) => ({
//   actions: bindActionCreators({
//     signInRequest,
//     clearAuthError
//   }, dispatch)
// })

export default connect(mapStateToProps, null) (UserProfile)