import React, { Fragment, PureComponent, Component } from 'react'
import PropTypes from 'prop-types'
import './Modal.css'

export default class Modal extends Component {

  componentDidMount () {
    console.log('MOUNTED')
    document.addEventListener('mousedown', this.handleClickOutside)
  }

  componentWillUnmount () {
    document.removeEventListener('mousedown', this.handleClickOutside)
  }

  setWrapperRef = (node) => {
    this.wrapperRef = node
  }

  handleClickOutside = (event) => {
    if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
      this.onModalClose()
    }
  }

  onModalClose = () => {
    this.props.onClose();
  }

  render () {
    const { children } = this.props
    const childWithProps = React.cloneElement(children, { handleClose: this.onModalClose })
    return (
      <Fragment>
        <div className="fade-background" />
        <div className="content" ref={this.setWrapperRef}>
          {childWithProps}
        </div>
      </Fragment>
    )
  }
}

Modal.defaultProps = {
  closeOnBackdropClick: true
}

Modal.propTypes = {
  children: PropTypes.node,
  onClose: PropTypes.func.isRequired
};
