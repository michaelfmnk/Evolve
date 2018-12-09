import React from 'react'
import CreationMenuDropdown from 'components/dropdowns/AppHeader/CreationMenuDropdown'
import UserMenuDropdown from 'components/dropdowns/AppHeader/UserMenuDropdown'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { logout } from 'actions/auth'
import { authUser } from 'selectors/user'
import PropTypes from 'prop-types'
import './AppHeader.css'

class AppHeader extends React.Component {
  redirectToHome = () => {
    console.log('HEADER')
    console.log(this.props)
    this.props.history.push('/home')
  }

  render () {
    return (
      <header className="page-header">
        <nav className="header-menu inline">
          <i className="fas fa-home nav-btn" onClick={this.redirectToHome} />
          <i className="fas fa-clipboard nav-btn"> boards</i>
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
            user={this.props.user}
            handleExitClick={this.props.actions.logout}
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
