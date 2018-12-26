import { saveAuthIdentifiersToStorage, clearLocalStorage } from 'helpers/localStorage'

describe('helpers tests', () => {
  const token = 'some token'
  const userId = 1

  describe('localStorage', () => {
    afterEach(() => {
      localStorage.setItem.mockClear();
      localStorage.removeItem.mockClear();
    });

    test('clearLocalStorage -- calls localStorage.removeItem with key "token" and with key "userId"', () => {
      clearLocalStorage()
      expect(localStorage.removeItem).toHaveBeenCalledTimes(2)
      expect(localStorage.removeItem).toHaveBeenCalledWith('token')
      expect(localStorage.removeItem).toHaveBeenCalledWith('userId')
    })

    test('saveAuthIdentifiersToStorage -- calls localStorage.setItem with key "token" whet it passed to func params', () => {
      saveAuthIdentifiersToStorage(token, null)
      expect(localStorage.setItem).toHaveBeenCalledTimes(1)
      expect(localStorage.setItem).toHaveBeenCalledWith('token', token)
    })

    test('saveAuthIdentifiersToStorage -- calls localStorage.setItem with key "userId" whet it passed to func params', () => {
      saveAuthIdentifiersToStorage(null, userId)
      expect(localStorage.setItem).toHaveBeenCalledTimes(1)
      expect(localStorage.setItem).toHaveBeenCalledWith('userId', userId)
    })

    test('saveAuthIdentifiersToStorage -- saves both token and userId to localStorage', () => {
      saveAuthIdentifiersToStorage(token, userId)
      expect(localStorage.setItem).toHaveBeenCalledTimes(2)
      expect(localStorage.setItem).toHaveBeenCalledWith('userId', userId)
      expect(localStorage.setItem).toHaveBeenCalledWith('token', token)
    })

  })
})