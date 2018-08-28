import * as functions from 'firebase-functions'
import next from 'next'

const dev = process.env.NODE_ENV !== 'production'
const app = next({ dev, conf: { distDir: 'next' } })
const handle = app.getRequestHandler()

const nextApp = functions.https.onRequest((request, response) => {
  console.log('File: ' + request.originalUrl)
  console.log('dev:', dev)
  // log the page.js file or resource being requested
  console.dir(request)
  const queryParams = { id: request.params.id }
  console.log('queryParams:', queryParams)
  if (queryParams.id) {
    const actualPath = `/post`
    return app
      .prepare()
      .then(() => handle(request, response, actualPath, queryParams))
  } else {
    return app.prepare().then(() => handle(request, response))
  }
})

export { nextApp }
