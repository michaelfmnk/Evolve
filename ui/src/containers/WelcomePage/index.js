import React from 'react'
import { connect } from 'react-redux'
import { configureAnchors } from 'react-scrollable-anchor'
import NavHeader from 'components/WelcomePage/NavHeader'
import WelcomeSection  from 'components/WelcomePage/WelcomeSection'
import AboutSection from 'components/WelcomePage/AboutSection'
import ProjectsSection from 'components/WelcomePage/ProjectsSection'
import ContactsSection from 'components/WelcomePage/ContactsSection'
import './WelcomePage.css'
 



class WelcomePage extends React.Component {

  handleScroll = (event) => {
    console.log(event.target)
    // if we in top page 
  }

  componentDidMount(){
    // document.addEventListener('scroll', this.handleScroll)
    configureAnchors({ scrollDuration: 300})
  }

  redirectTo = (path) => {
    this.props.history.push(path)
  }



  render(){
    return(
      <React.Fragment>
        <NavHeader 
          location={this.props.location}
          onLoginClick={() => this.redirectTo('/login')}
          onRegisterClick={() => this.redirectTo('/register')}
        />
        <WelcomeSection 
          onGetStartedClick={() => this.redirectTo('/register')}
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

export default connect(mapStateToprops, null)(WelcomePage)