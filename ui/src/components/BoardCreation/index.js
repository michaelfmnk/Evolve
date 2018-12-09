import React, { Component } from 'react'
import PropTypes from 'prop-types'
import classnames from 'classnames'
import 'components/BoardCreation/BoardCreation.css'

export default class BoardCreationModal extends Component {
    static propTypes = {
        handleClose: PropTypes.func,
        onBoardSubmit: PropTypes.func
    };

    state = {
        name: ''
    };

    onClose = () => {
        this.props.handleClose()
    };

    onNameChange = (event) => {
        this.setState({
            name: event.target.value
        })
    };

    onBoardSubmit = () => {
        const { name } = this.state
        if (name && name.length > 0) {
            this.props.onBoardSubmit({ name: this.state.name })
        }
    };

    render () {
        const { name } = this.state
        return (
          <div className="modal">
            <div className="creation-dialog">
              <input
                name={'name'}
                placeholder={'Add board title'}
                className="board-name-input"
                value={name}
                onChange={this.onNameChange}
                    />
              <span
                onClick={this.onClose}
                className="close-icon"
              />
            </div>
            <button
              onClick={this.onBoardSubmit}
              className={classnames('submit-board-button', { 'active': name.length > 0})}
            >
              Create board
            </button>
          </div>
        )
    }
}
