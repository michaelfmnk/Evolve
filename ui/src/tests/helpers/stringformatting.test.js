import { fullNameOf } from 'helpers/stringFormatting'

const mocks = [
  {
    user: {
      first_name: 'Name',
      last_name: 'Surname',
    },
    expectedFullName: 'Name Surname'
  },
  {
    
  }
]

describe('helpers', () => {
  describe('stringFormatting', () => {
    describe('fullNameOf', () => {
      test('should return full name of user', () => {
        expect(fullNameOf(mocks[0].user)).toEqual(mocks[0].expectedFullName)
      })
    })
  })
})