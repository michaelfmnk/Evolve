import React, { Fragment, PureComponent } from 'react'
import PropTypes from 'prop-types'
import './Modal.css'

export default class Modal extends PureComponent {
    static propTypes = {
        children: PropTypes.node,
        onClose: PropTypes.func.isRequired
    };

    onModalClose = () => this.props.onClose();

    render () {
        const { children } = this.props
        const childWithProps = React.cloneElement(children, { handleClose: this.onModalClose })
        return (
          <Fragment>
            <div className="fade-background" />
            <div className="content">
              {childWithProps}
            </div>
          </Fragment>
        )
    }
}
