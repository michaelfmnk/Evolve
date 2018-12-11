import React, { Component } from 'react'
import CardsList from 'components/CardsList'
import AddEntityForm from 'components/AddEntityForm'

import { DropTarget } from 'react-dnd'



const squareTarget = {
  canDrop (props) {
    // const {canMovePiece, position: {x, y}} = props
    // return canMovePiece(x, y)
    console.log('CAN DROP ? ')
    console.log(props)
    return true
  },

  drop (props, monitor, component) {
    console.log(' DROPPED ')
    console.log(props,  monitor, component)
    return props.column
  }
}

function collect (connect, monitor) {
  const info = {
    connectDropTarget: connect.dropTarget(),
    isOver: monitor.isOver(),
    canDrop: monitor.canDrop()
  }

  return info
}

class Column extends Component {
  render() {
    const {column, actions, connectDropTarget , moveCard} = this.props
    return connectDropTarget(
      <div className="column">
        <div className="cap-block">
            <div className="caption">{column.name}</div>
            <div className="options" onClick={() => actions.deleteColumn(column.id)}><i className="fas fa-times"></i></div>
        </div>
    
        <CardsList 
          cards={column.cards}
          createCard={(title) => actions.createCard(column.id, {title})}
          moveCard={moveCard}
        />
    
      </div>
    )
    
  }
} 

Column = DropTarget("Card", squareTarget, collect)(Column)

const ColumnsList = ({columns, actions, moveCard}) => (
  <div className="columnhandler">
  {
    columns.map( column => (
      <Column column={column} actions={actions} moveCard={actions.moveCard}/>
    ))
  }
  {/* <div className="onemore"><a href="#"><i className="fas fa-plus"></i> Add Card</a></div> */}
  <div className="column">
    <AddEntityForm 
      placeholder='Enter column name'
      btnText='Add column'
      createEntity={(name) => actions.createColumn({name})}
    />
  </div>
  </div>
)

export default ColumnsList