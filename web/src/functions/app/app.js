import next from 'next'
import express from 'express'
import cors from 'cors'
import * as functions from 'firebase-functions'

const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev, conf: { distDir: 'next' } })
const handle = app.getRequestHandler()

const server = express()
server.use(cors())
server.get('/p/:id', (req, res) => {
  const actualPage = '/post'
  const queryParams = { id: req.params.id }
  app.render(req, res, actualPage, queryParams)
})
server.get('*', (req, res) => handle(req, res))

const nextApp = functions.https.onRequest(async (req, res) => {
  await app.prepare()
  return server(req, res)
})

export { nextApp }
