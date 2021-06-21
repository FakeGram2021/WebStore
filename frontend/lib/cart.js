import { CookiesLib } from './cookies'

export const CartLib = {
  getCartArticles,
  addArticleToCart,
  removeArticleFromCart,
  clearCart
}

function getCartArticles() {
  const cookies = CookiesLib.getCookies();
  const cart = cookies?.get('cart');
  return cart ? cart : [] ;
}

function addArticleToCart(article) {
  const cookies = CookiesLib.getCookies();
  let cart = cookies.get('cart');
  if (cart) {
    cart.push(article);
  } else {
    cart = [article];
  }
  cookies.set('cart', cart);
}

function removeArticleFromCart(index) {
  const cookies = CookiesLib.getCookies();
  const cart = cookies.get('cart');
  cart.splice(index, 1);
  cookies.set('cart', cart);
}

function clearCart() {
  const cookies = CookiesLib.getCookies();
  cookies.set('cart', []);
}
