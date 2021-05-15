import cookie from "cookie";

export const CookiesLib = {
  getAuthToken,
};

function getAuthToken(req) {
  const cookieValues = cookie.parse(
    req ? req.headers.cookie || "" : document.cookie
  );
  return cookieValues.token ? cookieValues.token : null;
}
