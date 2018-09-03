import React, { Component } from 'react'
import Page from '../layouts/main'
import TwitterEmbed from '../components/twitterEmbed'
import Link from 'next/link'
import { auth, providerTwitter, configs } from '../config'
import cookies from 'next-cookies'
import Cookies from 'js-cookie'
import {
  addSubscription,
  embed,
  getBeneficialTweets,
  saveUserToken,
  searchUser
} from '../utils/api'

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
      tweets: []
    }
  }

  async componentDidMount() {
    auth.onAuthStateChanged(user => {
      this.setState({ user })
    })

    const result = await auth.getRedirectResult().catch(error => {
      console.log('redirect result', error)
    })
    const user = result.user
    if (user) {
      const res = await saveUserToken(result)
      console.log(res)
      Cookies.set('yabami_auth', res.data.jwt)
      this.setState({ user })
    }

    const tweets = await getBeneficialTweets()
    if (tweets && tweets.data) {
      this.setState({ tweets: tweets.data })
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
    let searchUserName = document.getElementById('search-user-name')
    console.log(searchUserName.value)
    const users = await searchUser(searchUserName.value)
    if (users) {
      this.setState({ twitterUsers: users.data })
    }
  }

  addSubscription = async twitterAccountId => {
    const res = await addSubscription(twitterAccountId)
    console.log(res)
  }

  render() {
    const { user, twitterUsers, tweets } = this.state
    return (
      <Page>
        <h1>{user ? `Login: ${user.displayName}` : 'Not Login'}</h1>
        {user && (
          <div>
            <input type="text" id="search-user-name" />
            <button onClick={this.searchTwitterUser}>
              Search Twitter User
            </button>
          </div>
        )}
        <ul>
          {twitterUsers.map(twitterUser => (
            <li key={twitterUser.id_str}>
              <Link
                as={`/p/${twitterUser.id_str}`}
                href={`/post?id=${twitterUser.name}`}
              >
                <a>{twitterUser.screen_name}</a>
              </Link>
              <button onClick={() => this.addSubscription(twitterUser.id_str)}>
                Add
              </button>
            </li>
          ))}
        </ul>
        {tweets.map((twitter, index) => {
          return <TwitterEmbed twitter={twitter} index={index} />
        })}
        {user ? (
          <div>
            <button onClick={this.handleSignOut}>Sign Out</button>
          </div>
        ) : (
          <button onClick={this.handleLogin}>Login with Twitter</button>
        )}
      </Page>
    )
  }
}

export default Index
