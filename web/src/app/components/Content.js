import firebase, {providerTwitter} from '../config'

const handleLogin = () => {
  firebase.auth().signInWithRedirect(providerTwitter)
}

const handleSignOut = () => {
  firebase.auth().signOut().then(result => {
    console.log(result);
  }).catch(error => {
    console.log(error);
  });
}

const Content = () => (
  <button onClick={handleLogin}>Login Twitter</button>
)

export default Content
