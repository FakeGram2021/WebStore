import axios from "axios";

const InventoryManagementClient = axios.create({
  baseURL: `${process.env.INVENTORY_MANAGEMENT_API}`,
});

export default InventoryManagementClient;
