import React from 'react'
import ScrollableAnchor from 'react-scrollable-anchor'
import './ProjectsSection.css'

import ProjectHeaderImg from 'resources/images/WelcomePage/bg-masthead2.jpg'
import FirstProjectImg from 'resources/images/WelcomePage/demo-image-01.jpg'
import SecondProjectImg from 'resources/images/WelcomePage/demo-image-02.jpg'

const ProjectSection = (props) => (
  <ScrollableAnchor id={'projects'}>
    <section className="projects-section">
      <div className="container">
        <div className="projects-section">
          <div className="projects-big-img">
            <img src={ProjectHeaderImg} alt="" />
          </div>
          <div className="projects-big-text">
            <div className="projects-text">
              <h2>But enough about us</h2>
              <p>
              Let's talk about you. Regardless of whether you are a business owner or manage small team, 
              you need a scrum board service that takes care of complex things so that you can focus on your work.
              </p>
            </div>
          </div>
        </div>

        <div className="projects-line">
          <div className="projects-line-img">
            <img src={FirstProjectImg} alt="" />
          </div>
          <div className="projects-big-div">
            <div className="projects-mid-div">
              <div className="projects-small-div">
                <div className="projects-line-final-text">
                  <h2>Just try</h2>
                  <p>
                  The intuitive interface, which even schoolchildren were able to 
                  master in minutes, is not overloaded with details and has a wide range of 
                  personalization options to make work comfortable especially for you.
                  </p>
                  <hr />
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="projects-line ch">
          <div className="projects-line-img">
            <img src={SecondProjectImg} alt="" />
          </div>
          <div className="projects-big-div">
            <div className="projects-mid-div">
              <div className="projects-small-div">
                <div className="projects-line-final-text">
                  <h2>We always help</h2>
                  <p>
                  A team of specialists will provide timely advice on any work issues, 
                  help you choose a direction for development, 
                  and even send training material for newcomers to Scrum.
                  </p>
                  <hr />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </ScrollableAnchor>
)

export default ProjectSection
