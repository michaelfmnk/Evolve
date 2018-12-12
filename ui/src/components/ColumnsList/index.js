import React, { Component, Fragment } from 'react'
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

class Input extends Component {


  textInput = React.createRef();

  componentDidMount() {
    this.textInput.current.focus()
  }

  render() {
    const { onChange, value } = this.props;
    return <input className="caption" name="name" onChange={onChange} value={value} ref={this.textInput}/>
  }
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
    })
  }

  handleUpdateName = () => {
    const { column, actions } = this.props;

    this.props.actions.updateColumn({
      id: column.id,
      board_id: column.board_id,
      name: this.state.name
    });

    this.toggleEditing();
  }

  render() {
    const {column, actions, connectDropTarget , moveCard, openCard} = this.props
    const { isEditing, name } = this.state;


    return connectDropTarget(
      <div className="column">
        <div className="cap-block">
          {
            isEditing
              ? <Fragment>
                  <Input onChange={this.handleChange} value={name}/>
                  <div 
                    className="options" 
                    onClick={this.handleUpdateName}
                   >
                  <i className="fas fa-check" />
                </div>
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
      <Column column={column} actions={actions} moveCard={actions.moveCard} openCard={openCard}/>
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