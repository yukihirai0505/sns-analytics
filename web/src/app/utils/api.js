import { client } from './client'
import Cookies from 'js-cookie'

export {
  saveUserToken,
  searchUser,
  getBeneficialTweets,
  getSubscriptions,
  addSubscription
}

const saveUserToken = async result => {
  return post(
    '/user_token',
    {
      uid: result.user.uid,
      twitterAccessToken: result.credential.accessToken,
      twitterAccessTokenSecret: result.credential.secret
    },
    false
  )
}

const searchUser = async name => {
  return get(`/twitter/search_user?q=${name}`)
}

const getBeneficialTweets = () => {
  return get('/twitter/beneficial')
}

const getSubscriptions = () => {
  return get(`/user_subscription`)
}

const addSubscription = twitterAccountId => {
  return post(`/user_subscription/save`, {
    twitterAccountId: twitterAccountId
  })
}

const get = async (url, withAuthHeader = true) => {
  return request((headers) => client
    .get(url, {
      headers: headers
    }), withAuthHeader)
}

const post = (url, data = {}, withAuthHeader = true) => {
  return request((headers) => client
    .post(url, data, {
      headers: headers
    }), withAuthHeader)
}

const request = async (req, withAuthHeader = true) => {
  let headers = withAuthHeader
    ? { Authorization: 'Bearer ' + Cookies.get('yabami_auth') }
    : {}
  let res = await req(headers)
    .catch(error => {
      console.log(error)
    })
  return res.data
}
