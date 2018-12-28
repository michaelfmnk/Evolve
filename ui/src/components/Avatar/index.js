import React from 'react'
import './Avatar.css'

const Avatar = ({user, alt, circled, className, defaultIcon, ...rest}) => {
  let resultClass = 'avatar';
  if( circled ) resultClass += ' circled'
  if(className) resultClass= `${resultClass} ${className}`
  if(!user){
    console.log('NONE')
    return null
  }

  if (!user.avatar_url && !defaultIcon) return (
    <div className={resultClass + ' default'} {...rest}>
      { (user.first_name.charAt(0) +  user.last_name.charAt(0)).toUpperCase() }
    </div>
  )

  return (
    <img 
      src={user.avatar_url || '/img/avatar.jpeg'} 
      className={resultClass} 
      alt={alt}
      {...rest}
    />
  )
}

Avatar.defaultProps = {
  circled: true,
  alt: ''
}

export default Avatar