import React, {Component} from 'react'
import AddEntityForm from 'components/AddEntityForm'
import  CardMembers from 'components/OpenedCard/CardMembers'
import { DragSource } from 'react-dnd'

const cardSource = {
  beginDrag (props, dnd, element) {
    console.log(props, dnd, element)
    return {}
  },

  endDrag(props, monitor, component) {
    const column = monitor.getDropResult()
    if( column ) {
      props.moveCard(props.card, column)
    }
  }
}

function collect (connect, monitor) {
  return {
    connectDragSource: connect.dragSource(),
    connectDragPreview: connect.dragPreview(),
    isDragging: monitor.isDragging()
  }
}

class Card extends Component {
  render() {
    const {card , connectDragSource, isDragging, openCard, column } = this.props
    return connectDragSource(
      <div className="todo" style={{opacity: isDragging? 0.2 : 1}} onClick={openCard(card)}>
        <div className="image"></div>
        <div className="caption">{card.title}</div>
        <div className="info">
            {/* <div className="notifications">
                <i className="fas fa-bell"></i>
                <div className="amount">2</div>
            </div> */}

            <div className="moreoptions">
              <ul>
                {
                  card.content && 
                    <li content={'this card has description'}>
                      <i className="fas fa-list" />
                    </li>
                }
              </ul>
            </div>
            <CardMembers 
              card={card}
              column={column}
            />
        </div>
      </div>
    )
  }
}

Card = DragSource("Card", cardSource, collect)(Card);

const CardsList = ({cards, createCard, moveCard, openCard, column}) => (
  <div className="rowhandler">
   {
     cards.map( card => (
        <Card 
          key={card.id}
          card={card} 
          moveCard={moveCard} 
          openCard={openCard} 
          column={column}
        />
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