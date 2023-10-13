package com.company.intecap.apitienda.controllers;

import com.company.intecap.apitienda.model.Fabricante;
import com.company.intecap.apitienda.response.FabricanteResponseRest;
import com.company.intecap.apitienda.service.IFabricanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins =  {"http://localhost:3001","http://localhost:3000","http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")  //prefijo de la ruta de la api rest  http://localhost:8080/api/v1
public class FabricanteRestController {

    //implementar los metodos de la clase FabricanteServiceImpl
    @Autowired //inyeccion de dependencias
    private IFabricanteService service;

    @GetMapping("/fabricantes") //ruta de la api rest http://localhost:8083/api/v1/fabricantes
    public ResponseEntity<FabricanteResponseRest> buscarFabricantes(){
        return service.buscarFabricantes();
    }

    @GetMapping("/fabricantes/{id}") //ruta de la api rest http://localhost:8083/api/v1/fabricantes/1
    public ResponseEntity<FabricanteResponseRest> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }
    @PostMapping("/fabricantes") //ruta de la api rest http://localhost:8083/api/v1/fabricantes
    public ResponseEntity<FabricanteResponseRest> crear(@RequestBody Fabricante request){
        return service.crear(request);
    }

    @PutMapping("/fabricantes/{id}") //ruta de la api rest http://localhost:8083/api/v1/fabricantes/1
    public ResponseEntity<FabricanteResponseRest> actualizar(@RequestBody Fabricante request, @PathVariable Long id){
        return service.actualizar(request, id);
    }

    @DeleteMapping("/fabricantes/{id}") //ruta de la api rest http://localhost:8083/api/v1/fabricantes/1
    public ResponseEntity<FabricanteResponseRest> eliminar(@PathVariable Long id){
        return service.eliminar(id);
    }
}
