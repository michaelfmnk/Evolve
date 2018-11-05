import callApi from 'helpers/callApi'
import * as endpoints from 'constants/endpoints/users'

class UsersService {
  getUser(id) {
    return callApi({
      method: 'GET',
      url: endpoints.getUserById(id),
    })
  }
}

export default new UsersService()
