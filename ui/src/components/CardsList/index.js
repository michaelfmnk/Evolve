import React from 'react'
import AddEntityForm from 'components/AddEntityForm'

const CardsList = ({cards, createCard}) => (
  <div className="rowhandler">
   {
     cards.map( card => (
        <div className="todo">
            <div className="image"></div>
            <div className="caption">{card.title}</div>
            <div className="info">
                {/* <div className="notifications">
                    <i className="fas fa-bell"></i>
                    <div className="amount">2</div>
                </div> */}

                <div className="moreoptions">
                    <ul>
                        {card.content && <li content={'this card has description'}><i className="fas fa-list"></i></li>}
                    </ul>
                </div>
                <div className="white-pace"></div>
                <div className="members">
                    <ul>
                        <li></li>
                    </ul>
                </div>
            </div>
        </div>
      ))
     }

    <AddEntityForm 
      createEntity={createCard}
      placeholder="Enter new card title"
      btnText="Add card"

    />
  </div>
)

export default CardsList