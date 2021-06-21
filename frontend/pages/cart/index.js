import Head from 'next/head';
import Link from 'next/link';
import Layout from '../../components/layout';
import CartItem from '../../components/cart/CartItem';
import { useState } from 'react';
import { CartLib } from '../../lib/cart';

const Cart = () => {
  const cartArticles = CartLib.getCartArticles();
  const [stateCartArticles, setStateCartArticles] = useState(cartArticles);
  const totalPrice = stateCartArticles.reduce((sum, article) => sum + article.price, 0);

  const handleArticleRemove = (index) => () => {
    CartLib.removeArticleFromCart(index);
    setStateCartArticles(CartLib.getCartArticles());
  };

  const clearCart = () => {
    CartLib.clearCart();
    setStateCartArticles([]);
  };
  return (
    <>
      <Head>
        <title>{'Shopping cart'}</title>
        <link rel='icon' href='/favicon.ico' />
      </Head>
      <Layout centered={!(stateCartArticles.length !== 0)}>
        {stateCartArticles.length !== 0 && (
          <div className='bg-white dark:bg-gray-800 rounded-lg shadow mt-8 p-2'>
            <span className='text-4xl font-bold text-center text-gray-800 ml-5'>Cart items:</span>
            {stateCartArticles.map((article, index) => (
              <div className='flex-none' key={(article.id, index)}>
                <CartItem
                  name={article.name}
                  description={article.description}
                  price={article.price}
                  imageUrl={article.imageUrl}
                  handleArticleRemove={handleArticleRemove(index)}
                />
              </div>
            ))}
            <hr className='divide-y-4 divide-yellow-600 divide-solid m-2' />
            <div className='flex justify-end'>
              <div className='flex-initial text-4xl font-bold text-center text-gray-800 m-4'>
                Total:
              </div>
              <div className='flex-initial text-4xl font-bold text-center text-gray-800 m-4'>
                {totalPrice}$
              </div>
            </div>
            <div className='flex justify-between'>
              <div className='flex-initial m-4'>
                <button
                  onClick={clearCart}
                  className='py-2 px-4 bg-red-600 hover:bg-indigo-700 focus:ring-indigo-500 focus:ring-offset-indigo-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg'>
                  Clear cart
                </button>
              </div>
              <div className='flex-initial m-4'>
                <Link href='/checkout'>
                  <button className='py-2 px-10 bg-indigo-600 hover:bg-indigo-700 focus:ring-indigo-500 focus:ring-offset-indigo-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg'>
                    Checkout
                  </button>
                </Link>
              </div>
            </div>
          </div>
        )}
        {stateCartArticles.length === 0 && (
          <div className='py-2 px-8 md:px-12'>
            <div className='text-2xl md:text-3xl font-bold text-gray-800 text-center'>
              No articles added to cart
            </div>
          </div>
        )}
      </Layout>
    </>
  );
};

export default Cart;
