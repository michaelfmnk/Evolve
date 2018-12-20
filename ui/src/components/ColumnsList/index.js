import React, { Component, Fragment } from 'react'
import CardsList from 'components/CardsList'
import AddEntityForm from 'components/AddEntityForm'
import { debounce } from 'lodash'
import { DropTarget } from 'react-dnd'



const squareTarget = {
  canDrop (props) {
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
  state = {
    isEditing: false,
    name: this.props.column.name
  }

  textInput = React.createRef();

  toggleEditing = () => {
    this.setState({
      isEditing: !this.state.isEditing
    })
  }

  handleChange = ({ target: {name, value }}) => {
    this.setState({
      [name]: value
    }, this.handleUpdateName() )
  }

  handleUpdateName = debounce(() => {
    console.log(this.state)
    const { column, actions } = this.props;

    actions.updateColumn({
      id: column.id,
      board_id: column.board_id,
      name: this.state.name
    });
  }, 200)

  render() {
    const {column, actions, connectDropTarget , moveCard, openCard} = this.props
    const { isEditing, name } = this.state;


    return connectDropTarget(
      <div className="column">
        <div className="cap-block">
          {
            isEditing
              ? <Fragment>
                  <textarea
                    className='caption'
                    name='name'
                    onChange={this.handleChange} 
                    value={name}
                    autoFocus
                    onBlur={this.toggleEditing}
                  />
               </Fragment>
              : <Fragment>
                <div className="caption" onClick={this.toggleEditing}>{column.name}</div>
                <div 
                    className="options" 
                    onClick={() => actions.deleteColumn(column.id)}
                 >
                  <i className="fas fa-times" />
                </div>
              </Fragment>
          } 
        </div>
    
        <CardsList 
          cards={column.cards}
          createCard={(title) => actions.createCard(column.id, {title})}
          moveCard={moveCard}
          openCard={openCard}
          column={column}
        />
      </div>
    )
    
  }
} 

Column = DropTarget("Card", squareTarget, collect)(Column)

const ColumnsList = ({columns, actions, openCard}) => (
  <div className="columnhandler">
  {
    columns.map( column => (
      <Column 
        key={column.id}
        column={column} 
        actions={actions} 
        openCard={openCard}
        moveCard={actions.moveCard} 
      />
    ))
  }

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