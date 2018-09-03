import Page from '../layouts/main'

const Post = props => (
  <Page>
    <h1>Hi</h1>
    <h1>{props.show.name}</h1>
    <p>{props.show.summary.replace(/<[/]?p>/g, '')}</p>
    <img src={props.show.image.medium} />
  </Page>
)

Post.getInitialProps = async function(context) {
  // const { id } = context.query
  // const res = await fetch(`https://api.tvmaze.com/shows/${id}`)
  // const show = await res.json()
  //
  // console.log(`Fetched show: ${show.name}`)
  //
  // return { show }
  const show = {
    name: 'hoge',
    summary: 'hogehoge',
    image: {
      medium: 'hogehoge'
    }
  }
  return {
    show
  }
}

export default Post
