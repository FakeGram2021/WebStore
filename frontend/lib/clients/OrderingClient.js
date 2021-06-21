import axios from "axios";

const OrderingClient = axios.create({
  baseURL: `${process.env.ORDERING_API}`,
});

export default OrderingClient;
