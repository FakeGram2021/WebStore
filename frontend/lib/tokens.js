import jwtDecode from "jwt-decode";
import Cookies from "universal-cookie";

export const TokensLib = {
  getToken,
  removeToken,
  decodeToken,
};

function getToken(req) {
  const cookies = new Cookies(req ? req.headers.cookie || "" : document.cookie);
  return cookies.get("token");
}

function removeToken() {
  const cookies = new Cookies(req ? req.headers.cookie || "" : document.cookie);
  cookies.remove("token");
}

function decodeToken(token) {
  try {
    return jwtDecode(token);
  } catch (error) {
    return null;
  }
}
