package com.project_discord_levche.discordlevche.service;

import com.project_discord_levche.discordlevche.model.RoleModel;
import com.project_discord_levche.discordlevche.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<RoleModel> findAll() {
        return roleRepository.findAll();
    }

    public RoleModel save(RoleModel role) {
        return roleRepository.save(role);
    }

    public RoleModel findById(Long id){
        return roleRepository.findById(id);
    }

    public RoleModel update(Long id, RoleModel roleDetails) {
        RoleModel role = findById(id);
        if(role != null){
            role.setName(roleDetails.getName());
            return roleRepository.save(role);
        }
        return null;
    }

    public void delete(Long id){
        roleRepository.deleteById(id);
    }
}

