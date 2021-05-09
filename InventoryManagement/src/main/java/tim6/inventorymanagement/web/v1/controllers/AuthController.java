package tim6.inventorymanagement.web.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import tim6.inventorymanagement.security.TokenUtilities;
import tim6.inventorymanagement.services.AdminService;
import tim6.inventorymanagement.web.v1.dto.auth.LoginDTO;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin
public class AuthController {

    AdminService adminService;

    UserDetailsService userDetailsService;

    AuthenticationManager authenticationManager;

    TokenUtilities tokenUtilities;

    @Autowired
    public AuthController(
            AdminService adminService,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            TokenUtilities tokenUtilities) {
        this.adminService = adminService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenUtilities = tokenUtilities;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(), loginDTO.getPassword());
        this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        try {
            UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(loginDTO.getUsername());
            String token = this.tokenUtilities.generateToken(userDetails);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
