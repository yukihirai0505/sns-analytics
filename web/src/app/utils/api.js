import { client } from './client'
import Cookies from 'js-cookie'

export { saveUserToken, searchUser, getBeneficialTweets, embed }

async function saveUserToken(result) {
  let res = await client
    .post(
      '/user_token',
      {
        uid: result.user.uid,
        twitterAccessToken: result.credential.accessToken,
        twitterAccessTokenSecret: result.credential.secret
      },
      {
        withCredentials: true
      }
    )
    .catch(error => {
      console.log(error)
    })
  return res.data
}

async function searchUser(name) {
  let res = await client
    .post('/twitter/search_user', {
      q: name,
      jwt: Cookies.get('yabami_auth')
    })
    .catch(error => {
      console.log(error)
    })
  return res.data
}

async function getBeneficialTweets() {
  let res = await client
    .post('/twitter/beneficial', {
      jwt: Cookies.get('yabami_auth')
    })
    .catch(error => {
      console.log(error)
    })
  return res.data
}

async function embed() {
  let res = await client.get(`/twitter/embed?url=${url}`).catch(error => {
    console.log(error)
  })
  return res.data
}
