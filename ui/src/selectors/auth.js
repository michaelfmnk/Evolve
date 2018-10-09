export const isVerifyingSelector = ({auth}) => !!((auth.userId && !auth.token))
export const userIdSelector = ({auth}) => auth.userId
export const isLoggedInSelector = ({auth}) => !!((auth.userId && auth.token))
