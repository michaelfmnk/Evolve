import * as types from 'constants/actionTypes/images'


export const getDefaultBackgrounds = () => ({
  type: types.GET_BACKGROUNDS,
  REQUEST: {
    url: "/api/backgrounds"
  }
})