export const isVerifyingSelector = ({auth}) => !!((auth.user.id && !auth.token))
export const authUserIdSelector = ({auth}) => auth.user.id
export const isLoggedInSelector = ({auth}) => !!((auth.user.id && auth.token))
