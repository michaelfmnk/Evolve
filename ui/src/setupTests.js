
global.localStorage =  {
  getItem: jest.fn(),
  setItem: jest.fn(),
  removeItem: jest.fn(),
  clear: jest.fn()
};

global.console = {
  error: jest.fn()
}