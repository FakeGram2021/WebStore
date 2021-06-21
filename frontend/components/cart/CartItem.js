import Image from "next/image";

const CartItem = ({
  name,
  description,
  price,
  imageUrl,
  handleArticleRemove
}) => {
  return (
    <div className="flex items-center bg-white dark:bg-gray-800 rounded-lg shadow m-3 p-2">
      <div className="flex-none w-20 md:w-40 relative">
        <Image
          src={imageUrl}
          width={184}
          height={184}
          className="absolute rounded-lg inset-0 w-full h-full object-cover"
        />
      </div>
      <div className="flex-none  mx-8">
        <h1 className="text-4xl font-semibold dark:text-gray-50">{name}</h1>
      </div>
      <div className="flex-none flex-grow text-right mr-16">
          <div className="text-4xl font-semibold text-gray-800 dark:text-gray-300 ml-auto">
            ${price}
          </div>
      </div>
      <div onClick={handleArticleRemove} className="flex-none mr-6">
        <svg className="w-8 h-8" fill="none" stroke="red" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="4" d="M6 18L18 6M6 6l12 12"></path>
        </svg>
      </div>
    </div>
  )
}

export default CartItem;