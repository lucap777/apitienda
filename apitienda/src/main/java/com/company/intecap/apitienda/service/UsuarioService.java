package com.company.intecap.apitienda.service;

import com.company.intecap.apitienda.dao.IUsuarioDao;
import com.company.intecap.apitienda.model.Usuario;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service // @Service es un bean de Spring que se utiliza para marcar la clase de servicio. @Service se utiliza para anotar clases que realizan algún tipo de función de servicio, a menudo para un servicio remoto, transaccional o de procesamiento de datos.
public class UsuarioService implements UserDetailsService {
    //UserDetailsService es una interfaz de Spring Security que se utiliza para recuperar los detalles del usuario conectado actualmente, dada su identificación de usuario. Implementa un método llamado loadUserByUsername () que se utiliza para recuperar los detalles del usuario conectado actualmente.
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UsuarioService.class);

    @Autowired // @Autowired se utiliza para inyectar dependencias en el bean de Spring.
    private IUsuarioDao usuarioDao; // IUsuarioDao es una interfaz que extiende de CrudRepository, que es una interfaz de Spring Data JPA que proporciona métodos CRUD heredados de CrudRepository. IUsuarioDao proporciona un método personalizado para buscar un usuario por nombre de usuario.
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = this.usuarioDao.findByNombreUsuario(username); // findByNombreUsuario es un método personalizado de IUsuarioDao que busca un usuario por nombre de usuario.

        if (usuario == null) {
            log.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el usuario '" + username + "' en el sistema!");
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                // getRoles() es un método de la clase Usuario que devuelve una lista de roles.
                //GrantedAuthority es una interfaz que representa una autoridad concedida a un Authenticationobjeto. Las autoridades se utilizan para representar tanto los roles como los permisos.
                .stream()// stream() es un método de la interfaz Collection que devuelve un flujo secuencial de elementos de esta colección.
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority -> log.info("Role: " + authority.getAuthority())) // peek() es un método de la interfaz Stream que devuelve un flujo que consiste en los elementos de este flujo, además de realizar la acción especificada en el parámetro.
                .collect(Collectors.toList()); // devolver un collectors.toList() collect() es un método de la interfaz Stream que devuelve un Collector que describe cómo se debe realizar la reducción de secuencias, por ejemplo, la colección en la que se deben almacenar los datos de entrada.
        return new User(usuario.getNombreUsuario(), usuario.getPassword(), usuario.getHabilitado(), true, true, true, authorities);
    }
}
