import InventoryManagementClient from "../../../lib/clients/InventoryManagementClient";

const handler = async (request, response) => {
  const { method, body } = request;
  const { id } = request.query;

  switch (method) {
    case "PUT":
      try {
        const outcome = await InventoryManagementClient.put(
          `articles/${id}`,
          body,
          { headers: request.headers }
        );
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.status).json(error.data);
      }
    case "DELETE":
      try {
        const outcome = await InventoryManagementClient.delete(
          `articles/${id}`,
          { headers: request.headers }
        );
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.status).json(error.data);
      }
    default:
      response.setHeader("Allow", "POST");
      return response.status(405);
  }
};

export default handler;
