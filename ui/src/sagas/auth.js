import { takeEvery, put, select } from 'redux-saga/effects'
import axiosInstance from 'constants/axios/instance'
import { push } from 'connected-react-router'
import { saveAuthIdentifiersToStorage, clearLocalStorage } from 'helpers/localStorage'
import { success } from 'helpers/actionsProcessTemplaters'
import { currentPathSelector } from 'selectors/router'
import { welcome, signIn, signUp, home, invitation } from 'constants/routes/ui'
import * as types from 'constants/actionTypes/auth'
import { GET_BOARD_BY_ID } from 'constants/actionTypes/boards'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'


function * authHandler (action) {
  const { token, user = {} } = action.payload;
  console.log('INSIDE HANDLER')
  console.log(token, )
  saveAuthIdentifiersToStorage(token, user.id)

  axiosInstance.defaults.headers.Authorization = token

  if(action.shouldRedirect) {
    yield put(push(home))
  }
}

function * signUpHandler (action) {
  yield saveAuthIdentifiersToStorage(null, action.payload.id)
}

function * logout (action) {
  clearLocalStorage()
  const currentPath = yield select(currentPathSelector)
  if ([signIn, signUp, invitation ].every(path => currentPath !== path)) {
    yield put(push(welcome))
  }
}

function * activate (action) {
  yield put(push(`/boards/${action.boardId}`))
}


const reconnectInterval = {};
const clients = {};

function * socket ({payload: {boardId}}) {

  const wsPath = `/api/ws/boards/${boardId}`
  const headers = {
    'Authorization': localStorage.getItem('token')
  }

  const onConnect = ( ) => console.log('INSIDE ONCONNECT')

  const onClose = () => {
    console.log('On CLose')
  }


  const socket = new SockJS(`${window.location.origin}/${wsPath}`, {}, { transports: 'websocket' });
  clients[wsPath] = Stomp.over(socket);
  clients[wsPath].connect(headers, () => {
      
          if (reconnectInterval[wsPath]) {
              clearInterval(reconnectInterval[wsPath]);
              reconnectInterval[wsPath] = null;
          }
          onConnect();
      }, onClose);
}



export default function * AuthSaga () {
  yield takeEvery( success(types.SIGN_UP), signUpHandler )
  yield takeEvery( [ success(types.VERIFY_ACCOUNT), success(types.SIGN_IN)], authHandler )
  yield takeEvery( success(types.ACTIVATE_INVITATION_LINK), activate )
  yield takeEvery( success(GET_BOARD_BY_ID) , socket )
  yield takeEvery( types.LOGOUT, logout )
}
