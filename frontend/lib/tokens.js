import jwtDecode from "jwt-decode";
import { CookiesLib } from "./cookies";

export const TokensLib = {
  getToken,
  setToken,
  removeToken,
  decodeToken,
};

function getToken(req = undefined) {
  const cookies = CookiesLib.getCookies(req);
  if (cookies) return cookies.get("token") ? cookies.get("token") : null;
  return null;
}

function setToken(token, req = undefined) {
  const cookies = CookiesLib.getCookies(req);
  return cookies.set("token", token, {
    path: "/",
    maxAge: 3600,
    sameSite: true,
  });
}

function removeToken(req = undefined) {
  const cookies = CookiesLib.getCookies(req);
  cookies.remove("token");
}

function decodeToken(token) {
  try {
    return jwtDecode(token);
  } catch (error) {
    return null;
  }
}
