import axios from "axios";

const ReportsClient = axios.create({
  baseURL: `${process.env.REPORTS_API}`,
});

export default ReportsClient;
