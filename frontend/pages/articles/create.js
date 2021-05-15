import Head from "next/head";
import Layout from "../../components/layout";
import { useState } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";
import ArticleEdit from "../../components/articleEdit";
import AlertSuccess from "../../components/alerts/alertSuccess";
import AlertError from "../../components/alerts/alertError";
import withAuth from "../../components/util/withAuth";

function CreateArticle() {
  const [article, setArticle] = useState({
    name: "",
    description: "",
    price: "",
    amountInStock: "",
    imageUrl:
      "https://res.cloudinary.com/dtddfx5ww/image/upload/v1621075575/WebStore/placeholder.jpg",
  });
  const [alertSuccess, setAlertSuccess] = useState(false);
  const [alertError, setAlertError] = useState(false);
  const [cookie] = useCookies(["token"]);

  const handleArticleFormInputChange = (name) => (event) => {
    const val = event.target.value;
    setArticle({ ...article, [name]: val });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      await axios.post("/api/articles", article, {
        headers: {
          Authorization: `Bearer ${cookie["token"]}`,
        },
      });
      setAlertSuccess(true);
    } catch (error) {
      setAlertError(true);
    }
  };

  return (
    <>
      <Head>
        <title>{"Create article"} </title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <Layout>
        {alertSuccess && (
          <AlertSuccess
            text={"Article successfully added"}
            handleClose={() => setAlertSuccess(false)}
          />
        )}

        {alertError && (
          <AlertError
            text={"Article could not be added"}
            handleClose={() => setAlertError(false)}
          />
        )}
        <ArticleEdit
          article={article}
          setArticle={setArticle}
          handleArticleFormInputChange={handleArticleFormInputChange}
          handleSubmit={handleSubmit}
        />
      </Layout>
    </>
  );
}

export default withAuth(CreateArticle);
