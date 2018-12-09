import React from 'react'
import CardsList from 'components/CardsList'
import AddEntityForm from 'components/AddEntityForm'

const ColumnsList = ({columns, actions}) => (
  <div className="columnhandler">
  {
    columns.map( column => (
      <div className="column">
          <div className="cap-block">
              <div className="caption">{column.name}</div>
              <div className="options" onClick={() => actions.deleteColumn(column.id)}><i className="fas fa-times"></i></div>
          </div>

          <CardsList 
            cards={column.cards}
            createCard={(title) => actions.createCard(column.id, {title})}
          />

      </div>
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