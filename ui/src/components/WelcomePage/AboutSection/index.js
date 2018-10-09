import React from 'react'
import ScrollableAnchor from 'react-scrollable-anchor'
import './AboutSection.css'
import IpadImg from 'resources/images/WelcomePage/ipad.png'

const AboutSection = (props) => (
  <ScrollableAnchor id={'about'}>

  <section  className="about-section" >
        <div className="about-big">
            <div className="about-text">
                <h2>Какие-то Слова</h2>
                <p> Прям очень много слов.Прям очень много слов.Прям очень много слов.Прям очень много слов.Прям
                    очень много слов.Прям очень много слов.
                    <a href="#">ссылочка</a>. Прям очень много слов.Прям очень много слов.Прям очень много
                    слов.Прям очень много слов.Прям очень много слов.</p>
            </div>
        </div>
        <img src={IpadImg} alt=""/>
      
  </section>
  </ScrollableAnchor>
)

export default AboutSection

{/* <section  className="about-section">
<div className="container">
    <div className="about-big">
        <div className="about-text">
            <h2>Какие-то Слова</h2>
            <p> Прям очень много слов.Прям очень много слов.Прям очень много слов.Прям очень много слов.Прям
                очень много слов.Прям очень много слов.
                <a href="#">ссылочка</a>. Прям очень много слов.Прям очень много слов.Прям очень много
                слов.Прям очень много слов.Прям очень много слов.</p>
        </div>
    </div>
    <img src="resources/images/WelcomePage/ipad.png" alt=""/>
</div>
</section> */}