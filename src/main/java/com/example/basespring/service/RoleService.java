package com.example.basespring.service;

import com.example.basespring.entity.Role;
import com.example.basespring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role save(Role srole){
        return roleRepository.save(srole);
    }
}