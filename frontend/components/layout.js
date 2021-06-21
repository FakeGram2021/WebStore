import Header from "./header";

const Layout = ({ centered=true, children }) => (
  <>
    {centered && 
      <main className="dark:bg-gray-800 bg-white relative h-screen">
        <Header />
        <div className="flex items-center justify-center mt-16 h-5/6">
          <div className="container mx-auto">{children}</div>
        </div>
      </main>
    }
    {!centered &&
      <main className="dark:bg-gray-800 bg-white relative h-screen">
        <Header />
          <div className="container mx-auto">{children}</div>
      </main>
    }

  </>
);
export default Layout;
