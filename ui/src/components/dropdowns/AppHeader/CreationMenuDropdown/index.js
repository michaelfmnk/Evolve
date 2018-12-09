import React from 'react'
import Dropdown from 'components/Dropdown'
import './CreationMenuDropdown.css'

class CreationMenuDropdown extends React.Component {
  animation = () => {
    const component = this
    setTimeout(() => {
      if (component.dropdown) {
        component.dropdown.classList += ' animated'
      }
    }, 100)
  }

  renderTrigger = () => (
    <i className="fas fa-plus trigger" />
  )

  renderDropdown = () => (
    <ul className='creation-menu-dropdown-wrp' ref={(elem) => { this.dropdown = elem }}>
      <li onClick={this.props.toggleCreationModal}>
        <span ><i className="fas fa-plus" /> create board</span>
      </li>
      <li><span ><i className="fas fa-plus" /> create team</span></li>
    </ul>
  )

  render () {
    return (
      <Dropdown
        renderDropdown={this.renderDropdown}
        renderTrigger={this.renderTrigger}
        animation={this.animation}
      />
    )
  }
}

export default CreationMenuDropdown
