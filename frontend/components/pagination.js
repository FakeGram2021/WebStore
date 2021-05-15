import Link from "next/link";

const Pagination = ({
  pageNumber,
  first,
  last,
  previousPageLink,
  nextPageLink,
}) => (
  <div className="flex flex-col items-center my-12">
    <div className="flex text-gray-700">
      <div className="flex h-8 font-medium ">
        {!first && (
          <LeftLine
            previousPageLink={previousPageLink}
            previousPage={pageNumber - 1}
          />
        )}
        <div className="w-8 md:flex justify-center items-center hidden  cursor-pointer leading-5 transition duration-150 ease-in  border-t-2 border-transparent">
          {pageNumber}
        </div>
        {!last && (
          <RightLine nextPageLink={nextPageLink} nextPage={pageNumber + 1} />
        )}
      </div>
    </div>
  </div>
);

const LeftLine = ({ previousPageLink, previousPage }) => (
  <div className="h-8 w-8 mr-1 flex justify-center items-center  cursor-pointer">
    <Link href={{ pathname: previousPageLink, query: { page: previousPage } }}>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="100%"
        height="100%"
        fill="none"
        viewBox="0 0 24 24"
        stroke="indigo"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
        className="feather feather-chevron-left w-4 h-4"
      >
        <polyline points="15 18 9 12 15 6" />
      </svg>
    </Link>
  </div>
);

const RightLine = ({ nextPageLink, nextPage }) => (
  <div className="h-8 w-8 ml-1 flex justify-center items-center  cursor-pointer">
    <Link href={{ pathname: nextPageLink, query: { page: nextPage } }}>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="100%"
        height="100%"
        fill="none"
        viewBox="0 0 24 24"
        stroke="indigo"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
        className="feather feather-chevron-right w-4 h-4"
      >
        <polyline points="9 18 15 12 9 6" />
      </svg>
    </Link>
  </div>
);

export default Pagination;
