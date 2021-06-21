import Head from "next/head";
import Layout from "../../components/layout";
import InventoryManagementClient from "../../lib/clients/InventoryManagementClient";
import { useState } from "react";
import axios from "axios";
import ArticleEdit from "../../components/articleEdit";
import AlertSuccess from "../../components/alerts/alertSuccess";
import AlertError from "../../components/alerts/alertError";
import withAuth from "../../components/util/withAuth";
import withErrorPage from "../../components/util/withErrorPage";
import { TokensLib } from "../../lib/tokens";

function Article({ data, errorStatus }) {
  const [article, setArticle] = useState(data);
  const [alertSuccess, setAlertSuccess] = useState(false);
  const [alertError, setAlertError] = useState(false);

  const handleArticleFormInputChange = (name) => (event) => {
    const val = event.target.value;
    setArticle({ ...article, [name]: val });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      await axios.put(`/api/articles/${article.id}`, article, {
        headers: {
          Authorization: `Bearer ${TokensLib.getToken()}`,
        },
      });
      setAlertSuccess(true);
      setTimeout(() => {
        setAlertSuccess(false);
      }, 1250);
    } catch (error) {
      setAlertError(true);
      setTimeout(() => {
        setAlertError(false);
      }, 1250);
    }
  };

  return (
    <>
      <Head>
        <title>{`Article ${article.name}`} </title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Layout>
        {alertSuccess && (
          <AlertSuccess
            text={"Article successfully updated"}
            handleClose={() => setAlertSuccess(false)}
          />
        )}

        {errorStatus && (
          <AlertError
            text={"Article could not be loaded"}
            handleClose={() => null}
          />
        )}

        {alertError && (
          <AlertError
            text={"Article could not be updated"}
            handleClose={() => setAlertError(false)}
          />
        )}

        {!errorStatus && (
          <ArticleEdit
            article={article}
            setArticle={setArticle}
            handleArticleFormInputChange={handleArticleFormInputChange}
            handleSubmit={handleSubmit}
          />
        )}
      </Layout>
    </>
  );
}

export async function getServerSideProps(context) {
  try {
    const response = await InventoryManagementClient.get(
      `articles/${encodeURIComponent(context.params.id)}`
    );
    return {
      props: {
        data: response.data,
        errorStatus: null,
      },
    };
  } catch (error) {
    return {
      props: {
        data: {
          name: "",
          description: "",
          price: "",
          amountInStock: "",
          imageUrl:
            "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
        },
        errorStatus: error.response ? error.response.status : "No response",
      },
    };
  }
}

export default withAuth(withErrorPage(Article));
