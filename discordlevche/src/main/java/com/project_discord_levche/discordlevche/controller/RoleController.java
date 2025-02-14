package com.project_discord_levche.discordlevche.controller;

import com.project_discord_levche.discordlevche.model.RoleModel;
import com.project_discord_levche.discordlevche.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discordlevche/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleModel> getAllRoles() {
        return roleService.findAll();
    }

    @PostMapping
    public RoleModel createRole(@RequestBody RoleModel role) {
        return roleService.save(role);
    }

    @GetMapping("/{id}")
    public RoleModel getRoleById(@PathVariable Long id){
        return roleService.findById(id);
    }

    @PutMapping("/{id}")
    public RoleModel updateRole(@PathVariable Long id, @RequestBody RoleModel roleDetails){
        return roleService.update(id, roleDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id){
        roleService.delete(id);
    }
}

