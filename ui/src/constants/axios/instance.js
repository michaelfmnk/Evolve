import axios from 'axios'
import store from 'store'
import { logout } from 'actions/auth'
import { push } from 'connected-react-router'

const instance = axios.create({

})

const token = localStorage.getItem('token')

if (token) {
  instance.defaults.headers.Authorization = `${token}`
}

export default instance
