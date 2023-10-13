package com.company.intecap.apitienda.service;
import com.company.intecap.apitienda.dao.IUsuarioDao;
import com.company.intecap.apitienda.model.Usuario;
import com.company.intecap.apitienda.response.UsuarioResponseRest;
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
public class UsuarioServiceImpl implements IUsuarioService {
    //errores capturar en la consola
    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    //el log es para mostrar en consola los errores

    @Autowired //inyeccion de dependencias
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    //para que sea de solo lectura (package   import org.springframework.transaction.annotation.Transactional;)
    public ResponseEntity<UsuarioResponseRest> buscarUsuarios() {
        //aqui se implementa la logica de negocio

        log.info("inicio metodo buscarUsuarios()");
        UsuarioResponseRest response = new UsuarioResponseRest(); //instancia de la clase UsuarioResponseRest

        try {
            List<Usuario> usuario = (List<Usuario>) usuarioDao.findAll(); //busca todas las usuarios findAll(): devuelve un iterable de usuarios (List<Usuario>)
            response.getUsuarioResponse().setUsuarios(usuario);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "500", "Error al consultar usuarios");
            log.error("Error al consultar usuarios {}", e.getMessage());
            e.getStackTrace(); //para mostrar el error en consola
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioResponseRest> buscarPorId(Long id) {
        log.info("inicio metodo buscarPorId()");
        UsuarioResponseRest response = new UsuarioResponseRest(); //instancia de la clase UsuarioResponseRest
        List<Usuario> list = new ArrayList<>(); //lista de usuarios

        try {
            Optional<Usuario> usuario = usuarioDao.findById(id); //busca una usuario por id

            if (usuario.isPresent()) { //si la usuario existe
                list.add(usuario.get()); //agrega la usuario a la lista
                response.getUsuarioResponse().setUsuarios(list); //setea la lista de usuarios a la clase UsuarioResponseRest
            } else {
                log.error("Error al consultar usuario {}", id);
                response.setMetadata("Respuesta no ok", "404", "Usuario no encontrada");
                return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
            }
        } catch (Exception e) {
            log.error("Error al consultar usuario {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al consultar usuario");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }

        response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
    public ResponseEntity<UsuarioResponseRest> crear(Usuario usuario) {
        log.info("inicio metodo crear() Usuario");

        UsuarioResponseRest response = new UsuarioResponseRest(); //instancia de la clase UsuarioResponseRest
        List<Usuario> list = new ArrayList<>(); //lista de usuarios

        try {

            Usuario usuarioGuardada = usuarioDao.save(usuario); //guarda la usuario en la base de datos


            if (usuarioGuardada != null) { //si la usuario se guardo correctamente
                list.add(usuarioGuardada); //agrega la usuario a la lista
                response.getUsuarioResponse().setUsuarios(list); //setea la lista de usuarios a la clase UsuarioResponseRest
            } else {
                log.error("Error al crear usuario {}", usuario.toString());
                response.setMetadata("Respuesta no ok", "400", "Usuario no creada");
                return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
            }
        } catch (Exception e) {
            log.error("Error al crear usuario {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al crear usuario");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }
        response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
    public ResponseEntity<UsuarioResponseRest> actualizar(Usuario usuario, Long id) {
        log.info("inicio metodo actualizar() Usuario");

        UsuarioResponseRest response = new UsuarioResponseRest(); //instancia de la clase UsuarioResponseRest
        List<Usuario> list = new ArrayList<>(); //lista de usuarios

        try {
            Optional<Usuario> usuarioBuscada = usuarioDao.findById(id); //busca una usuario por id
            //Optional: porque puede que no exista la usuario

            if (usuarioBuscada.isPresent()) { // si encontro la usuario
                //Setear todos los campos que viene en el objeto usuario que llega por parametro(id, nombre, precio)
                usuarioBuscada.get().setNombreUsuario(usuario.getNombreUsuario()); //setea el nombre de la usuario

                Usuario usuarioActualizada = usuarioDao.save(usuarioBuscada.get()); //primero se busca la usuario por id y luego se guarda en la base de datos
                //Usuario: es la usuario que se va a guardar en la base de datos

                if (usuarioActualizada != null) { //si usuarioActualizada es diferente de null es porque se actualizo correctamente la usuario
                    list.add(usuarioActualizada); //agrega la usuario a la lista
                    response.getUsuarioResponse().setUsuarios(list); //setea la lista de usuarios a la clase UsuarioResponseRest
                } else {
                    log.error("Error al actualizar usuario {}", usuario.toString());
                    response.setMetadata("Respuesta no ok", "400", "Usuario no actualizada");
                    return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400

                }
            } else {
                log.error("Error al actualizar usuario {}", usuario.toString());
                response.setMetadata("Respuesta no ok", "400", "Usuario no actualizada");
                return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
            }
        } catch (Exception e) {
            log.error("Error al actualizar usuario {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al actualizar usuario");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }
        response.setMetadata("Respuesta ok", "200", "Usuario Actualizada");
        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK); //respuesta exitosa
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioResponseRest> eliminar(Long id) {
        log.info("inicio metodo actualizar() Usuario");
        UsuarioResponseRest response = new UsuarioResponseRest(); //instancia de la clase UsuarioResponseRest

        try {
            Optional<Usuario> usuario = usuarioDao.findById(id); //busca una usuario por id

            if (usuario.isPresent()) { //si la usuario existe
                usuarioDao.delete(usuario.get()); //elimina la usuario por id
            } else {
                log.error("Error al eliminar usuario {}", id);
                response.setMetadata("Respuesta no ok", "400", "Usuario no eliminada");
                return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
            }

        } catch (Exception e) {
            log.error("Error al Eliminar usuario {}", e.getMessage());
            response.setMetadata("Respuesta no ok", "500", "Error al Eliminar usuario");
            return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500

        }
        response.setMetadata("Respuesta ok", "200", "Usuario Eliminada");
        return new ResponseEntity<UsuarioResponseRest>(response, HttpStatus.OK); //respuesta exitosa

    }


}


