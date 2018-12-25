import axios from 'axios'

const instance = axios.create()

const token = localStorage.getItem('token')

if (token) {
  instance.defaults.headers.Authorization = `${token}`
}

// instance.interceptors.request.use((config) => {
//   const token = store.getState().auth.token;
//   if (token) {
//     config.headers.authorization = `Bearer ${token}`;
//   }
//   return config;
// });

export default instance
