import firebase from 'firebase/app'
import 'firebase/auth'

export const app = !firebase.apps.length
  ? firebase.initializeApp({
      apiKey: 'AIzaSyCQzyZ2dRUl_dQFeCLqRxrHJEMD9a5D0WI',
      authDomain: 'snsanalytics-ef74a.firebaseapp.com',
      projectId: 'snsanalytics-ef74a'
    })
  : firebase.app()

const env = process.env.NODE_ENV || 'development'

export const configs = {
  development: {
    api: 'http://localhost:8888/wp-json/yabami/v1'
  },
  production: {
    api: 'https://yabaiwebyasan.com/wp-json/yabami/v1'
  }
}[env]

export const auth = app.auth()
export const providerTwitter = new firebase.auth.TwitterAuthProvider()
export default firebase
