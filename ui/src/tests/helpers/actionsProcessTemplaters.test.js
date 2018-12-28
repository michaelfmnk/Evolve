import { 
  successActionWithType,
  startActionWithType,
  failActionWithType,
  success,
  start,
  fail, 
} from 'helpers/actionsProcessTemplaters'

describe('helpers tests', () => {
  describe('actionsProcessTemplaters', () => {
    const actionType = 'SOME_ACTION'
    const successIndicator = 'SUCCESS_'
    const failIndicator = 'ERROR_'
    const startIndicator = 'START_'
    const payload = {
      someKey: 'someValue'
    }

    test('start -- returns correct string value', () => {
      expect(start(actionType)).toEqual(startIndicator + actionType)
    })

    test('success -- returns correct string value', () => {
      expect(success(actionType)).toEqual(successIndicator + actionType)
    })

    test('fail -- returns correct string value', () => {
      expect(fail(actionType)).toEqual(failIndicator + actionType)
    })

    test('startActionWithType -- returns correct start action object', () => {
      const expectedAction = {
        type: startIndicator + actionType,
        payload
      }
      expect(startActionWithType(actionType, payload)).toEqual(expectedAction)
    })

    test('successActionWithType -- returns correct success action object', () => {
      const expectedAction = {
        type: successIndicator + actionType,
        payload
      }
      expect(successActionWithType(actionType, payload)).toEqual(expectedAction)
    })

    test('failActionWithType -- returns correct fail action object', () => {
      const expectedAction = {
        type: failIndicator + actionType,
        payload
      }
      expect(failActionWithType(actionType, payload)).toEqual(expectedAction)
    })

  })
})