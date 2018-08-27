import firebase from 'firebase/app'
import 'firebase/auth'

if (!firebase.apps.length) {
  firebase.initializeApp({
    apiKey: 'AIzaSyCQzyZ2dRUl_dQFeCLqRxrHJEMD9a5D0WI',
    authDomain: 'snsanalytics-ef74a.firebaseapp.com',
    projectId: 'snsanalytics-ef74a'
  });
}

export const providerTwitter = new firebase.auth.TwitterAuthProvider()
export default firebase
