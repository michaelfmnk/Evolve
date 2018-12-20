import axios from 'axios'

const instance = axios.create()

const token = localStorage.getItem('token')

if (token) {
  instance.defaults.headers.Authorization = `${token}`
}

export default instance
