export const isVerifyingSelector = ({auth}) => !!((auth.user.id && !auth.token))
export const authUserIdSelector = ({auth}) => auth.user.id
export const isLoggedInSelector = ({auth}) => !!((auth.user.id && auth.token))
export const errorMessageSelector = ({auth}) => auth.error ? auth.error.errorData.detail : ''
