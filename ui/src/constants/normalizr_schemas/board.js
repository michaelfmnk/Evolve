import { schema } from 'normalizr'

// const user = new schema.Entity('users')
const card = new schema.Entity('cards')

const column = new schema.Entity('columns', {
  cards: [card]
})

export const board = new schema.Entity('board', {
  columns: [column]
})
