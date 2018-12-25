import React, { PureComponent } from 'react'

const initialFormState = {
  title: '',
  active: false
}

class AddEntityForm extends PureComponent {

  state = initialFormState

  setWrapperRef = (node) => {
    this.formWrapper = node
  }

  activate = (e) => {
    if( !this.state.active) {
      this.setState({ active: true }, () => {
        document.addEventListener('click', this.disactivate)
      })
    }
  }

  disactivate = (event, hideImmediate) => {

    const shouldHide = hideImmediate || this.formWrapper && !this.formWrapper.contains(event.target)

    if(shouldHide) {
      this.setState({ ...initialFormState }, () => {
        document.removeEventListener('click', this.disactivate)
      })
    }
  }

  closePopupContent = () => {
    this.setState({ active: false }, () => {
      document.removeEventListener('click', this.disactivate)
    })
  }

  handleInput = ({target}) => {
    let value = target.value.replace('\n', '').trim()
    
    this.setState({
      [target.name]: value
    })
  }

  handleCreateEntity = async () => {
    let title = this.state.title.trim()
    if( !title ) return;

    await this.props.createEntity(title);
    this.disactivate({}, true)
  }

  render() {
    const { active, title } = this.state;
    const { placeholder, btnText } = this.props
    const additionalClass = !!title ? 'active' : '' 
    return(
      <div className="onemore" ref={this.setWrapperRef}>
        {
         active
          ? <div className="add-card-form"> 
              <textarea 
                placeholder={placeholder}
                name="title"
                onChange={this.handleInput}
              />

              <div className="buttons-group"> 
                <span className={`btn submit ${additionalClass}`} onClick={this.handleCreateEntity}> 
                  {btnText}
                </span>
                <span className='btn' onClick={ (e) => this.disactivate(e, true)}> 
                  Cancel 
                </span>
              </div> 

            </div>
      
          : <span 
              className="add-button" 
              onClick={this.activate}
            >
              <i className="fas fa-plus"></i>
              { " " + btnText}
            </span>
        } 
      </div>
    )
  }
}

export default AddEntityForm