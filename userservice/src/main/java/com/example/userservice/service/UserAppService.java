package com.example.userservice.service;

import com.example.userservice.domain.Role;
import com.example.userservice.domain.UserApp;

import java.util.List;

public interface UserAppService {
    UserApp saveUserApp(UserApp userApp);
    UserApp getUserApp(String username);

    List<UserApp> getUsers();

    Role saveRole(Role role);

    void  addRoleToUser(String username, String roleName);
}
