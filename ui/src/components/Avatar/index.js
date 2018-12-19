import React from 'react'
import './Avatar.css'

const Avatar = ({src, alt, circled, className, ...rest}) => {
  let resultClass = 'avatar';
  if( circled ) resultClass += ' circled'
  if(className) resultClass= `${resultClass} ${className}`

  return (
    <img 
      src={src || '/img/avatar.jpeg'} 
      className={resultClass} 
      alt={alt || ''}
      {...rest}
    />
  )
}

Avatar.defaultProps = {
  circled: true
}

export default Avatar