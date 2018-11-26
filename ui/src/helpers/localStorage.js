export function saveAuthIdentifiersToStorage (token, userId) {
  userId && localStorage.setItem('userId', userId)
  token && localStorage.setItem('token', token)
}

export function clearLocalStorage () {
  localStorage.removeItem('token')
  localStorage.removeItem('userId')
}
