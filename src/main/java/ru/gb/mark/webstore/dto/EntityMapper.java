package ru.gb.mark.webstore.dto;

//Data Mapper
public interface EntityMapper<T, E> {

    T mapDtoToEntity(E dto);
    E mapEntityToDto(T entity);

}
