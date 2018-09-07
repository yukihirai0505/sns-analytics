import Cookies from 'js-cookie'
import { auth } from '../config'
import { getSelf, saveUserToken } from './api'

export const checkSignIn = async (component) => {
  auth.onAuthStateChanged(async user => {
    let res = await getSelf()
    if (res) {
      component.setState({user})
    }
  })

  const result = await auth.getRedirectResult().catch(error => {
    console.log('redirect result', error)
  })
  const user = result.user
  if (user) {
    const res = await saveUserToken(result)
    console.log(res)
    Cookies.set('yabami_auth', res.data.jwt)
    component.setState({user})
  }
}
