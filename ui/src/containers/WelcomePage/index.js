import React from 'react'
import { connect } from 'react-redux'
import { configureAnchors } from 'react-scrollable-anchor'
import PropTypes from 'prop-types'
import NavHeader from 'components/WelcomePage/NavHeader'
import WelcomeSection from 'components/WelcomePage/WelcomeSection'
import AboutSection from 'components/WelcomePage/AboutSection'
import ProjectsSection from 'components/WelcomePage/ProjectsSection'
import ContactsSection from 'components/WelcomePage/ContactsSection'
import { signIn, signUp } from 'constants/routes/ui'
import './WelcomePage.css'

class WelcomePage extends React.Component {
  componentDidMount () {
    configureAnchors({ scrollDuration: 300 })
  }

  redirectTo = (path) => {
    this.props.history.push(path)
  }

  render () {
    return (
      <React.Fragment>
        <NavHeader
          location={this.props.location}
          onLoginClick={() => this.redirectTo(signIn)}
          onRegisterClick={() => this.redirectTo(signUp)}
        />
        <WelcomeSection
          onGetStartedClick={() => this.redirectTo(signIn)}
        />
        <AboutSection />
        <ProjectsSection />
        <ContactsSection />
      </React.Fragment>
    )
  }
}

const mapStateToprops = (state) => ({

})

WelcomePage.propTypes = {
  history: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired
}

export default connect(mapStateToprops, null)(WelcomePage)
