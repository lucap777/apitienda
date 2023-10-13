package com.company.intecap.apitienda.controllers;

import com.company.intecap.apitienda.model.Role;
import com.company.intecap.apitienda.response.RoleResponseRest;
import com.company.intecap.apitienda.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")  //prefijo de la ruta de la api rest  http://localhost:8080/api/v1
public class RoleRestController {

    //implementar los metodos de la clase RoleServiceImpl
    @Autowired //inyeccion de dependencias
    private IRoleService service;

    @GetMapping("/roles") //ruta de la api rest http://localhost:8083/api/v1/roles
    public ResponseEntity<RoleResponseRest> buscarRoles(){
        return service.buscarRoles();
    }

    @GetMapping("/roles/{id}") //ruta de la api rest http://localhost:8083/api/v1/roles/1
    public ResponseEntity<RoleResponseRest> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }
    @PostMapping("/roles") //ruta de la api rest http://localhost:8083/api/v1/roles
    public ResponseEntity<RoleResponseRest> crear(@RequestBody Role request){
        return service.crear(request);
    }

    @PutMapping("/roles/{id}") //ruta de la api rest http://localhost:8083/api/v1/roles/1
    public ResponseEntity<RoleResponseRest> actualizar(@RequestBody Role request, @PathVariable Long id){
        return service.actualizar(request, id);
    }

    @DeleteMapping("/roles/{id}") //ruta de la api rest http://localhost:8083/api/v1/roles/1
    public ResponseEntity<RoleResponseRest> eliminar(@PathVariable Long id){
        return service.eliminar(id);
    }
}
