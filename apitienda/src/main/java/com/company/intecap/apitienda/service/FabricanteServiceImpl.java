package com.company.intecap.apitienda.service;

import com.company.intecap.apitienda.dao.IFabricanteDao;
import com.company.intecap.apitienda.model.Fabricante;
import com.company.intecap.apitienda.response.FabricanteResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.intecap.apitienda.dao.IFabricanteDao.*;

@Service //@Service es una anotacion que se utiliza para marcar una clase como un bean de servicio
public class FabricanteServiceImpl implements IFabricanteService {
    //errores capturar en la consola
    private static final Logger log = LoggerFactory.getLogger(FabricanteServiceImpl.class);
    //el log es para mostrar en consola los errores

    @Autowired //inyeccion de dependencias
    private IFabricanteDao fabricanteDao;


    @Override
    @Transactional(readOnly = true)
    //para que sea de solo lectura (package   import org.springframework.transaction.annotation.Transactional;)
    public ResponseEntity<FabricanteResponseRest> buscarFabricantes() {
        //aqui se implementa la logica de negocio

        log.info("inicio metodo buscarFabricantes()");
        FabricanteResponseRest response = new FabricanteResponseRest(); //instancia de la clase FabricanteResponseRest

        try {
            List<Fabricante> fabricante = (List<Fabricante>) fabricanteDao.findAll();; //busca todas las fabricantes findAll(): devuelve un iterable de fabricantes (List<Fabricante>)
            response.getFabricanteResponse().setFabricantes(fabricante);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "500", "Error al consultar fabricantes");
            log.error("Error al consultar fabricantes {}", e.getMessage());
            e.getStackTrace(); //para mostrar el error en consola
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<FabricanteResponseRest> buscarPorId(Long id) {
        log.info("inicio metodo buscarPorId()");
        FabricanteResponseRest response = new FabricanteResponseRest(); //instancia de la clase FabricanteResponseRest
        List<Fabricante> list = new ArrayList<>(); //lista de fabricantes

        try {
            Optional<Fabricante> fabricante = fabricanteDao.findById(id); //busca un fabricante por id

            if (fabricante.isPresent()) { //si el fabricante  existe
                list.add(fabricante.get()); //agrega fabricante a la lista
                response.getFabricanteResponse().setFabricantes(list); //setea la lista de fabricantes a la clase FabricanteResponseRest
            } else {
                log.error("Error al consultar fabricante {}", id);
                response.setMetadata("Respuesta no ok", "404", "Fabricante no encontrada");
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
            }
        } catch (Exception e) {
            log.error("Error al consultar fabricante {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al consultar fabricante");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }

        response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
    public ResponseEntity<FabricanteResponseRest> crear(Fabricante fabricante) {
        log.info("inicio metodo crear() Fabricante");

        FabricanteResponseRest response = new FabricanteResponseRest(); //instancia de la clase FabricanteResponseRest
        List<Fabricante> list = new ArrayList<>(); //lista de fabricantes

        try {

            Fabricante fabricanteGuardada = fabricanteDao.save (fabricante); //guarda la fabricante en la base de datos


            if (fabricanteGuardada != null) { //si la fabricante se guardo correctamente
                list.add(fabricanteGuardada); //agrega la fabricante a la lista
                response.getFabricanteResponse().setFabricantes(list); //setea la lista de fabricantes a la clase FabricanteResponseRest
            } else {
                log.error("Error al crear fabricante {}", fabricante.toString());
                response.setMetadata("Respuesta no ok", "400", "Fabricante no creada");
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
            }
        } catch (Exception e) {
            log.error("Error al crear fabricante {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al crear fabricante");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }
        response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
    public ResponseEntity<FabricanteResponseRest> actualizar(Fabricante fabricante, Long id) {
        log.info("inicio metodo actualizar() Fabricante");

        FabricanteResponseRest response = new FabricanteResponseRest(); //instancia de la clase FabricanteResponseRest
        List<Fabricante> list = new ArrayList<>(); //lista de fabricantes

        try {
            Optional<Fabricante> fabricanteBuscada = fabricanteDao.findById(id); //busca una fabricante por id
            //Optional: porque puede que no exista la fabricante

            if (fabricanteBuscada.isPresent()) { // si encontro la fabricante
                //Setear todos los campos que viene en el objeto fabricante que llega por parametro(id, nombre, descripcion)
                fabricanteBuscada.get().setNombre(fabricante.getNombre()); //setea el nombre de la fabricante


                Fabricante fabricanteActualizada = fabricanteDao.save(fabricanteBuscada.get()); //primero se busca la fabricante por id y luego se guarda en la base de datos
                //Fabricante: es la fabricante que se va a guardar en la base de datos

                if (fabricanteActualizada != null) { //si fabricanteActualizada es diferente de null es porque se actualizo correctamente la fabricante
                    list.add(fabricanteActualizada); //agrega la fabricante a la lista
                    response.getFabricanteResponse().setFabricantes(list); //setea la lista de fabricantes a la clase FabricanteResponseRest
                } else {
                    log.error("Error al actualizar fabricante {}", fabricante.toString());
                    response.setMetadata("Respuesta no ok", "400", "Fabricante no actualizada");
                    return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400

                }
            } else {
                log.error("Error al actualizar fabricante {}", fabricante.toString());
                response.setMetadata("Respuesta no ok", "400", "Fabricante no actualizada");
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
            }
        } catch (Exception e) {
            log.error("Error al actualizar fabricante {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al actualizar fabricante");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }
        response.setMetadata("Respuesta ok", "200", "Fabricante Actualizada");
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    public ResponseEntity<FabricanteResponseRest> eliminar(Long id) {
        log.info("inicio metodo actualizar() Fabricante");
        FabricanteResponseRest response = new FabricanteResponseRest(); //instancia de la clase FabricanteResponseRest

        try {
            Optional<Fabricante> fabricante = fabricanteDao.findById(id); //busca una fabricante por id

            if (fabricante.isPresent()) { //si la fabricante existe
                fabricanteDao.delete(fabricante.get()); //elimina la fabricante por id
            } else {
                log.error("Error al eliminar fabricante {}", id);
                response.setMetadata("Respuesta no ok", "400", "Fabricante no eliminada");
                return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
            }

        } catch (Exception e) {
            log.error("Error al Eliminar fabricante {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al Eliminar fabricante");
            return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500

        }
        response.setMetadata("Respuesta ok", "200", "Fabricante Eliminada");
        return new ResponseEntity<FabricanteResponseRest>(response, HttpStatus.OK); //respuesta exitosa

    }


}
