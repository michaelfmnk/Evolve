import React from 'react'
import PropTypes from 'prop-types'
import './Dropdown.css'

class Dropdown extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      displayMenu: false
    }
  }

  showDropdownMenu = (event) => {
    event.preventDefault()
    this.setState({ displayMenu: true }, () => {
      this.props.animation && this.props.animation()
      document.addEventListener('click', this.hideDropdownMenu)
    })
  }

  hideDropdownMenu = () => {
    this.setState({ displayMenu: false }, () => {
      document.removeEventListener('click', this.hideDropdownMenu)
    })
  }

  render () {
    const { renderTrigger, renderDropdown } = this.props
    return (
      <div className='dropdown-component-wrp'>
        <div onClick={this.showDropdownMenu}>
          { renderTrigger() }
        </div>
        { this.state.displayMenu && renderDropdown() }
      </div>
    )
  }
}

Dropdown.propTypes = {
  renderTrigger: PropTypes.func.isRequired,
  renderDropdown: PropTypes.func.isRequired,
  animation: PropTypes.func
}

export default Dropdown
