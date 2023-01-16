package com.example.basespring.restapi;

import com.example.basespring.entity.User;
import com.example.basespring.repository.RoleRepository;
import com.example.basespring.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserApi {


    @Autowired
    UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/views/list")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> getList(){
        return ResponseEntity.ok(userDetailsServiceimpl.findAll());
    }

    @GetMapping("/views/list/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if (!optionalUser.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalUser.get());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> create(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userDetailsServiceimpl.save(user));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if ((!optionalUser.isPresent())){
            ResponseEntity.badRequest().build();
        }
        User existUser = optionalUser.get();

        existUser.setAvt(user.getAvt());
        existUser.setFirstName(user.getFirstName());
        existUser.setLastName(user.getLastName());
        existUser.setUsername(user.getUsername());
        existUser.setEmail(user.getEmail());
        existUser.setPhoneNumber(user.getPhoneNumber());
        existUser.setBirthday(user.getBirthday());
        existUser.setGender(user.getGender());
        existUser.setAddress(user.getAddress());
        existUser.setPassword(encoder.encode(user.getPassword()));
        existUser.setRoles(user.getRoles());
        return ResponseEntity.ok(userDetailsServiceimpl.save(existUser));
    }

    @PutMapping("/update/{id}/{keyword}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updated(@PathVariable Long id, @PathVariable String keyword , @RequestBody User user){
        Optional<User> optionalUser = userDetailsServiceimpl.findById(id);
        if ((!optionalUser.isPresent())){
            ResponseEntity.badRequest().build();
        }
        User existUser = optionalUser.get();

        if (keyword.equals("avt")){
            existUser.setAvt(user.getAvt());
        } else if (keyword.equals("firstName")){
            existUser.setFirstName(user.getFirstName());
        } else if (keyword.equals("lastName")){
            existUser.setLastName(user.getLastName());
        } else if (keyword.equals("username")){
            existUser.setUsername(user.getUsername());
        } else if (keyword.equals("email")){
            existUser.setEmail(user.getEmail());
        } else if (keyword.equals("phoneNumber")){
            existUser.setPhoneNumber(user.getPhoneNumber());
        } else if (keyword.equals("birthday")){
            existUser.setBirthday(user.getBirthday());
        } else if (keyword.equals("gender")){
            existUser.setGender(user.getGender());
        } else if (keyword.equals("address")){
            existUser.setAddress(user.getAddress());
        } else if (keyword.equals("password")){
            existUser.setPassword(encoder.encode(user.getPassword()));
        } else if (keyword.equals("roles")){
            existUser.setRoles(user.getRoles());
        } else {
            ResponseEntity.badRequest();
            new RuntimeException("Error: keyword not true");
        }
        return ResponseEntity.ok(userDetailsServiceimpl.save(existUser));
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if ((!userDetailsServiceimpl.findById(id).isPresent())){
            ResponseEntity.badRequest().build();
        }
        userDetailsServiceimpl.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
