import Head from "next/head";
import Router from "next/router";
import InventoryManagementClient from "../lib/clients/InventoryManagementClient";
import ArticleCard from "../components/articleCard";
import Layout from "../components/layout";
import Pagination from "../components/pagination";
import handler from "./api/auth";
import axios from "axios";
import AlertError from "../components/alerts/alertError";
import AlertSuccess from "../components/alerts/alertSuccess";
import { useState } from "react";
import { TokensLib } from "../lib/tokens";
import { CartLib } from "../lib/cart";

function Index({ data, error, auth }) {
  const [alertSuccess, setAlertSuccess] = useState(false);
  const [alertError, setAlertError] = useState(false);

  const handleAddToBasket = (article) => () => {
    CartLib.addArticleToCart(article);
    setAlertSuccess(true);
    setTimeout(() => {
      setAlertSuccess(false);
    }, 1250);
  }

  const handleArticleDelete = async (id) => {
    console.log(id);
    try {
      await axios.delete(`/api/articles/${id}`, {
        headers: {
          Authorization: `Bearer ${TokensLib.getToken()}`,
        },
      });
      Router.reload();
    } catch (error) {
      setAlertError(true);
    }
  };

  return (
    <>
      <Head>
        <title>Articles</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <Layout auth={handler}>
        {error && (
          <AlertError text={"Could not load data"} handleClose={() => null} />
        )}
        {alertSuccess && (
          <AlertSuccess
            text={"Article succesfully added to cart"}
            handleClose={() => setAlertSuccess(false)}
          />
        )}
        {alertError && (
          <AlertError
            text={"Article could not be removed"}
            handleClose={() => setAlertError(false)}
          />
        )}
        {data && (
          <>
            <div className="grid gap-16 grid-cols-2 align-middle justify-center">
              {data.content &&
                data.content.map((article) => (
                  <ArticleCard
                    key={article.id}
                    auth={auth}
                    article={article}
                    id={article.id}
                    name={article.name}
                    description={article.description}
                    price={article.price}
                    amountInStock={article.amountInStock}
                    imageUrl={article.imageUrl}
                    handleAddToBasket={handleAddToBasket}
                    handleDeleteArticle={handleArticleDelete}
                  />
                ))}
            </div>
            <Pagination
              pageNumber={data.number}
              first={data.number === 0}
              last={data.number === data.totalPages - 1 && data.number !== 0}
              handlePreviousPageChange={() =>
                `/?page=${encodeURIComponent(data.number - 1)}`
              }
              handleNextPageChange={() =>
                `/?page=${encodeURIComponent(data.number + 1)}`
              }
            />
          </>
        )}
      </Layout>
    </>
  );
}

export async function getServerSideProps(context) {
  try {
    const response = context.query.page
      ? await InventoryManagementClient.get(
          `articles?size=4&page=${encodeURIComponent(context.query.page)}`
        )
      : await InventoryManagementClient.get("articles?size=4&page=0");
    return {
      props: {
        data: response.data,
        error: null,
        auth: TokensLib.getToken(context.req),
      },
    };
  } catch (error) {
    return {
      props: {
        data: null,
        error: error.message,
        auth: TokensLib.getToken(context.req),
      },
    };
  }
}

export default Index;
