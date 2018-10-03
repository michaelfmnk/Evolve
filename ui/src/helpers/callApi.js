export default async function (args) {
  try {
    console.log(extendFetchArgs(args))
    const result = await fetch(args.url, extendFetchArgs(args))
    if (result.status >= 400) {
      throw new Error(result.status + ' ' + result.statusText)
    }
    return result.json()
  } catch (e) {
    throw e
  }
}

function extendFetchArgs ({ method, body, headers = {} }) {
  body && (body = JSON.stringify(body))

  headers = {
      ...headers,
      'Accept': 'application/json',
      'Content-Type': 'application/json'
  }

  const token = localStorage.getItem('token')

  if (token !== null) {
    headers['Authorization'] = token
  }

  return {
    method,
    headers,
    body
  }
}
