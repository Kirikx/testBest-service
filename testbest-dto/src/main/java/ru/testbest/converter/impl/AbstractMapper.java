package ru.testbest.converter.impl;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.BaseDTO;
import ru.testbest.persistence.BaseEntity;

@Slf4j
public abstract class AbstractMapper<E extends BaseEntity, D extends BaseDTO> implements
    ConverterTest<E, D> {

  @Autowired
  ModelMapper mapper;

  private Class<E> entityClass;
  private Class<D> dtoClass;

  protected AbstractMapper(Class<E> entityClass, Class<D> dtoClass) {
    this.entityClass = entityClass;
    this.dtoClass = dtoClass;
  }

  @Override
  public E convertToEntity(D dto) {
    return Objects.isNull(dto)
        ? null
        : mapper.map(dto, entityClass);
  }

  @Override
  public D convertToDto(E entity) {
    return Objects.isNull(entity)
        ? null
        : mapper.map(entity, dtoClass);
  }

  protected Converter<E, D> toDtoConverter() {
    return context -> {
      E source = context.getSource();
      D destination = context.getDestination();
      mapSpecificFields(source, destination);
      return context.getDestination();
    };
  }

  protected Converter<D, E> toEntityConverter() {
    return context -> {
      D source = context.getSource();
      E destination = context.getDestination();
      mapSpecificFields(source, destination);
      return context.getDestination();
    };
  }

  protected void mapSpecificFields(E source, D destination) {
  }

  protected void mapSpecificFields(D source, E destination) {
  }
}
