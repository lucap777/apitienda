package com.company.intecap.apitienda.service;

import com.company.intecap.apitienda.model.Articulo;
import com.company.intecap.apitienda.model.Fabricante;
import com.company.intecap.apitienda.response.ArticuloResponseRest;
import com.company.intecap.apitienda.response.FabricanteResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IArticuloService {

    @Transactional(readOnly = true)
    //para que sea de solo lectura (package   import org.springframework.transaction.annotation.Transactional;)
    ResponseEntity<ArticuloResponseRest> buscaArticulos();

    //mostrar todos los articulos
    public ResponseEntity<ArticuloResponseRest> buscarArticulos();

    public ResponseEntity<ArticuloResponseRest> buscarPorId(Long id);
    // metodo que crea una fabricante
    public ResponseEntity<ArticuloResponseRest> crear(Articulo articulo);
    // metodo que actualiza una fabricante
    public ResponseEntity<ArticuloResponseRest> actualizar(Articulo articulo, Long id);

    // metodo que elimina una fabricante
    public ResponseEntity<ArticuloResponseRest> eliminar(Long id);

}
