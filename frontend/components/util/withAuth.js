import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { CookiesLib } from "../../lib/cookies";

const withAuth = (Component) => {
  const withAuth = (props) => {
    const router = useRouter();

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    useEffect(() => {
      const token = CookiesLib.getAuthToken();
      setIsLoggedIn(token);
      if (!token) {
        router.push("/login");
      }
    }, []);

    if (isLoggedIn) {
      return <Component {...props} />;
    } else {
      return null;
    }
  };

  return withAuth;
};

export default withAuth;
