import React from 'react'
import PropTypes from 'prop-types'
import CreationMenuDropdown from 'components/dropdowns/AppHeader/CreationMenuDropdown'
import UserMenuDropdown from 'components/dropdowns/AppHeader/UserMenuDropdown'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { logout } from 'actions/auth'
import { authUser } from 'selectors/user'
import './AppHeader.css'

class AppHeader extends React.Component {
  redirectToHome = () => {
    this.props.history.push('/home')
  }

  render () {
    const { user, actions } = this.props
    const profileLink = `/users/${user.id}/profile`
    return (
      <header className="page-header">
        <nav className="header-menu inline">
          <i className="fas fa-home nav-btn" onClick={this.redirectToHome} />
          <i className="fas fa-clipboard nav-btn"> {" "} boards</i>
        </nav>
        <div className="logo-wrp">
          {/* <img className="logo-img" /> */}
          Evolve
        </div>
        <nav className="header-menu inline">
          <CreationMenuDropdown 
            toggleCreationModal={this.props.toggleCreationModal}
          
          />
          <UserMenuDropdown
            user={user}
            handleExitClick={actions.logout}
            profileLink={profileLink}
          />
        </nav>
      </header>
    )
  }
}

const mapStateToProps = (state) => ({
  user: authUser(state)
})

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({
    logout
  }, dispatch)
})

AppHeader.propTypes = {
  actions: PropTypes.object
}

export default connect(mapStateToProps, mapDispatchToProps)(AppHeader)
