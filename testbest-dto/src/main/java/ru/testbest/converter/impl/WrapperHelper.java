package ru.testbest.converter.impl;

import ru.testbest.dto.Wrapper;
import ru.testbest.dto.test.ChapterWrapDto;

public interface WrapperHelper<E> {

  Wrapper wrapDto(E entity);

  E unwrapDto(Wrapper dto);

}
