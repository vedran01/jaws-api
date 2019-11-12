package org.jaws.core.mapper;

public interface DataMapper<T, U> {

  T toDO(U dto, Object... args);

  U toDTO(T model, Object... args);

}
