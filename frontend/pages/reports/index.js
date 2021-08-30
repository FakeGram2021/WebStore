import axios from 'axios';
import Head from 'next/head';
import Layout from '../../components/layout';
import withAuth from '../../components/util/withAuth';
import { useState } from 'react';
import dayjs from 'dayjs';
import clsx from 'clsx';
import { PieChart, Pie, Tooltip, Cell } from 'recharts';

const Report = () => {
  const [articleStatistics, setArticleStatistics] = useState([]);
  const [selectedReport, setSelectedReport] = useState('mostSold');
  const [dates, setDates] = useState({
    from: new Date(),
    to: new Date(),
  });
  const pieChartColors = ['#7C3AED', '#ed00b4', '#ff0074', '#ff683b', '#ffa600'];

  const generateReport = async (event) => {
    event.preventDefault();
    const response = await axios.get(`api/report/${selectedReport}`, {
      params: {
        from: dates.from,
        to: dates.to,
      },
    });
    let chartData = [];
    if (selectedReport === 'mostSold') {
      chartData = response.data.map((articleStats) => ({
        value: articleStats.soldQuantity,
        name: articleStats.article.name,
      }));
    } else {
      chartData = response.data.map((articleStats) => ({
        value: articleStats.profit,
        name: articleStats.article.name,
      }));
    }
    setArticleStatistics(chartData);
  };

  const handleChange = (name) => (event) => {
    const val = event.target.value;
    const date = dayjs(new Date(val));
    setDates({ ...dates, [name]: date.format('DDMMYYYY') });
  };

  return (
    <>
      <Head>
        <title>{'Sales reports'}</title>
        <link rel='icon' href='/favicon.ico' />
      </Head>
      <Layout centered={false}>
        <div className='shadow min-h-screen rounded-md mt-5'>
          <div className='flex p-6'>
            <div
              className={clsx('w-1/2 border-b-2 text-center ', {
                'border-purple-500': selectedReport === 'mostSold',
                'hover:border-purple-500': selectedReport !== 'mostSold',
              })}
              onClick={() => setSelectedReport('mostSold')}>
              <button className='text-2xl antialiased font-medium focus:outline-none'>Most sold articles report</button>
            </div>
            <div
              className={clsx('w-1/2 border-b-2 text-center hover:border-purple-500', {
                'border-purple-500': selectedReport === 'profit',
                'hover:border-purple-500': selectedReport !== 'profit',
              })}
              onClick={() => setSelectedReport('profit')}>
              <button className='text-2xl antialiased font-medium focus:outline-none'>
                Most profitable articles report
              </button>
            </div>
          </div>
          <form onSubmit={generateReport}>
            <div className='flex justify-center pt-6'>
              <div className='flex-none w-1/4'>
                <span className='mx-6'>From date:</span>
                <input
                  required
                  className='shadow-md rounded-md h-10'
                  label='From date'
                  type='date'
                  placeholder='dd-mm-yyyy'
                  onChange={handleChange('from')}
                />
              </div>
              <div className='flex-none w-1/4'>
                <span className='mx-6'>To date:</span>
                <input
                  required
                  className='shadow-md rounded-md h-10'
                  label='To date'
                  type='date'
                  placeholder='dd-mm-yyyy'
                  onChange={handleChange('to')}
                />
              </div>
              <div className='flex-1 self-center'>
                <button
                  type='submit'
                  className='rounded-md shadow-md bg-purple-600 hover:bg-purple-700 focus:ring-purple-500 focus:ring-offset-purple-200 text-white w-1/4 text-center font-semibold h-8'>
                  Generate
                </button>
              </div>
            </div>
          </form>

          {articleStatistics.length !== 0 && (
            <div style={{ textAlign: '-webkit-center' }}>
              <PieChart width={750} height={500}>
                <Pie data={articleStatistics} fill='#8884d8' label>
                  {articleStatistics.map((entry, index) => (
                    <Cell key={entry.name} fill={pieChartColors[index]} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </div>
          )}
        </div>
      </Layout>
    </>
  );
};

export default withAuth(Report);
