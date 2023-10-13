package com.company.intecap.apitienda.response;

public class RoleResponseRest extends ResponseRest {
    private RoleResponse roleResponse = new RoleResponse();

    public RoleResponse getRoleResponse() {
        return roleResponse;
    }

    public void setRoleResponse(RoleResponse roleResponse) {
        this.roleResponse = roleResponse;
    }
}
