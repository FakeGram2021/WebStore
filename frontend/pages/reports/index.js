import Head from 'next/head';
import Layout from '../../components/layout';
import withAuth from "../../components/util/withAuth";

const Report = () => {
  return (
    <>
      <Head>
        <title>{'Sales reports'}</title>
        <link rel='icon' href='/favicon.ico' />
      </Head>
      <Layout centered={false}>
      </Layout>
    </>
  )
}

export default withAuth(Report);