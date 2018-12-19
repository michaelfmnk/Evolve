export const signIn = '/sign_in'
export const signUp = '/sign_up'
export const welcome = '/welcome'
export const home = '/home'
export const invitation = '/invitations/:boardId/:code'

export const profileRoot = '/users/:user_id/profile'
export const profileActivity = profileRoot + '/activity'
export const profileCards = profileRoot + '/cards'
export const editProfile = profileRoot + '/edit'

export const getProfileActivityRoute = (userId) => `/users/${userId}/profile/activity`
export const getProfileCardsRoute = (userId) => `/users/${userId}/profile/cards`
export const getEditProfileRoute = (userId) => `/users/${userId}/profile/edit`
