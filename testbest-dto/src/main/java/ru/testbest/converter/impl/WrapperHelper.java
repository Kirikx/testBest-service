package ru.testbest.converter.impl;

import ru.testbest.dto.BaseDTO;

public interface WrapperHelper<E> {

  BaseDTO wrapDto(E entity);

  E unwrapDto(BaseDTO dto);

}
