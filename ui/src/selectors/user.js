export const authUser = ({auth}) => auth.user
export const userByIdSelector = ({users}, id) => users.byId[id] || {}
