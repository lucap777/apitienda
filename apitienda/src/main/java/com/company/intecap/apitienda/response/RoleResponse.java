package com.company.intecap.apitienda.response;

import com.company.intecap.apitienda.model.Role;

import java.util.List;

public class RoleResponse {
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
