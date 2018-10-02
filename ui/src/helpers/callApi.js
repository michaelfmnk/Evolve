export default async function (args) {
	try {
		return await fetch(args.url, extendFetchArgs(args))
	} catch (e) {
		throw e
	}
}

function extendFetchArgs ({ method, body, headers = {} }) {
	body && (body = JSON.stringify(body))

	headers = {
			...headers,
			'Accept': 'application/json',
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${localStorage.getItem('token')}`
	}

	return {
		method,
    headers,
    body
	}
}
