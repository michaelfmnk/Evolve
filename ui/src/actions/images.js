import * as types from 'constants/actionTypes/images'
import * as endpoints from 'constants/routes/api/images'


export const getDefaultBackgrounds = () => ({
  type: types.GET_BACKGROUNDS,
  REQUEST: {
    url: endpoints.defaultBackgrounds
  }
})