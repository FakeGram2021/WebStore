package tim6.inventorymanagement.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tim6.inventorymanagement.models.entities.Admin;
import tim6.inventorymanagement.services.AdminService;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class AdminDetailsServiceImpl implements UserDetailsService {

    private final AdminService adminService;

    @Autowired
    public AdminDetailsServiceImpl(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = this.adminService.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException(
                    String.format("There is no user with username%s", username));
        }

        List<GrantedAuthority> grantedAuthorities =
                new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return new org.springframework.security.core.userdetails.User(
                admin.getUsername(), admin.getPassword(), grantedAuthorities);
    }
}
