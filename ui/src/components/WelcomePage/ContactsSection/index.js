import React from 'react'
import ScrollableAnchor from 'react-scrollable-anchor'
import './ContactsSection.css'

const ContactsSection = (props) => (
  <ScrollableAnchor id={'contacts'}>
    <section className="contacts-section">
      <div className="container">
        <div className="foot">
          <div className="contacts-box">
            <div className="contacts-box-mid">
              <div className="foot-textbox">
                <h2>Address</h2>
                <hr />
                <div className="text-small">So so beloved.. Our.. FFEKS..</div>
              </div>
            </div>
          </div>

          <div className="contacts-box">
            <div className="contacts-box-mid">
              <div className="foot-textbox">
                <h2>Email</h2>
                <hr className="my-4" />
                <div className="text-small">
                  <a href="#">info@evolve-stage.com</a>
                </div>
              </div>
            </div>
          </div>

          <div className="contacts-box">
            <div className="contacts-box-mid">
              <div className="foot-textbox">
                <h2>Phone</h2>
                <hr />
                <div className="text-small">+38 (050) 123-45-67</div>
              </div>
            </div>
          </div>
        </div>

        <div className="contacts-low">
          <a href="#">
              FB
          </a>
          <a href="#">
              GIT
          </a>
        </div>
      </div>
    </section>
  </ScrollableAnchor>
)

export default ContactsSection
