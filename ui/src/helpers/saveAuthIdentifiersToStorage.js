export default function (authIdentifiers) {
	localStorage.serItem('userId', authIdentifiers.user_id)
  localStorage.serItem('token', authIdentifiers.token)
}
