import React, { Fragment } from 'react'

const CardHeader = ({backgroundImg, handleCardNameInput, title, closeCard}) => (
  <Fragment>
    {backgroundImg && (
      <div className="imagesection">
          <img src={backgroundImg} alt=""/>
      </div>
    )}

    <h2 className="sectioncaption card-header">
        <i className="fas fa-clipboard"></i>
        <textarea value={title} name="title" onChange={handleCardNameInput}/>
      
      <button className="closewindow" onClick={closeCard}>
        <i className="fas fa-times"></i>
      </button>
    </h2>
  </Fragment>
)

export default CardHeader