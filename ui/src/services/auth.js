import callApi from 'helpers/callApi'
import * as endpoints from 'api/auth'

class AuthService {
	signIn (credentials) {
		return callApi({
			method: 'POST',
			url: endpoints.signIn,
			body: credentials
		})
	}

	signUp (userInfo) {
		return callApi({
			method: 'POST',
			url: endpoints.signUp,
			body: userInfo
		})
	}

	verifyAccount (userId) {
		return callApi({
			method: 'GET',
			url: endpoints.verifyAccount(userId)
		})
	}
}

export default new AuthService()
