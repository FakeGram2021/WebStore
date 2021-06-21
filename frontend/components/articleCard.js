import Image from "next/image";
import Link from "next/link";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ArticleCard = ({
  id,
  auth,
  article,
  name,
  description,
  price,
  amountInStock,
  imageUrl,
  handleAddToBasket,
  handleDeleteArticle,
}) => {
  const isAuthed = () => auth;
  const isInStock = () => amountInStock > 0;

  return (
    <div className="flex bg-white dark:bg-gray-800 rounded-lg shadow">
      <div className="flex-none w-24 md:w-48 relative">
        <Image
          src={imageUrl}
          alt={`Image of ${name}`}
          width={184}
          height={184}
          className="absolute rounded-lg inset-0 w-full h-full object-cover"
        />
      </div>
      <form className="flex-auto p-6">
        <div className="flex flex-wrap">
          <h1 className="text-xl font-semibold dark:text-gray-50">{name}</h1>
          <div className="text-xl font-semibold text-gray-500 dark:text-gray-300 ml-8">
            ${price}
          </div>
        </div>
        <div className="flex items-baseline mt-4 mb-6 text-gray-700 dark:text-gray-300">
          <div className="space-x-2 flex">
            <label className="text-center">{description}</label>
          </div>
        </div>
        <div className="flex mb-4 text-sm font-medium">
          {isAuthed() && (
            <>
              <Link href={`/articles/${id}`}>
                <a
                  type="button"
                  className="py-2 px-4  bg-purple-600 hover:bg-purple-700 focus:ring-purple-500 focus:ring-offset-purple-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg"
                >
                  Edit
                </a>
              </Link>
              <button
                type="button"
                className="py-2 px-4 bg-red-600 hover:bg-red-700 focus:ring-red-500 focus:ring-offset-indigo-200 text-white w-12 ml-4 transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg"
                onClick={() => handleDeleteArticle(id)}
              >
                <FontAwesomeIcon icon={faTrash} />
              </button>
            </>
          )}
          {!isAuthed() && isInStock() && (
            <button
              type="button"
              className="py-2 px-4 bg-indigo-600 hover:bg-indigo-700 focus:ring-indigo-500 focus:ring-offset-indigo-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg"
              onClick={handleAddToBasket(article)}
            >
              Add to shopping basket
            </button>
          )}
          {!isAuthed() && !isInStock() && (
            <button
              type="button"
              disabled
              className="py-2 px-4 bg-gray-600 hover:bg-gray-700 focus:ring-gray-500 focus:ring-offset-gray-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg"
            >
              Out of stock
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default ArticleCard;
