package org.jaws.repository;

import org.jaws.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface JawsRepository<T extends AbstractEntity, ID extends Serializable> extends JpaRepository<T, ID> {
}
