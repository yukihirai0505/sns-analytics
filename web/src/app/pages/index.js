import React, { Component } from 'react'
import App from '../components/App'
import Link from 'next/link'
import { auth, providerTwitter } from '../config'
import axios from 'axios'

class Index extends Component {
  static async getInitialProps({}) {
    // NOTICE: It is not possible to call non-google APIs using the free Spark plan as explained on the Firebase pricing page:
    // ref: https://stackoverflow.com/questions/43415759/use-firebase-cloud-function-to-send-post-request-to-non-google-server
    const show = {
      show: {
        id: 1,
        name: 'hoge'
      }
    }
    const shows = [show]
    return { shows }
  }

  constructor(props) {
    super(props)
    this.state = {
      user: undefined,
      show: []
    }
  }

  async componentDidMount() {
    auth.onAuthStateChanged(user => {
      this.setState({ user })
    })

    const result = await auth.getRedirectResult().catch(error => {
      console.log('redirect result', error)
    })

    console.log('redirect result', result)
    const user = result.user
    if (user) {
      this.setState({ user })
      const res = await axios
        .post('https://yabaiwebyasan.com/wp-json/yabami/v1/user_token', {
          uid: user.uid,
          twitterAccessToken: result.credential.accessToken
        })
        .catch(function(error) {
          console.log(error)
        })
      console.log(res)
    }
  }

  handleLogin = () => {
    auth.signInWithRedirect(providerTwitter)
  }

  handleSignOut = () => {
    auth
      .signOut()
      .then(result => {
        this.setState({ user: undefined })
      })
      .catch(error => {
        console.log(error)
      })
  }

  render() {
    const { user } = this.state
    return (
      <App>
        <h1>{user ? `Login: ${user.displayName}` : 'Not Login'}</h1>
        <ul>
          {this.props.shows.map(({ show }) => (
            <li key={show.id}>
              <Link as={`/p/${show.id}`} href={`/post?id=${show.id}`}>
                <a>{show.name}</a>
              </Link>
            </li>
          ))}
        </ul>
        {user ? (
          <button onClick={this.handleSignOut}>Sign Out</button>
        ) : (
          <button onClick={this.handleLogin}>Login with Twitter</button>
        )}
      </App>
    )
  }
}

export default Index
