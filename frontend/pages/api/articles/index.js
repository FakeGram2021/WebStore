import InventoryManagementClient from "../../../lib/clients/InventoryManagementClient";

const handler = async (request, response) => {
  const { method, body, query } = request;

  switch (method) {
    case "GET":
      try {
        const outcome = await InventoryManagementClient.get("articles", {
          params: query,
          headers: { cookie: request.headers.cookie },
        });
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.response.status).json(error.response.data);
      }
    case "POST":
      try {
        const outcome = await InventoryManagementClient.post("articles", body, {
          headers: { cookie: request.headers.cookie },
        });
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.response.status).json(error.response.data);
      }
    default:
      response.setHeader("Allow", "GET, POST");
      return response.status(405);
  }
};

export default handler;
