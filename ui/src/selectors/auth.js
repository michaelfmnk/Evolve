export const isVerifyingSelector = ({auth}) => !!((auth.user.id && !auth.token))
export const userIdSelector = ({auth}) => auth.user.id
export const isLoggedInSelector = ({auth}) => !!((auth.user.id && auth.token))
