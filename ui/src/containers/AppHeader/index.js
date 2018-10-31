import React from 'react'
import CreationMenuDropdown from 'components/dropdowns/AppHeader/CreationMenuDropdown'
import UserMenuDropdown from 'components/dropdowns/AppHeader/UserMenuDropdown'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { logout } from 'actions/auth'
import PropTypes from 'prop-types'
import './AppHeader.css'

class AppHeader extends React.Component {
  render () {
    return (
      <header className="page-header">
        <nav className="header-menu inline">
          <i className="fas fa-home nav-btn" />
          <i className="fas fa-clipboard nav-btn"> boards</i>
        </nav>
        <div className="logo-wrp">
          {/* <img className="logo-img" /> */} 
          Evolve
        </div>
        <nav className="header-menu inline">
          <CreationMenuDropdown />
          <UserMenuDropdown
            handleExitClick={this.props.actions.logout}
          />
        </nav>
      </header>
    )
  }
}

const mapStateToProps = (state) => ({

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
