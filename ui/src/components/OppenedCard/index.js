import React, { Component, Fragment } from 'react'
import './OppenedCard.css'
import 'jodit';
import 'jodit/build/jodit.min.css';
import JoditEditor from "jodit-react";
import CardMembers from "./CardMembers"
import { debounce } from 'lodash'

class OppenedCard extends Component {

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
  /**
   * @property Jodit jodit instance of native Jodit
   */
  jodit;
  setRef = jodit => this.jodit = jodit;

  config = {
    readonly: false, // all options from https://xdsoft.net/jodit/doc/
    placeholder: "Add full description for this card...",
    showWordsCounter: false,
    showCharsCounter: false,
    showMessageErrors: false,
    showXPathInStatusbar: false
  }

  handleSaveDescription = async () => {
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

    const additionalClass = content !== card.content? 'active' : '' 
    
    return (
      <div className="windowhandler">
        <div className="fade-background" />
        <div className="openedboard">
            
            {card.backgroundImg && (
              <div className="imagesection">
                  <img src={card.backgroundImg} alt=""/>
              </div>
            )}

            <h2 className="sectioncaption card-header">
              
                <i className="fas fa-clipboard"></i>
								<textarea value={title} name="title" onChange={this.handleChange}/>
								{/* {title} */}
             
              <button className="closewindow" onClick={closeCard}>
                <i className="fas fa-times"></i>
              </button>
            </h2>

            <div className="sectioncontent">
                In column <span className='column-name'> { column.name }</span>
                
								<CardMembers 
									canEdit
									withLabel
									card={card} 
									boardUsers={boardUsers} 
                  handleUserAssigning={handleUserAssigning}
                  authUserId={authUserId}
                />
                
            </div>


        <div className="description">
            <div className="sectioncaption" onClick={this.toggleEditing}>
                <i className="fas fa-list"></i>
                Description
            </div>
            <div className="sectioncontent">
                {
                  isEditing
                  ? (
                    <Fragment>
                      <div className='content-editor-wrp'>
                        <JoditEditor
                            editorRef={this.setRef}
                            value={this.state.content}
                            config={this.config}
                            onChange={this.updateContent}
                        />
                      </div>
                      
                      <div className="buttons-group"> 
                        <span 
                          className={`btn submit ${additionalClass} `} 
                          onClick={this.handleSaveDescription}
                        > 
                          Save changes
                        </span>
                        <span className='btn' onClick={this.cancelEditing}> 
                          Cancel 
                        </span>
                      </div> 
                    </Fragment>
                  )
                  : <Fragment>
                     {
                      card.content
                      ? <div 
                            onClick={this.toggleEditing} 
                            dangerouslySetInnerHTML={{ __html: card.content}}
                            className='desctiption-wrp'
                          />
                      : <p className='add-cart-content' onClick={this.toggleEditing}>
                          Add full description for this card...
                        </p>
                     }

                    <div className="buttons-group"> 
                      <span className={`btn delete `} onClick={() => deleteCard ? deleteCard(card) : undefined}> 
                        Delete card
                      </span>
                      {/* <span className='btn' onClick={this.toggleActive}> 
                        Archive
                      </span> */}
                    </div> 
                  </Fragment>
								}
            </div>
        </div>
      </div>
    </div>
    )
  }
}

export default OppenedCard
