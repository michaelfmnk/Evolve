import React, { Fragment } from 'react'
import CardContentEditor from './CardContentEditor'

const CardBody = ({
  isEditing, content,toggleEditing, cancelEditing, updateContent, 
  saveBtnActiveClass, handleSaveCardDescription, deleteCard
}) => 
  (
    isEditing
      ? <CardContentEditor 
          content={content}
          updateContent={updateContent}
          saveBtnActiveClass={saveBtnActiveClass}
          handleSaveContent={handleSaveCardDescription}
          cancelEditing={cancelEditing}
        />
      : <Fragment>
        {
          content
          ? <div 
              onClick={toggleEditing} 
              dangerouslySetInnerHTML={{ __html: content}}
              className='desctiption-wrp'
            />
          : <p className='add-cart-content' onClick={toggleEditing}>
              Add full description for this card...
            </p>
        }

        <div className="buttons-group"> 
          <span className={`btn delete `} onClick={deleteCard}> 
            Delete card
          </span>
          {/* <span className='btn' onClick={this.toggleActive}> 
            Archive
          </span> */}
        </div> 
      </Fragment>
)

export default CardBody