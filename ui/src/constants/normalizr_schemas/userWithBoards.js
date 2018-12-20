import { schema } from 'normalizr'

// Define a users schema
const user = new schema.Entity('users')

const board = new schema.Entity('boards', {
  owner: user,
  collaborators: [ user ]
})

export const userWithBoards = new schema.Entity('authUser', {
  own_boards: [board],
  joined_boards: [board]
})
