import React from 'react'
import logo from 'logo.svg'
import forest from 'resources/images/forest.jpg'
import lightForest from 'resources/images/lightForest.jpg'
import white from 'resources/images/white.jpg'
import PropTypes from 'prop-types'
import './BoardsList.css'

class BoardsList extends React.Component {
  render () {
    const { boards } = this.props
    return (
      <ul className='boards-list'>
         {
           boards && boards.map(board => (
              <li className='board-block' style={{backgroundImage: `url(${board.background})`}}>
                <span className='fade' />
                <p className="name">{board.name}</p>
                <div className="party">
                  <p className='members-header'>Members:</p>
                  <div className='members-wrp'>
                    {
                      board.users && board.users.map(user => (
                        <img src={user.avatar} />
                      ))
                    }
                  </div>
                </div>
              </li>
            ))
          }
        <li className="board-block add-board">
          <p><i className="fas fa-plus" /></p>
        </li>
      </ul>
    )
  }
}

BoardsList.propTypes = {
  boards: PropTypes.arrayOf(PropTypes.object)
}

BoardsList.defaultProps = {
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
