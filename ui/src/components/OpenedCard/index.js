import React, { Component } from 'react'
import CardMembers from "./CardMembers"
import CardHeader from './CardHeader'
import CardBody from './CardBody'
import Modal from 'components/Modal'
import { debounce } from 'lodash'
import './OpenedCard.css'

class OpenedCard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isEditing: '',
			content: this.props.card.content || '',
			title: this.props.card.title || ''
    }
  }

  updateContent = (value) => {
    this.setState({content:value})
  }

  handleSaveCardDescription = async () => {
    let card = {
      ...this.props.card,
			content: this.state.content,
    }

		await this.props.updateCard(card)
		this.toggleEditing()
	}
	
	cancelEditing = () => {
		this.setState({
			isEditing: false,
			content: this.props.card.content
		})
	}

  toggleEditing = () => this.setState( {isEditing: !this.state.isEditing })

	handleChange = (e) => {
    let { target: {name, value} } = e

    // disable enters
    value = value.replace('\n', '')

		this.setState({
			[name]: value
		}, this.handleSaveTitle())
	}

	handleSaveTitle = debounce(() => {
		this.props.updateCard({
			...this.props.card,
			title: this.state.title
		})
	}, 1000)

  render() {
    const { 
			card, column, boardUsers, deleteCard,
			closeCard ,handleUserAssigning, authUserId
    } = this.props;
    
    const { content, title, isEditing } = this.state;

    const saveBtnActiveClass = content !== card.content? 'active' : '' 
    
    return (
      <Modal onClose={closeCard} >
        <div className="windowhandler">
          <div className="openedboard">
            <CardHeader 
              handleCardNameInput={this.handleChange}
              backgroundImg={card.backgroundImg}
              title={title}
              closeCard={closeCard}
            />

            <div className="sectioncontent">
                In column <span className='column-name'> { column.name }</span>
                <CardMembers 
                  canEdit
                  withLabel
                  card={card} 
                  boardUsers={boardUsers} 
                  handleUserAssigning={handleUserAssigning}
                  authUserId={authUserId}
                  cancelEditing={this.cancelEditing}
                /> 
            </div>
            <div className="description">
                <div className="sectioncaption" onClick={this.toggleEditing}>
                    <i className="fas fa-list"></i>
                    Description
                </div>
                <div className="sectioncontent">
                  <CardBody 
                    content={content}
                    isEditing={isEditing}
                    updateContent={this.updateContent}
                    deleteCard={deleteCard ? () => deleteCard(card) : undefined}
                    handleSaveCardDescription={this.handleSaveCardDescription}
                    toggleEditing={this.toggleEditing}
                    cancelEditing={this.cancelEditing}
                    saveBtnActiveClass={saveBtnActiveClass}
                  />
                </div>
            </div>
          </div>
        </div>
     </Modal>
    )
  }
}

export default OpenedCard
