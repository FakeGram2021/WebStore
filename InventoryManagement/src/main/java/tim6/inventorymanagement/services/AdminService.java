package tim6.inventorymanagement.services;

import tim6.inventorymanagement.models.entities.Admin;

public interface AdminService {
    Admin findByUsername(String username);
}
