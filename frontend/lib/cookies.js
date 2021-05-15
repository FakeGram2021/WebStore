import Cookies from "universal-cookie";

export const CookiesLib = {
  getCookies,
};

function getCookies(req = undefined) {
  if (req) {
    return new Cookies(req.headers.cookie || "");
  } else if (typeof window !== "undefined") {
    return new Cookies(document.cookie);
  } else {
    return null;
  }
}
