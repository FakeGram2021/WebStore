import Link from "next/link";

const HeaderItem = ({ link, text }) => (
  <Link href={link}>
    <a className="text-gray-800 rounded hover:bg-gray-900 hover:text-gray-100 hover:font-medium py-2 px-2 md:mx-2">
      {text}
    </a>
  </Link>
);

export default HeaderItem;
