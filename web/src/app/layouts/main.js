import React from 'react'
import Meta from '../components/Meta'
import Header from '../components/Header'
import Footer from '../components/Footer'
export default ({ children }) => (
  <div>
    <Meta />
    <Header />
    {children}
    <Footer />
  </div>
)
