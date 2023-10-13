package com.company.intecap.apitienda.dao;

import com.company.intecap.apitienda.model.Role;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.Optional;


public interface IRoleDao extends CrudRepository<Role, Long> {
    Optional<Role> findById(SingularAttribute<AbstractPersistable, Serializable> id);
}


