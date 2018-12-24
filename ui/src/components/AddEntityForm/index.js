import React, { PureComponent } from 'react'

class AddEntityForm extends PureComponent {

  state = { 
    title: '',
    active: false
  }

  toggleActive = () => {
    this.setState( { active: !this.state.active })
  }

  handleInput = ({target}) => {
    let value = target.value.replace('\n', '').trim()
    
    this.setState({
      [target.name]: value
    }, () => console.log(this.state))
  }

  handleCreateEntity = async () => {
    const { title } = this.state
    if( !title.trim() ) return

    await this.props.createEntity(title);
    this.toggleActive()
  }

  render() {
    const { active, title } = this.state;
    const { placeholder, btnText } = this.props
    const additionalClass = !!title ? 'active' : '' 
    return(
      <div className="onemore">
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
                <span className='btn' onClick={this.toggleActive}> 
                  Cancel 
                </span>
              </div> 

            </div>
      
          : <span 
              className="add-button" 
              onClick={this.toggleActive}
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