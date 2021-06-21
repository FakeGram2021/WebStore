import Link from "next/link";

const HeaderIcon = ({link, children}) => (
  <Link href={link}>
    <div slot="icon" className="relative py-2 px-2 md:mx-2">
      {children}
    </div>
  </Link>
);

export default HeaderIcon;