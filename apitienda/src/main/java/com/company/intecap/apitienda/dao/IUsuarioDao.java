package com.company.intecap.apitienda.dao;

import com.company.intecap.apitienda.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    //Optional<Usuario> findById(SingularAttribute<AbstractPersistable, Serializable> id);
    public  Usuario findByNombreUsuario(String nombreUsuario);

    @Query("select u.id from Usuario u where u.nombreUsuario=?1" )
    public Usuario findByNombreUsuario2(String nombreUsuario);

}
