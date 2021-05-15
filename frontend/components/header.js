import Link from "next/link";
import HeaderItem from "./headerItem";
import { useCookies } from "react-cookie";

const Header = () => {
  const [cookie, setCookie, removeCookie] = useCookies(["token"]);
  return (
    <nav className="bg-white shadow-lg">
      <div className="md:flex items-center justify-between py-2 px-8 md:px-12">
        <div className="text-2xl font-bold text-gray-800 md:text-3xl">
          <Link href="/">
            <a>A G E N T</a>
          </Link>
        </div>
        {cookie["token"] ? (
          <div>
            <HeaderItem link={"/articles/create"} text={"Add new article"} />
            <span onClick={() => removeCookie("token")}>
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
