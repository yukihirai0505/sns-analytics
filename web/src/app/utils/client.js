import axios from 'axios'
import { configs } from '../config'

export { client }

const client = axios.create({
  baseURL: configs.api
})
