package com.company.intecap.apitienda.service;

import com.company.intecap.apitienda.dao.IArticuloDao;
import com.company.intecap.apitienda.model.Articulo;
import com.company.intecap.apitienda.response.ArticuloResponseRest;
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

@Service //@Service es una anotacion que se utiliza para marcar una clase como un bean de servicio
public class ArticuloServiceImpl implements IArticuloService {
    //errores capturar en la consola
    private static final Logger log = LoggerFactory.getLogger(ArticuloServiceImpl.class);
    //el log es para mostrar en consola los errores

    @Autowired //inyeccion de dependencias
    private IArticuloDao articuloDao;


    @Override
    public ResponseEntity<ArticuloResponseRest> buscaArticulos() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    //para que sea de solo lectura (package   import org.springframework.transaction.annotation.Transactional;)
    public ResponseEntity<ArticuloResponseRest> buscarArticulos() {
        //aqui se implementa la logica de negocio

        log.info("inicio metodo buscarArticulos()");
        ArticuloResponseRest response = new ArticuloResponseRest(); //instancia de la clase ArticuloResponseRest

        try {
            List<Articulo> articulo = (List<Articulo>) articuloDao.findAll(); //busca todas las articulos findAll(): devuelve un iterable de articulos (List<Articulo>)
            response.getArticuloResponse().setArticulos(articulo);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "500", "Error al consultar articulos");
            log.error("Error al consultar articulos {}", e.getMessage());
            e.getStackTrace(); //para mostrar el error en consola
            return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ArticuloResponseRest> buscarPorId(Long id) {
        log.info("inicio metodo buscarPorId()");
        ArticuloResponseRest response = new ArticuloResponseRest(); //instancia de la clase ArticuloResponseRest
        List<Articulo> list = new ArrayList<>(); //lista de articulos

        try {
            Optional<Articulo> articulo = articuloDao.findById(id); //busca una articulo por id

            if (articulo.isPresent()) { //si la articulo existe
                list.add(articulo.get()); //agrega la articulo a la lista
                response.getArticuloResponse().setArticulos(list); //setea la lista de articulos a la clase ArticuloResponseRest
            } else {
                log.error("Error al consultar articulo {}", id);
                response.setMetadata("Respuesta no ok", "404", "Articulo no encontrada");
                return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
            }
        } catch (Exception e) {
            log.error("Error al consultar articulo {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al consultar articulo");
            return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }

        response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
    public ResponseEntity<ArticuloResponseRest> crear(Articulo articulo) {
        log.info("inicio metodo crear() Articulo");

        ArticuloResponseRest response = new ArticuloResponseRest(); //instancia de la clase ArticuloResponseRest
        List<Articulo> list = new ArrayList<>(); //lista de articulos

        try {

            Articulo articuloGuardada = articuloDao.save(articulo); //guarda la articulo en la base de datos


            if (articuloGuardada != null) { //si la articulo se guardo correctamente
                list.add(articuloGuardada); //agrega la articulo a la lista
                response.getArticuloResponse().setArticulos(list); //setea la lista de articulos a la clase ArticuloResponseRest
            } else {
                log.error("Error al crear articulo {}", articulo.toString());
                response.setMetadata("Respuesta no ok", "400", "Articulo no creada");
                return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
            }
        } catch (Exception e) {
            log.error("Error al crear articulo {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al crear articulo");
            return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }
        response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
    public ResponseEntity<ArticuloResponseRest> actualizar(Articulo articulo, Long id) {
        log.info("inicio metodo actualizar() Articulo");

        ArticuloResponseRest response = new ArticuloResponseRest(); //instancia de la clase ArticuloResponseRest
        List<Articulo> list = new ArrayList<>(); //lista de articulos

        try {
            Optional<Articulo> articuloBuscada = articuloDao.findById(id); //busca una articulo por id
            //Optional: porque puede que no exista la articulo

            if (articuloBuscada.isPresent()) { // si encontro la articulo
                //Setear todos los campos que viene en el objeto articulo que llega por parametro(id, nombre, precio)
                articuloBuscada.get().setNombre(articulo.getNombre()); //setea el nombre de la articulo
                articuloBuscada.get().setPrecio(articulo.getPrecio()); //setea la precio de la articulo

                Articulo articuloActualizada = articuloDao.save(articuloBuscada.get()); //primero se busca la articulo por id y luego se guarda en la base de datos
                //Articulo: es la articulo que se va a guardar en la base de datos

                if (articuloActualizada != null) { //si articuloActualizada es diferente de null es porque se actualizo correctamente la articulo
                    list.add(articuloActualizada); //agrega la articulo a la lista
                    response.getArticuloResponse().setArticulos(list); //setea la lista de articulos a la clase ArticuloResponseRest
                } else {
                    log.error("Error al actualizar articulo {}", articulo.toString());
                    response.setMetadata("Respuesta no ok", "400", "Articulo no actualizada");
                    return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400

                }
            } else {
                log.error("Error al actualizar articulo {}", articulo.toString());
                response.setMetadata("Respuesta no ok", "400", "Articulo no actualizada");
                return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
            }
        } catch (Exception e) {
            log.error("Error al actualizar articulo {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al actualizar articulo");
            return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }
        response.setMetadata("Respuesta ok", "200", "Articulo Actualizada");
        return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    public ResponseEntity<ArticuloResponseRest> eliminar(Long id) {
        log.info("inicio metodo actualizar() Articulo");
        ArticuloResponseRest response = new ArticuloResponseRest(); //instancia de la clase ArticuloResponseRest

        try {
            Optional<Articulo> articulo = articuloDao.findById(id); //busca una articulo por id

            if (articulo.isPresent()) { //si la articulo existe
                articuloDao.delete(articulo.get()); //elimina la articulo por id
            } else {
                log.error("Error al eliminar articulo {}", id);
                response.setMetadata("Respuesta no ok", "400", "Articulo no eliminada");
                return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
            }

        } catch (Exception e) {
            log.error("Error al Eliminar articulo {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al Eliminar articulo");
            return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500

        }
        response.setMetadata("Respuesta ok", "200", "Articulo Eliminada");
        return new ResponseEntity<ArticuloResponseRest>(response, HttpStatus.OK); //respuesta exitosa

    }


}
