import React, { Component } from 'react'
import App from '../components/App'
import Link from 'next/link'
import { auth, providerTwitter, configs } from '../config'
import axios from 'axios'
import cookies from 'next-cookies'
import Cookies from 'js-cookie'

class Index extends Component {
  static async getInitialProps(ctx) {
    // NOTICE: It is not possible to call non-google APIs using the free Spark plan as explained on the Firebase pricing page:
    // ref: https://stackoverflow.com/questions/43415759/use-firebase-cloud-function-to-send-post-request-to-non-google-server
    const { yabami_auth } = cookies(ctx)
    return {}
  }

  constructor(props) {
    super(props)
    this.state = {
      user: undefined,
      twitterUsers: [],
      categories: []
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
      const res = await axios
        .post(
          `${configs.api}/user_token`,
          {
            uid: user.uid,
            twitterAccessToken: result.credential.accessToken,
            twitterAccessTokenSecret: result.credential.secret
          },
          {
            withCredentials: true
          }
        )
        .catch(function(error) {
          console.log(error)
        })
      console.log(res)
      Cookies.set('yabami_auth', res.data.data.jwt)
      this.setState({ user })
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

  searchTwitterUser = async () => {
    const res = await axios
      .post(`${configs.api}/twitter/search_user`, {
        q: 'iHayato',
        jwt: Cookies.get('yabami_auth')
      })
      .catch(function(error) {
        console.log(error)
      })
    console.log(res.data.data)
    this.setState({ twitterUsers: res.data.data })

    const categoryRes = await axios
      .get(`${configs.api}/category`)
      .catch(function(error) {
        console.log(error)
      })
    this.setState({ categories: categoryRes.data.data })
  }

  render() {
    const { user, twitterUsers, categories } = this.state
    return (
      <App>
        <h1>{user ? `Login: ${user.displayName}` : 'Not Login'}</h1>
        <ul>
          {twitterUsers.map(twitterUser => (
            <li key={twitterUser.id}>
              <Link
                as={`/p/${twitterUser.name}`}
                href={`/post?id=${twitterUser.name}`}
              >
                <a>{twitterUser.screen_name}</a>
              </Link>
            </li>
          ))}
        </ul>
        <ul>
          {categories.map((category, index) => (
            <li key={index}>{category.name}</li>
          ))}
        </ul>
        {user && (
          <button onClick={this.searchTwitterUser}>Search Twitter User</button>
        )}
        {user ? (
          <div>
            <button onClick={this.handleSignOut}>Sign Out</button>
          </div>
        ) : (
          <button onClick={this.handleLogin}>Login with Twitter</button>
        )}
      </App>
    )
  }
}

export default Index
