import Header from "./header";

const Layout = ({ centered=true, children }) => (
  <>
    {centered && 
      <main className="dark:bg-gray-800 bg-white relative min-h-full">
        <Header />
        <div className="flex items-center justify-center mt-16 h-5/6">
          <div className="container mx-auto">{children}</div>
        </div>
      </main>
    }
    {!centered &&
      <div className="dark:bg-gray-800 bg-white relative min-h-full">
        <Header />
          <div className="container mx-auto">{children}</div>
      </div>
    }

  </>
);
export default Layout;
