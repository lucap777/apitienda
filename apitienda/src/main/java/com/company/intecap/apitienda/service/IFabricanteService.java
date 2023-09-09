package com.company.intecap.apitienda.service;

import com.company.intecap.apitienda.model.Fabricante;
import com.company.intecap.apitienda.response.FabricanteResponseRest;
import org.springframework.http.ResponseEntity;

public interface IFabricanteService {

   // declaramos los servicios que vamos a utilizar
    // metodo que devuelve una lista de fabricantes
    public ResponseEntity<FabricanteResponseRest> buscarFabricantes();
    // metodo que devuelve una fabricante por id
    public ResponseEntity<FabricanteResponseRest> buscarPorId(Long id);
    // metodo que crea una fabricante
    public ResponseEntity<FabricanteResponseRest> crear(Fabricante fabricante);
    // metodo que actualiza una fabricante
    public ResponseEntity<FabricanteResponseRest> actualizar(Fabricante fabricante, Long id);

    // metodo que elimina una fabricante
    public ResponseEntity<FabricanteResponseRest> eliminar(Long id);

}
