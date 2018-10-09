import React from 'react'
import ScrollableAnchor from 'react-scrollable-anchor'
import './ProjectsSection.css'

import ProjectHeaderImg from 'resources/images/WelcomePage/bg-masthead.jpg'
import FirstProjectImg from 'resources/images/WelcomePage/demo-image-01.jpg'
import SecondProjectImg from 'resources/images/WelcomePage/demo-image-02.jpg'

const ProjectSection = (props) => (
  <ScrollableAnchor id={'projects'}>
    <section className="projects-section">
      <div className="container">
        <div className="projects-section">S
          <div className="projects-big-img">
            <img src={ProjectHeaderImg} alt="" />
          </div>
          <div className="projects-big-text">
            <div className="projects-text">
              <h2>Ещё слова</h2>
              <p>
                  Прям очень много слов.Прям очень много слов.Прям очень много слов.Прям очень много слов.Прям
                  очень много слов.Прям очень много слов.
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
                  <h2>Заглавие</h2>
                  <p>
                      Прям очень много слов.Прям очень много слов.Прям очень много слов.
                      Прям очень много слов.Прям очень много слов.
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
                  <h2>Заглавие</h2>
                  <p>
                      Прям очень много слов.Прям очень много слов.Прям очень много слов.Прям очень много
                      слов.Прям очень много слов.Прям очень много слов.
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
