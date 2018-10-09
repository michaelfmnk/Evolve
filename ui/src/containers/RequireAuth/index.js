import React from 'react'
import { Redirect } from 'react-router-dom'
import PropTypes from 'prop-types'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import { isLoggedInSelector } from 'selectors/auth'

const RequireAuth = (props) => {
  const {Component, isLoggedIn} = props

  if ( isLoggedIn) {
    return <Component {...props} />
  }

  return <Redirect to='/welcome'/>
}

const mapStateToProps = state => {
  return {
    isLoggedIn: isLoggedInSelector(state),
  }
}

export default connect(mapStateToProps, null)(RequireAuth)