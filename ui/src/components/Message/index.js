import React from 'react'
import './Message.css'

const Message = ({type, text}) => {
  if ( !text ) return null
  return (
    <div className='message-wrapper'>
      <span className={`${type}-message`}> {text} </span>
    </div>
  )
}

export default Message