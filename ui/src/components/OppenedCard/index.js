import React, { Component, Fragment } from 'react'
import './OppenedCard.css'
import 'jodit';
import 'jodit/build/jodit.min.css';
import JoditEditor from "jodit-react";
import CardMembers from "./CardMembers"

class OppenedCard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isEditing: '',
      content: this.props.card.content || ''
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

  handleSaveDescription = () => {
    let card = {
      ...this.props.card,
      content: this.state.content
    }

    console.log('CLICKED')
    console.log(card)
    this.props.updateCard(card)
  }

  toggleEditing = () => this.setState( {isEditing: !this.state.isEditing })


  render() {
    const { card, column, boardUsers, assignUserToCard, closeCard, unassignUserFromCard ,handleUserAssigning } = this.props;
    const { content, isEditing } = this.state;

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
              <div>
                <i className="fas fa-clipboard"></i>
                { card.title }
              </div>
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
									assignUserToCard={assignUserToCard} 
									unassignUserFromCard={unassignUserFromCard}
									handleUserAssigning={handleUserAssigning}
                />
                
            </div>


        <div className="description">
            <div className="sectioncaption">
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
                        <span className='btn' onClick={this.toggleEditing}> 
                          Cancel 
                        </span>
                      </div> 
                    </Fragment>
                  )
                  : card.content
                     ? <p>{card.content}</p>
                     : <p className='add-cart-content' onClick={this.toggleEditing}>
                         Add full description for this card...
                       </p>
                }
            </div>
        </div>

      </div>

    </div>

    )
  }
}

export default OppenedCard




{
  /*


                                  <div className="attachments">
                        <div className="sectioncaption">
                            <i className="fas fa-paperclip"></i>
                            Вложения
                        </div>
                        <div className="sectioncontent">
                            <div className="media">
                                <div className="col">
                                    <img className="shortcut" src="./thumb-1920-34904.jpg"></img>
                                </div>
                                <div className="col">
                                    <div className="caption">thumb-1920-34904.jpg</div>
                                    <div className="info">Добавлено: 3ч назад</div>
                                </div>
                            </div>
                            <div className="media">
                                <div className="col">
                                    <img className="shortcut" src="./thumb-1920-34904.jpg"></img>
                                </div>
                                <div className="col">
                                    <div className="caption">thumb-1920-34904.jpg</div>
                                    <div className="info">Добавлено: 5ч назад</div>
                                </div>
                            </div>
                            <a href="" className="addmedia">Добавить вложение</a>
                        </div>
                    </div>
        
                    <div className="actionslog">
                        <div className="sectioncaption">
                            <i className="fas fa-list"></i>
                            Последние действия
                        </div>
                        <div className="sectioncontent">

                        </div>
                    </div>

                    
              <div className="comments">
                        <div className="sectioncaption">
                            <i className="far fa-comment"></i>
                            Коментарии
                        </div>
                        <div className="sectioncontent">
                            <div className="com addcomment">
                                <div className="col">
                                    <div className="user"></div>
                                </div>
                                <div className="col">
                                    <textarea className="newcomment" placeholder="Оставьте комментарий..."></textarea>
                                    <button className="clip btn"><i className="fas fa-paperclip"></i></button>
                                    <button className="alertuser btn">@</button>
                                    <input type="submit" value="Отправить" />
                                </div>
                            </div>
                            <div className="com oldcomment">
                                <div className="col">
                                    <div className="user"></div>
                                </div>
                                <div className="col">
                                    <div className="text">
                                        askdnaokdn
                                    </div>
                                    <div className="info">
                                        <div className="reaction">
                                            <span className="count like-count">1</span>
                                            <button className="like btn"><i className="fas fa-thumbs-up"></i></button>
                                            <span className="count dislike-count">3</span>
                                            <button className="dislike btn"><i className="fas fa-thumbs-down"></i></button>
                                        </div>
                                        <div className="date">Added 3 hours ago</div>
                                    </div>
                                </div>
                            </div>
                            <div className="com oldcomment">
                                <div className="col">
                                    <div className="user"></div>
                                </div>
                                <div className="col">
                                    <div className="text">
                                        Lorem ipsum dolor sit amet consectetur, adipisicing elit. Aliquam deleniti,
                                        odit cum sint vero sed quisquam quod, corporis tenetur aut voluptatem id
                                        similique ad explicabo adipisci non facilis inventore quidem.
                                    </div>
                                    <div className="info">
                                        <div className="reaction">
                                            <span className="count like-count">21</span>
                                            <button className="like btn"><i className="fas fa-thumbs-up"></i></button>
                                            <span className="count dislike-count">3</span>
                                            <button className="dislike btn"><i className="fas fa-thumbs-down"></i></button>
                                        </div>
                                        <div className="date">Added 12 hours ago</div>
                                    </div>
                                </div>
                            </div>
                            <div className="com oldcomment">
                                <div className="col">
                                    <div className="user"></div>
                                </div>
                                <div className="col">
                                    <div className="text">Nice!</div>
                                    <div className="info">
                                        <div className="reaction">
                                            <span className="count like-count">1</span>
                                            <button className="like btn"><i className="fas fa-thumbs-up"></i></button>
                                            <span className="count dislike-count">4</span>
                                            <button className="dislike btn"><i className="fas fa-thumbs-down"></i></button>
                                        </div>
                                        <div className="date">Added 3rd December, 2018</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    */
}