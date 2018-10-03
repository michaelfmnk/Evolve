export default function (authIdentifiers) {
  localStorage.setItem('userId', authIdentifiers.user_id)
  localStorage.setItem('token', authIdentifiers.token)
}
