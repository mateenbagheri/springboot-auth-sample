package com.example.userservice.api;

import com.example.userservice.domain.Role;
import com.example.userservice.domain.UserApp;
import com.example.userservice.service.UserAppService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
// this means all the mappings in this class will be added after a /api map
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class UserController {
    private final UserAppService userAppService;

    @GetMapping("/users")
    public ResponseEntity<List<UserApp>> getUsers(){
        return ResponseEntity.ok().body(userAppService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserApp>saveUser(@RequestBody UserApp userApp){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userAppService.saveUserApp(userApp));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userAppService.saveRole(role));
    }

    @PostMapping("/user/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        userAppService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}


@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
