import InventoryManagementClient from "../../../lib/clients/InventoryManagementClient";

const handler = async (request, response) => {
  const { method, body, query } = request;
  const { id } = query;

  switch (method) {
    case "GET":
      try {
        const outcome = await InventoryManagementClient.get(`articles/${id}`, {
          params: query,
          headers: { cookie: request.headers.cookie },
        });
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.response.status).json(error.response.data);
      }
    case "PUT":
      try {
        const outcome = await InventoryManagementClient.put(
          `articles/${id}`,
          body,
          { headers: { cookie: request.headers.cookie } }
        );
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.response.status).json(error.response.data);
      }
    case "DELETE":
      try {
        const outcome = await InventoryManagementClient.delete(
          `articles/${id}`,
          { headers: { cookie: request.headers.cookie } }
        );
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.response.status).json(error.response.data);
      }
    default:
      response.setHeader("Allow", "GET,PUT,DELETE");
      return response.status(405);
  }
};

export default handler;
