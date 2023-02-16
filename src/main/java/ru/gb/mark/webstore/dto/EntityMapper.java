package ru.gb.mark.webstore.dto;

public interface EntityMapper<T, E> {

    T mapDtoToEntity(E dto);
    E mapEntityToDto(T entity);

}
