package com.company.intecap.apitienda.dao;

import com.company.intecap.apitienda.model.Articulo;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.Optional;


public interface IArticuloDao extends CrudRepository<Articulo, Long> {
    Optional<Articulo> findById(SingularAttribute<AbstractPersistable, Serializable> id);
}
