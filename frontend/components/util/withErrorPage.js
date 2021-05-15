import Error from "next/error";

const withErrorPage = (Component) => {
  const withErrorPage = (props) => {
    const { errorStatus } = props;

    if (errorStatus === 404) {
      return <Error statusCode={errorStatus} />;
    } else {
      return <Component {...props} />;
    }
  };

  return withErrorPage;
};

export default withErrorPage;
