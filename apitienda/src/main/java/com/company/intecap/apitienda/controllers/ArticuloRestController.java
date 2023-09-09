package com.company.intecap.apitienda.controllers;
import com.company.intecap.apitienda.model.Articulo;
import com.company.intecap.apitienda.response.ArticuloResponseRest;
import com.company.intecap.apitienda.service.IArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1") //prefijo de la ruta de la api rest  http://localhost:8080/api/v1
public class ArticuloRestController {

    @Autowired //inyeccion de dependencias
    private IArticuloService service;

    @GetMapping("/articulos") //ruta de la api rest http://localhost:8080/api/v1/articulos
    public ResponseEntity<ArticuloResponseRest> buscarArticulos(){
        return service.buscarArticulos();
    }
    @GetMapping("/articulos/{id}") //ruta de la api rest http://localhost:8080/api/v1/articulos/1
    public ResponseEntity<ArticuloResponseRest> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }
    @PostMapping("/articulos") //ruta de la api rest http://localhost:8083/api/v1/articulos
    public ResponseEntity<ArticuloResponseRest> crear(@RequestBody Articulo request){
        return service.crear(request);
    }

    @PutMapping("/articulos/{id}") //ruta de la api rest http://localhost:8083/api/v1/articulos/1
    public ResponseEntity<ArticuloResponseRest> actualizar(@RequestBody Articulo request, @PathVariable Long id){
        return service.actualizar(request, id);
    }

    @DeleteMapping("/articulos/{id}") //ruta de la api rest http://localhost:8080/api/v1/articulos/1
    public ResponseEntity<ArticuloResponseRest> eliminar(@PathVariable Long id){
        return service.eliminar(id);
    }
}
