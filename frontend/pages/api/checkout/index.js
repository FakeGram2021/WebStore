import OrderingClient from '../../../lib/clients/OrderingClient';

const handler = async (request, response) => {
  const { method, body } = request;

  switch (method) {
    case 'POST':
      try {
        const outcome = await OrderingClient.post('v1/order', body);
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.response.status).json(error.response.data);
      }
    default:
      response.setHeader('Allow', 'POST');
      return response.status(405);
  }
};

export default handler;
