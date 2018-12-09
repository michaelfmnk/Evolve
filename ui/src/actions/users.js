import * as ednpoints from 'constants/routes/api/users'
import * as types from 'constants/actionTypes/users'
import { userWithBoards } from 'constants/normalizr_schemas/userWithBoards'
import { normalize } from 'normalizr'
import { randomImg } from 'constants/defaultBackgrounds'

export const getAuthUserData = (userId) => ({
  type: types.GET_AUTH_USER_DATA,
  REQUEST: {
    url: ednpoints.userById(userId),
    responseDataConverter: userData => {
      const data = normalize(userData, userWithBoards)
      let boardsById = data.entities.boards

      Object.keys(boardsById).forEach(id => {

        if( !boardsById[id].background_url){
          boardsById[id].background_url = randomImg()
        }

      });

      return {
        authUser: data.entities.authUser[data.result],
        collaborators: data.entities.users,
        boards: boardsById
      }
    }
  }
})

export const getUser = (userId) => ({
  type: types.GET_USER_PREVIEW,
  REQUEST: {
    url: ednpoints.userById(userId)
  }
})
