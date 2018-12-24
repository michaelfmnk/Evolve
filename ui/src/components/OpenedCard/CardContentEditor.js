import React, { PureComponent, Fragment } from 'react'
import JoditEditor from "jodit-react";
import 'jodit';
import 'jodit/build/jodit.min.css';


class CardContentEditor extends PureComponent {

  /**
   * @property Jodit jodit instance of native Jodit
   */

  jodit;
  setRef = jodit => this.jodit = jodit;

  config = {
    readonly: false,
    placeholder: "Add full description for this card...",
    showWordsCounter: false,
    showCharsCounter: false,
    showMessageErrors: false,
    showXPathInStatusbar: false
  }

  render() {
    const { content, updateContent, saveBtnActiveClass, handleSaveContent, cancelEditing } = this.props;
    return (
      <Fragment>
        <div className='content-editor-wrp'>
          <JoditEditor
              editorRef={this.setRef}
              value={content}
              config={this.config}
              onChange={updateContent}
          />
        </div>
        
        <div className="buttons-group"> 
          <span 
            className={`btn submit ${saveBtnActiveClass} `} 
            onClick={handleSaveContent}
          > 
            Save changes
          </span>
          <span className='btn' onClick={cancelEditing}> 
            Cancel 
          </span>
        </div> 
      </Fragment>
    )
  }
}

export default CardContentEditor