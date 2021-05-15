import Layout from "../components/layout";
import { useState } from "react";
import axios from "axios";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";

function Login() {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const router = useRouter();
  const [cookie, setCookie] = useCookies(["token"]);

  const handleCredentialsChange = (name) => (event) => {
    const val = event.target.value;
    setCredentials({ ...credentials, [name]: val });
  };

  const signIn = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post("api/auth", credentials);
      setCookie("token", response.data, {
        path: "/",
        maxAge: 3600,
        sameSite: true,
      });
      await router.push("/");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Layout>
      <main className="bg-white max-w-lg mx-auto p-8 md:p-12 my-10 rounded-lg shadow-2xl">
        <section>
          <h3 className="font-bold text-2xl">Administrator login</h3>
          <p className="text-gray-600 pt-2">Sign in to your account.</p>
        </section>

        <section className="mt-10">
          <form className="flex flex-col" method="POST" onSubmit={signIn}>
            <div className="mb-6 pt-3 rounded bg-gray-200">
              <label
                className="block text-gray-700 text-sm font-bold mb-2 ml-3"
                htmlFor="username"
              >
                Username
              </label>
              <input
                type="text"
                id="username"
                className="bg-gray-200 rounded w-full text-gray-700 focus:outline-none border-b-4 border-gray-300 focus:border-purple-600 transition duration-500 px-3 pb-3"
                value={credentials.username}
                onChange={handleCredentialsChange("username")}
              />
            </div>
            <div className="mb-6 pt-3 rounded bg-gray-200">
              <label
                className="block text-gray-700 text-sm font-bold mb-2 ml-3"
                htmlFor="password"
              >
                Password
              </label>
              <input
                type="password"
                id="password"
                className="bg-gray-200 rounded w-full text-gray-700 focus:outline-none border-b-4 border-gray-300 focus:border-purple-600 transition duration-500 px-3 pb-3"
                value={credentials.password}
                onChange={handleCredentialsChange("password")}
              />
            </div>
            <button
              className="bg-purple-600 hover:bg-purple-700 text-white font-bold py-2 rounded shadow-lg hover:shadow-xl transition duration-200"
              type="submit"
            >
              Sign In
            </button>
          </form>
        </section>
      </main>
    </Layout>
  );
}

export default Login;
