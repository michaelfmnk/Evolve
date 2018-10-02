import * as types from 'actionsTypes/auth'

const initialState = {
	userId: null,
	token: null
}

export default function authReducer (state = initialState, action) {
	switch (action.type) {
		case types.SIGN_IN_SUCCESS:
			return action.payload

			case types.SIGN_UP_SUCCESS: {
				return {
					userId: action.payload.id,
					token: null
				}
			}

			case types.VERIFY_ACCOUNT_SUCCESS: {
				return {
					userId: action.payload.userId,
					token: action.payload.token
				}
			}

			default: return state
	}
}
