import { auth, providerTwitter } from '../config'

const handleLogin = () => {
  auth.signInWithRedirect(providerTwitter)
}

const handleSignOut = () => {
  auth
    .signOut()
    .then(result => {
      console.log(result)
    })
    .catch(error => {
      console.log(error)
    })
}

const Content = () => <button onClick={handleLogin}>Login Twitter</button>

export default Content
