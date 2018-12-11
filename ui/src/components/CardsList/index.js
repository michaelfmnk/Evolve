import React, {Component} from 'react'
import AddEntityForm from 'components/AddEntityForm'

import { DragSource } from 'react-dnd'

const knightSource = {
  beginDrag (props, dnd, element) {
    console.log('props of knight, since these aren\'t in the docs')
    console.log(props, dnd, element)
    return {}
  },

  endDrag(props, monitor, component) {
    console.log('END_DRAG')
    console.log(props, monitor, component)
    console.log(monitor.getDropResult())
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
    const {card , connectDragSource, isDragging } = this.props
    return connectDragSource(
      <div className="todo" style={{opacity: isDragging? 0.2 : 1}}>
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
    )
  }
}

Card = DragSource("Card", knightSource, collect)(Card);

const CardsList = ({cards, createCard, moveCard}) => (
  <div className="rowhandler">
   {
     cards.map( card => (
        <Card card={card} moveCard={moveCard} />
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