package com.example.userservice.service;

import com.example.userservice.domain.Role;
import com.example.userservice.domain.UserApp;
import com.example.userservice.repository.RoleRepo;
import com.example.userservice.repository.UserAppRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor  // adding all constructors needed
@Transactional
@Slf4j  // this provides us with log.info methods
public class UserAppServiceImplementation implements UserAppService, UserDetailsService {
    private final UserAppRepo userAppRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database.", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public UserApp saveUserApp(UserApp userApp) {
        log.info("Saving user {} to the database.", userApp.getName());
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return userAppRepo.save(userApp);
    }

    @Override
    public UserApp getUserApp(String username) {
        log.info("retrieving user with user name {}!", username);
        return userAppRepo.findByUsername(username);
    }

    @Override
    public List<UserApp> getUsers() {
        log.info("Getting a list of all users!");
        return userAppRepo.findAll();
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserApp userApp = userAppRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);

        userApp.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp userApp = userAppRepo.findByUsername(username);
        if (userApp == null) {
            log.error("User not found in database");
        } else {
            log.info("User found in database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        assert userApp != null;
        userApp.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(userApp.getUsername(), userApp.getPassword(), authorities);
    }
}
