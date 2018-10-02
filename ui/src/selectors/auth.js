export const isVerifying = ({auth}) => !!((auth.userId && !auth.token))
