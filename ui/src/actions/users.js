import {startAction, failAction, successAction} from 'helpers/actionsProcessTemplaters'
import * as types from 'actionsTypes/users'

export const getUser = (id, isAuthorizedUser) => ({
  type: startAction(types.GET_USER),
  payload: { id, isAuthorizedUser }
})

export const getUserSuccess = (user) => ({
  type: successAction(types.GET_USER),
  payload: user
})

export const getUserError = (error) => ({
  type: failAction(types.GET_USER),
  payload: error.message
})

export const getAuthorizedUserSuccess = (user) => ({
  type: successAction(types.GET_AUTHORIZED_USER),
  payload: user
})

export const getUsersPreviewsSuccess = (usersByIds) => ({
  type: successAction(types.GET_USERS_PREVIEWS),
  payload: { usersByIds }
})
