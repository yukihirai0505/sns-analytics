import App from '../components/App'
import Link from 'next/link'
import fetch from 'isomorphic-unfetch'
import Content from '../components/Content'
import firebase from '../config'

const showTwitterStatus = () => {
  firebase.auth().onAuthStateChanged(user => {
    console.log('onAuth', user)
  })
  const currentUser = firebase.auth().currentUser
  firebase
    .auth()
    .getRedirectResult()
    .then(result => {
      console.log(result)
    })
    .catch(error => {
      console.log(error)
    })
  console.log(currentUser)
}

const Index = props => (
  <App>
    <h1>Batman TV Shows</h1>
    {showTwitterStatus()}
    <ul>
      {props.shows.map(({ show }) => (
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

Index.getInitialProps = async function() {
  // NOTICE: It is not possible to call non-google APIs using the free Spark plan as explained on the Firebase pricing page:
  // ref: https://stackoverflow.com/questions/43415759/use-firebase-cloud-function-to-send-post-request-to-non-google-server
  // const res = await fetch('https://api.tvmaze.com/search/shows?q=batman')
  // const data = await res.json()
  // console.log(`Show data fetched. Count: ${data.length}`)
  // return {
  //   shows: data
  // }
  const show = {
    show: {
      id: 1,
      name: 'hoge'
    }
  }
  return {
    shows: [show, show, show]
  }
}

export default Index
