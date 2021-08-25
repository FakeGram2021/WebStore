import ReportsClient from '../../../../lib/clients/ReportsClient';

const handler = async (request, response) => {
  const { method, query } = request;
  switch (method) {
    case 'GET':
      try {
        const outcome = await ReportsClient.get('v1/report/profit', {
          params: query
        });
        return response.status(outcome.status).json(outcome.data);
      } catch (error) {
        return response.status(error.response.status).json(error.response.data);
      }
    default:
      response.setHeader('Allow', 'GET');
      return response.status(405);
  }
};

export default handler;
