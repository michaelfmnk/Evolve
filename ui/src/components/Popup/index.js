import React from 'react'
import PropTypes from 'prop-types'
import './Popup.css'

const POPUP_TRIGGER_DISPLAY_NAME = 'Popup.CloseTrigger'

class Popup extends React.Component {
  state = {
    showContent: false
  }

  animation = () => {
    const component = this
    setTimeout(() => {
      if (component.popupWrapper) {
        component.popupWrapper.classList += ' ' + component.props.animatedClassName
      }
    }, component.props.animationTimeout)
  }

  
  showPopupContent = (event) => {
    event.preventDefault()
    if( !this.state.showContent) {
      this.setState({ showContent: true }, () => {
        this.props.animated && this.animation()
        document.addEventListener('click', this.hidePopupContent)
      })
    }
  }

  hidePopupContent = (event) => {
    const shouldHide = this.props.closeOnPopupClick 
      ? true 
      : this.popupWrapper && !this.popupWrapper.contains(event.target)

    if(shouldHide) {
      this.setState({ showContent: false }, () => {
        document.removeEventListener('click', this.hidePopupContent)
        this.popupWrapper.classList.remove(this.props.animatedClassName)
      })
    }
  }

  closePopupContent = () => {
    this.setState({ showContent: false }, () => {
      document.removeEventListener('click', this.hidePopupContent)
      this.popupWrapper.classList.remove(this.props.animatedClassName)
    })
  }

  setWrapperRef = (node) => {
    this.popupWrapper = node
  }

  renderWithCloseTrigger = (content) => {
    return  React.Children.map(content, child => {

      let resChild = { ... child }

      if (!resChild.props) {
        return child
      }

      if( resChild.type && resChild.type.displayName === POPUP_TRIGGER_DISPLAY_NAME){
        resChild = React.cloneElement(child , { closePopup: this.closePopupContent } )
      }

      if (resChild.props.children) {
        return React.cloneElement(resChild, {
          children: this.renderWithCloseTrigger(resChild.props.children),
        })
      }

      return resChild
    }) 
  }

  render () {
    const { children, trigger, renderContent } = this.props;

    let triggerComponent = trigger;

    if(typeof trigger === 'function' ) {
      triggerComponent = trigger()
    }

    let content = children ? children : renderContent ? renderContent() : null

    return (
      <div className='popup-wrp' ref={this.setWrapperRef}>
        <div onClick={this.showPopupContent}>
          { triggerComponent }
        </div>
        { this.state.showContent && this.renderWithCloseTrigger(content)}
      </div>
    )
  }
}

const CloseTrigger = ({children, closePopup}) => (
  React.Children.map(children, child => (
    child.props 
      ? React.cloneElement( child, { onClick: closePopup } ) 
      : child
  ))
)

Popup.CloseTrigger = CloseTrigger

CloseTrigger.displayName = POPUP_TRIGGER_DISPLAY_NAME

Popup.defaultProps = {
  animatedClassName: 'animated',
  animationTimeout: 100
}

Popup.propTypes = {
  renderTrigger: PropTypes.func.isRequired,
  renderDropdown: PropTypes.func.isRequired,
  animation: PropTypes.func
}

export default Popup
