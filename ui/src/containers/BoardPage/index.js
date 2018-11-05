import React, { Component } from 'react'
import { connect } from 'react-redux'
import { boardByIdFromRoute } from 'selectors/boards'
import { bindActionCreators } from 'redux'
import './BoardPage.css'

class BoardPage extends Component {
  render(){
    const {board} = this.props
    if (!board) return null
    return(
      <main>
        <div  className = 'wrp' style={{backgroundImage: `url(${board.background_url})`, height:'100vh'}}/>

      </main>
    )
  }
}

const mapStateToProps = (state, props) => ({
  board: boardByIdFromRoute(state, props)
})

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators({

  }, dispatch)
})

export default connect(mapStateToProps, mapDispatchToProps)(BoardPage)