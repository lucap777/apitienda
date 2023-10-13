package com.company.intecap.apitienda.service;
import com.company.intecap.apitienda.model.Fabricante;
import com.company.intecap.apitienda.model.Role;
import com.company.intecap.apitienda.response.FabricanteResponseRest;
import com.company.intecap.apitienda.response.RoleResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.processing.RoundEnvironment;

public interface IRoleService {

    public ResponseEntity<RoleResponseRest> buscarRoles();

    public ResponseEntity<RoleResponseRest> buscarPorId(Long id);
    public ResponseEntity<RoleResponseRest> crear(Role role);
    public ResponseEntity<RoleResponseRest> actualizar(Role role, Long id);

    public ResponseEntity<RoleResponseRest> eliminar(Long id);

}
