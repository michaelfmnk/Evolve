export const start = (type) => `START_${type}`
export const fail = (type) => `ERROR_${type}`
export const success = (type) => `SUCCESS_${type}`

export const startActionWithType = (type, payload) => ({
  type: start(type),
  payload,
});

export const successActionWithType = (type, payload) => ({
  type: success(type),
  payload,
});

export const failActionWithType = (type, payload) => ({
  type: fail(type),
  payload
});