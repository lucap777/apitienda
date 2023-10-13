package com.company.intecap.apitienda.controllers;
import com.company.intecap.apitienda.model.Usuario;
import com.company.intecap.apitienda.response.UsuarioResponseRest;
import com.company.intecap.apitienda.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")  //prefijo de la ruta de la api rest  http://localhost:8080/api/v1
public class UsuarioRestController {

    //implementar los metodos de la clase UsuarioServiceImpl
    @Autowired //inyeccion de dependencias
    private IUsuarioService service;

    @GetMapping("/usuarios") //ruta de la api rest http://localhost:8083/api/v1/usuarios
    public ResponseEntity<UsuarioResponseRest> buscarUsuarios(){
        return service.buscarUsuarios();
    }

    @GetMapping("/usuarios/{id}") //ruta de la api rest http://localhost:8083/api/v1/usuarios/1
    public ResponseEntity<UsuarioResponseRest> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }
    @PostMapping("/usuarios") //ruta de la api rest http://localhost:8083/api/v1/usuarios
    public ResponseEntity<UsuarioResponseRest> crear(@RequestBody Usuario request){
        return service.crear(request);
    }

    @PutMapping("/usuarios/{id}") //ruta de la api rest http://localhost:8083/api/v1/usuarios/1
    public ResponseEntity<UsuarioResponseRest> actualizar(@RequestBody Usuario request, @PathVariable Long id){
        return service.actualizar(request, id);
    }

    @DeleteMapping("/usuarios/{id}") //ruta de la api rest http://localhost:8083/api/v1/usuarios/1
    public ResponseEntity<UsuarioResponseRest> eliminar(@PathVariable Long id){
        return service.eliminar(id);
    }
}
