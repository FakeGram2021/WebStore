import InventoryManagementClient from "../../lib/clients/InventoryManagementClient";

const handler = async (request, response) => {
  const { method, body } = request;

  switch (method) {
    case "POST":
      try {
        const outcome = await InventoryManagementClient.post("auth", body);
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
