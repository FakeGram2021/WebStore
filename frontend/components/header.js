import Link from "next/link";
import HeaderIcon from "./headerIcon";
import HeaderItem from "./headerItem";
import { TokensLib } from "../lib/tokens";
import { CartLib } from "../lib/cart";

const Header = () => {
  const cartItemsCount = CartLib.getCartArticles().length;
  return (
    <nav className="bg-white shadow-lg">
      <div className="md:flex items-center justify-between py-2 px-8 md:px-12">
        <div className="text-2xl font-bold text-gray-800 md:text-3xl">
          <Link href="/">
            <a>A G E N T</a>
          </Link>
        </div>
        {TokensLib.getToken() ? (
          <div>
            <HeaderItem link={"/articles/create"} text={"Add new article"} />
            <HeaderItem link={"/reports"} text={"Sales reports"} />
            <span onClick={() => TokensLib.removeToken()}>
              <HeaderItem link={"/"} text={"Logout"} />
            </span>
          </div>
        ) : (
          <div className="md:flex">
            <HeaderIcon link={"/cart"}>
            <div className="absolute text-xs rounded-full -mt-1 -mr-2 px-1 font-bold top-0 right-0 bg-red-700 text-white">{ cartItemsCount }</div>
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" 
                d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z">
                </path>
              </svg>
            </HeaderIcon>
            <HeaderItem link={"/login"} text={"Login"} />
          </div>
        )}
      </div>
    </nav>
  );
};

export default Header;
