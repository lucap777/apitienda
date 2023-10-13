package com.company.intecap.apitienda.service;

import com.company.intecap.apitienda.dao.IRoleDao;
import com.company.intecap.apitienda.model.Role;
import com.company.intecap.apitienda.response.RoleResponseRest;
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
    public class RoleServiceImpl implements IRoleService {
        //errores capturar en la consola
        private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
        //el log es para mostrar en consola los errores

        @Autowired //inyeccion de dependencias
        private IRoleDao roleDao;

        @Override
        @Transactional(readOnly = true)
        //para que sea de solo lectura (package   import org.springframework.transaction.annotation.Transactional;)
        public ResponseEntity<RoleResponseRest> buscarRoles() {
            //aqui se implementa la logica de negocio

            log.info("inicio metodo buscarRoles()");
            RoleResponseRest response = new RoleResponseRest(); //instancia de la clase RoleResponseRest

            try {
                List<Role> role = (List<Role>) roleDao.findAll(); //busca todas las roles findAll(): devuelve un iterable de roles (List<Role>)
                response.getRoleResponse().setRoles(role);
                response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
            } catch (Exception e) {
                response.setMetadata("Respuesta no ok", "500", "Error al consultar roles");
                log.error("Error al consultar roles {}", e.getMessage());
                e.getStackTrace(); //para mostrar el error en consola
                return new ResponseEntity<RoleResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<RoleResponseRest>(response, HttpStatus.OK);
        }

        @Override
        @Transactional(readOnly = true)
        public ResponseEntity<RoleResponseRest> buscarPorId(Long id) {
            log.info("inicio metodo buscarPorId()");
            RoleResponseRest response = new RoleResponseRest(); //instancia de la clase RoleResponseRest
            List<Role> list = new ArrayList<>(); //lista de roles

            try {
                Optional<Role> role = roleDao.findById(id); //busca una role por id

                if (role.isPresent()) { //si la role existe
                    list.add(role.get()); //agrega la role a la lista
                    response.getRoleResponse().setRoles(list); //setea la lista de roles a la clase RoleResponseRest
                } else {
                    log.error("Error al consultar role {}", id);
                    response.setMetadata("Respuesta no ok", "404", "Role no encontrada");
                    return new ResponseEntity<RoleResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
                }
            } catch (Exception e) {
                log.error("Error al consultar role {}", e.getMessage());
                response.setMetadata("Respuesta no ok", "500", "Error al consultar role");
                return new ResponseEntity<RoleResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
            }

            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
            return new ResponseEntity<RoleResponseRest>(response, HttpStatus.OK); //respuesta exitosa
        }

        @Override
        @Transactional
        //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
        public ResponseEntity<RoleResponseRest> crear(Role role) {
            log.info("inicio metodo crear() Role");

            RoleResponseRest response = new RoleResponseRest(); //instancia de la clase RoleResponseRest
            List<Role> list = new ArrayList<>(); //lista de roles

            try {

                Role roleGuardada = roleDao.save(role); //guarda la role en la base de datos


                if (roleGuardada != null) { //si la role se guardo correctamente
                    list.add(roleGuardada); //agrega la role a la lista
                    response.getRoleResponse().setRoles(list); //setea la lista de roles a la clase RoleResponseRest
                } else {
                    log.error("Error al crear role {}", role.toString());
                    response.setMetadata("Respuesta no ok", "400", "Role no creada");
                    return new ResponseEntity<RoleResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
                }
            } catch (Exception e) {
                log.error("Error al crear role {}", e.getMessage());
                response.setMetadata("Respuesta no ok", "500", "Error al crear role");
                return new ResponseEntity<RoleResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
            }
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
            return new ResponseEntity<RoleResponseRest>(response, HttpStatus.OK); //respuesta exitosa
        }

        @Override
        @Transactional
        //Rollback: si una de las operaciones falla, se deshacen todas las operaciones -- commit: si todo esta bien, se guardan todas las operaciones
        public ResponseEntity<RoleResponseRest> actualizar(Role role, Long id) {
            log.info("inicio metodo actualizar() Role");

            RoleResponseRest response = new RoleResponseRest(); //instancia de la clase RoleResponseRest
            List<Role> list = new ArrayList<>(); //lista de roles

            try {
                Optional<Role> roleBuscada = roleDao.findById(id); //busca una role por id
                //Optional: porque puede que no exista la role

                if (roleBuscada.isPresent()) { // si encontro la role
                    //Setear todos los campos que viene en el objeto role que llega por parametro(id, nombre, precio)
                    roleBuscada.get().setNombre(role.getNombre()); //setea el nombre de la role

                    Role roleActualizada = roleDao.save(roleBuscada.get()); //primero se busca la role por id y luego se guarda en la base de datos
                    //Role: es la role que se va a guardar en la base de datos

                    if (roleActualizada != null) { //si roleActualizada es diferente de null es porque se actualizo correctamente la role
                        list.add(roleActualizada); //agrega la role a la lista
                        response.getRoleResponse().setRoles(list); //setea la lista de roles a la clase RoleResponseRest
                    } else {
                        log.error("Error al actualizar role {}", role.toString());
                        response.setMetadata("Respuesta no ok", "400", "Role no actualizada");
                        return new ResponseEntity<RoleResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400

                    }
                } else {
                    log.error("Error al actualizar role {}", role.toString());
                    response.setMetadata("Respuesta no ok", "400", "Role no actualizada");
                    return new ResponseEntity<RoleResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
                }
            } catch (Exception e) {
                log.error("Error al actualizar role {}", e.getMessage());
                response.setMetadata("Respuesta no ok", "500", "Error al actualizar role");
                return new ResponseEntity<RoleResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
            }
            response.setMetadata("Respuesta ok", "200", "Role Actualizada");
            return new ResponseEntity<RoleResponseRest>(response, HttpStatus.OK); //respuesta exitosa
        }

        @Override
        @Transactional
        public ResponseEntity<RoleResponseRest> eliminar(Long id) {
            log.info("inicio metodo actualizar() Role");
            RoleResponseRest response = new RoleResponseRest(); //instancia de la clase RoleResponseRest

            try {
                Optional<Role> role = roleDao.findById(id); //busca una role por id

                if (role.isPresent()) { //si la role existe
                    roleDao.delete(role.get()); //elimina la role por id
                } else {
                    log.error("Error al eliminar role {}", id);
                    response.setMetadata("Respuesta no ok", "400", "Role no eliminada");
                    return new ResponseEntity<RoleResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
                }

            } catch (Exception e) {
                log.error("Error al Eliminar role {}", e.getMessage());
                response.setMetadata("Respuesta no ok", "500", "Error al Eliminar role");
                return new ResponseEntity<RoleResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500

            }
            response.setMetadata("Respuesta ok", "200", "Role Eliminada");
            return new ResponseEntity<RoleResponseRest>(response, HttpStatus.OK); //respuesta exitosa

        }


    }


