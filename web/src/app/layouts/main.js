import React from 'react'
import Meta from '../components/meta'
import Header from '../components/header'
import Footer from '../components/footer'
export default ({ children }) => (
  <div>
    <Meta />
    <Header />
    {children}
    <Footer />
  </div>
)
