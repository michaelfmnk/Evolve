import React from 'react'
import ScrollableAnchor from 'react-scrollable-anchor'
import './AboutSection.css'
import IpadImg from 'resources/images/WelcomePage/ipad.png'

const AboutSection = (props) => (
  <ScrollableAnchor id={'about'}>

    <section className="about-section" >
      <div className="about-big">
        <div className="about-text">
          <h2>Our name speaks for itself...</h2>
          <p> EVOLVE has little in common with the work products you are used to, 
          but it has a lot in common with the work itself. 
          Primarily aimed at customer satisfaction, our team will not only help streamline the process of large companies, 
          but also provide a reliable starting platform for small projects.
          We can safely call EVOLVE perfect blend of simplicity, convenience and, most importantly, reliability.
          Our name speaks for itself, and it says that joining our product will take your business to a "new level of evolution".
          </p>
        </div>
      </div>
      <img src={IpadImg} alt="" />

    </section>
  </ScrollableAnchor>
)

export default AboutSection
