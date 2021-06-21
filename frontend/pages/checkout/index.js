import _ from 'lodash';
import axios from 'axios';
import Head from 'next/head';
import Layout from '../../components/layout';
import { useState } from 'react';
import { CartLib } from '../../lib/cart';
import { useRouter } from 'next/router';

const Checkout = () => {
  const [customer, setCustomer] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    zipcode: '',
    country: '',
  });
  const router = useRouter();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const orderItems = getOrderItemsFromCart();
    const order = {
      customer,
      orderItems,
    };
    await axios.post("/api/checkout", order);
    CartLib.clearCart();
    router.push("/");
  };

  const getOrderItemsFromCart = () => {
    const groupedArticlesById = _.groupBy(CartLib.getCartArticles().map((article) => article.id));
    return Object.keys(groupedArticlesById).map((key) => {
      return { articleId: key, quantity: groupedArticlesById[key].length };
    });
  };

  const handleChange = (name) => (event) => {
    const val = event.target.value;
    setCustomer({ ...customer, [name]: val });
  };

  return (
    <>
      <Head>
        <title>{'Checkout'}</title>
        <link rel='icon' href='/favicon.ico' />
      </Head>
      <Layout centered={true}>
        <form onSubmit={handleSubmit}>
          <div className='shadow overflow-hidden sm:rounded-md'>
            <h2 className='px-4 py-5 bg-white sm:p-6 text-xl font-medium leading-6 text-gray-900'>
              Personal Information
            </h2>
            <div className='px-4 py-5 bg-white sm:p-6'>
              <div className='grid grid-cols-6 gap-6'>
                <div className='col-span-6 sm:col-span-3'>
                  <label htmlFor='first_name' className='block text-sm font-medium text-gray-700'>
                    First name
                  </label>
                  <input
                    type='text'
                    name='first_name'
                    id='first_name'
                    autoComplete='given-name'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('firstName')}
                    required
                  />
                </div>

                <div className='col-span-6 sm:col-span-3'>
                  <label htmlFor='last_name' className='block text-sm font-medium text-gray-700'>
                    Last name
                  </label>
                  <input
                    type='text'
                    name='last_name'
                    id='last_name'
                    autoComplete='family-name'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('lastName')}
                    required
                  />
                </div>

                <div className='col-span-6 sm:col-span-4'>
                  <label htmlFor='email_address' className='block text-sm font-medium text-gray-700'>
                    Email address
                  </label>
                  <input
                    type='email'
                    name='email_address'
                    id='email_address'
                    autoComplete='email'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('email')}
                    required
                  />
                </div>

                <div className='col-span-6 sm:col-span-3'>
                  <label htmlFor='country' className='block text-sm font-medium text-gray-700'>
                    Country / Region
                  </label>
                  <input
                    type='text'
                    name='country'
                    id='country'
                    autoComplete='country_name'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('country')}
                    required
                  />
                </div>

                <div className='col-span-6'>
                  <label htmlFor='street_address' className='block text-sm font-medium text-gray-700'>
                    Street address
                  </label>
                  <input
                    type='text'
                    name='street_address'
                    id='street_address'
                    autoComplete='street-address'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('address')}
                    required
                  />
                </div>

                <div className='col-span-6 sm:col-span-6 lg:col-span-2'>
                  <label htmlFor='city' className='block text-sm font-medium text-gray-700'>
                    City
                  </label>
                  <input
                    type='text'
                    name='city'
                    id='city'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('city')}
                    required
                  />
                </div>
                <div className='col-span-6 sm:col-span-3 lg:col-span-2'>
                  <label htmlFor='postal_code' className='block text-sm font-medium text-gray-700'>
                    ZIP / Postal
                  </label>
                  <input
                    type='text'
                    name='postal_code'
                    id='postal_code'
                    autoComplete='postal-code'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('zipcode')}
                    required
                  />
                </div>
                <div className='col-span-6 sm:col-span-3 lg:col-span-2'>
                  <label htmlFor='phone number' className='block text-sm font-medium text-gray-700'>
                    Phone number
                  </label>
                  <input
                    type='text'
                    name='phone'
                    id='phone'
                    className='mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm text-lg border-gray-300 rounded-md'
                    onChange={handleChange('phone')}
                    required
                  />
                </div>
              </div>
            </div>
            <div className='px-4 py-3 bg-gray-50 text-right sm:px-6'>
              <button
                type='submit'
                className='inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'>
                Submit
              </button>
            </div>
          </div>
        </form>
      </Layout>
    </>
  );
};

export default Checkout;
