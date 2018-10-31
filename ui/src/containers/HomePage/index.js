import React from 'react'
import BoardsList from 'components/BoardsList'
import './HomePage.css'

class HomePage extends React.Component {
  render () {
    console.log(this.props)
    return (
      <main className="home-page-wrp">
        <h2>Your available boards:</h2>
        <BoardsList />
      </main>
    )
  }
}

export default HomePage
