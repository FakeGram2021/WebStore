package tim6.inventorymanagement.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim6.inventorymanagement.models.entities.Admin;
import tim6.inventorymanagement.repositories.AdminRepository;
import tim6.inventorymanagement.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin findByUsername(String username) {
        return this.adminRepository.findByUsername(username).orElse(null);
    }
}
