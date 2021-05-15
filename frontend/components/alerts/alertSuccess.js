const AlertSuccess = ({ text, handleClose }) => (
  <div className="space-x-2 bg-green-50 p-4 rounded flex items-center text-green-600 my-10 -mt-40 shadow-lg mx-auto max-w-2xl w-full">
    <div className="">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        className="fill-current w-5 pt-1"
        viewBox="0 0 24 24"
      >
        <path d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm-1.959 17l-4.5-4.319 1.395-1.435 3.08 2.937 7.021-7.183 1.422 1.409-8.418 8.591z" />
      </svg>
    </div>
    <h3 className="text-green-800 tracking-wider flex-1">{text}</h3>
    <button
      className="inline-flex items-center hover:bg-green-100 border border-green-50 hover:border-green-300 hover:text-green-900 focus:outline-none rounded-full p-2 hover:cursor-pointer"
      onClick={handleClose}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        className="fill-current w-4 h-4 pt-1"
        viewBox="0 0 24 24"
      >
        <path d="M24 20.188l-8.315-8.209 8.2-8.282-3.697-3.697-8.212 8.318-8.31-8.203-3.666 3.666 8.321 8.24-8.206 8.313 3.666 3.666 8.237-8.318 8.285 8.203z" />
      </svg>
    </button>
  </div>
);

export default AlertSuccess;
