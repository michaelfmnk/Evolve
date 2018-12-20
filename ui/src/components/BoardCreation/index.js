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
				name: '',
				background_id: ''
    };

    componentDidMount() {
				if( !this.props.backgrounds.length ) {
					this.props.getDefaultBackgrounds()
				}
    }

    onClose = () => {
        this.props.handleClose()
    };

    onNameChange = (event) => {
        this.setState({
            name: event.target.value
        })
    };

    onBoardSubmit = async () => {
        const { name } = this.state
        if (name && name.length > 0) {
            await this.props.createBoard(this.state)
            this.props.toggleModal()
        }
    };

    render () {
				const { name , background_id} = this.state
				const { backgrounds } = this.props
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
              >
								<i className='fas fa-times'> </i>
							</span>
            </div>
            <h4> Choose background:</h4>
						<div className="backgrounds">
							
							{
								backgrounds.map( img => (
									<React.Fragment>
										<div onClick={() => this.setState({...img}, () => console.log(this.state))} className="img-wrp">
											<img key={img.background_id} src={img.background_url} alt=''/>
												{
													background_id === img.background_id && 
														<span className='checked-badge'> 
															<i className="fas fa-check"></i> 
														</span>
												}
											</div>
									 </React.Fragment>
								))
							}
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
