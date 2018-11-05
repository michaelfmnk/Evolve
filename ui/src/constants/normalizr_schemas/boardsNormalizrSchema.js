import { normalize, schema } from 'normalizr';

// Define a users schema
const user = new schema.Entity('users')
const card = new schema.Entity('cards')

const column = new schema.Entity.apply('columns', {
  cards: [card]
})

export const board = new schema.Entity('boards', {
  columns: [column]
})

