import React from 'react'
import Popup from 'components/Popup'
import './CreationMenuPopup.css'

const CreationMenuPopup = ({toggleCreationModal}) =>
 (
  <Popup
    closeOnPopupClick
    trigger={<i className="fas fa-plus trigger" />}
    animated
  >
    <ul className='creation-menu-popup-wrp' >
      <li onClick={toggleCreationModal}>
        <span ><i className="fas fa-plus" /> create board</span>
      </li>
      <li><span ><i className="fas fa-plus" /> create team</span></li>
    </ul>
  </Popup>  
)


export default CreationMenuPopup
