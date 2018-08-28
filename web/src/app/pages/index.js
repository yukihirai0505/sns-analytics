import React, { Component } from 'react'
import App from '../components/App'
import Link from 'next/link'
import fetch from 'isomorphic-unfetch'
import Content from '../components/Content'
import { auth } from '../config'

class Index extends Component {
  static async getInitialProps({}) {
    // NOTICE: It is not possible to call non-google APIs using the free Spark plan as explained on the Firebase pricing page:
    // ref: https://stackoverflow.com/questions/43415759/use-firebase-cloud-function-to-send-post-request-to-non-google-server
    // const res = await fetch('https://api.tvmaze.com/search/shows?q=batman')
    // const data = await res.json()
    // console.log(`Show data fetched. Count: ${data.length}`)
    // return {
    //   shows: data
    const show = {
      show: {
        id: 1,
        name: 'hoge'
      }
    }
    const shows = [show, show, show]
    return { shows }
  }

  constructor(props) {
    super(props)
    this.state = {
      user: undefined,
      show: []
    }
  }

  componentDidMount() {
    auth.onAuthStateChanged(user => {
      this.setState({ user })
    })
    auth
      .getRedirectResult()
      .then(result => {
        // TODO: save user data to DB
        console.log('redirect result', result)
      })
      .catch(error => {
        console.log('redirect result', error)
      })
  }

  render() {
    const { user } = this.state
    console.log(user)
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
        <Content />
      </App>
    )
  }
}

export default Index
