import React from 'react'
import logo from 'logo.svg'
import forest from 'resources/images/forest.jpg'
import lightForest from 'resources/images/lightForest.jpg'
import white from 'resources/images/white.jpg'
import PropTypes from 'prop-types'
import './BoardsList.css'

class BoardsList extends React.Component {
  handleBoardClick = (board) => {
    this.props.handleBoardClick(board)
  }


  render () {
    const { boards, canAdd, onAddBoard } = this.props
    return (
      <ul className='boards-list'>
        {
           boards && boards.map(board => (
             board &&
             <li
               className='board-block'
               style={{backgroundImage: `url(${board.background_url})`}}
               onClick={() => this.handleBoardClick(board)}
              >
               <span className='fade' />
               <p className="name">{board.name}</p>
               <div className="party">
                 <p className='members-header'>Members:</p>
                 <div className='members-wrp'>
                   <img src={board.owner.avatar_url || "/img/avatar.jpeg"} />
                   {
                      board.collaborators && board.collaborators.map(user => (
                        <img src={user.avatar_url || "/img/avatar.jpeg"} />
                      ))
                    }
                 </div>
               </div>
             </li>
            ))
          }
        {
            canAdd &&
            <li
              onClick={onAddBoard}
              className="board-block add-board"
            >
              <p><i className="fas fa-plus" /></p>
            </li>
          }
      </ul>
    )
  }
}

BoardsList.propTypes = {
  boards: PropTypes.arrayOf(PropTypes.object),
    onAddBoard: PropTypes.func
}

BoardsList.defaultProps = {
  canAdd: false,
  handleBoardClick: () => false,
  boards: [
    {
      name: 'First board',
      background: lightForest,
      users: [
        {
          avatar: logo
        },
        {
          avatar: logo
        }
      ]
    },
    {
      name: 'Ola-la',
      background: forest,
      users: [
        {
          avatar: logo
        },
        {
          avatar: logo
        },
        {
          avatar: logo
        },
        {
          avatar: logo
        }
      ]
    },
    {
      name: 'Board with very long title so long long olala it is toooo mach',
      background: white,
      users: [
        {
          avatar: logo
        },
        {
          avatar: logo
        }

      ]
    }
  ]
}

export default BoardsList
