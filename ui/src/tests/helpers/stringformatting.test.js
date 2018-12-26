import { fullNameOf } from 'helpers/stringFormatting'

describe('helpers tests', () => {
  describe('stringFormatting', () => {
    test('fullNameOf -- returns full name of user', () => {
      const fakeUser =  {
        first_name: 'Name',
        last_name: 'Surname',
      }
      const expectedFullName = fakeUser.first_name + " " + fakeUser.last_name
      
      expect(fullNameOf(fakeUser)).toEqual(expectedFullName)
    })
  })
})
