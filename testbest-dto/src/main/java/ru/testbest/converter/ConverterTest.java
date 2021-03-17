package ru.testbest.converter;

public interface ConverterTest<E, D> {

  D convertToDto(E entity);

  E convertToEntity(D dto);
}
