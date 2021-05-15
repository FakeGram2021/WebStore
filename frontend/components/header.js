import Link from "next/link";
import HeaderItem from "./headerItem";
import { TokensLib } from "../lib/tokens";

const Header = () => {
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
            <span onClick={() => TokensLib.removeToken()}>
              <HeaderItem link={"/"} text={"Logout"} />
            </span>
          </div>
        ) : (
          <HeaderItem link={"/login"} text={"Login"} />
        )}
      </div>
    </nav>
  );
};

export default Header;
