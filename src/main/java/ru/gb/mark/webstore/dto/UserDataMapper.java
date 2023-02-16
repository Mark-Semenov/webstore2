package ru.gb.mark.webstore.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.entity.User;

@Log4j2
@Data
@Component //Data Mapper
public class UserDataMapper implements EntityMapper<User, UserDTO> {

    @Override
    public User mapDtoToEntity(UserDTO dto) {

        return UserWrapper
                .builder()
                .setFirstname(dto.getFirstname())
                .setLastname(dto.getLastname())
                .setEmail(dto.getEmail())
                .setPassword(dto.getPassword())
                .setPhone(dto.getPhone())
                .setDate(dto.getDate())
                .setCart(dto.getCart())
                .setRoles(dto.getRoles())
                .build();
    }

    @Override
    public UserDTO mapEntityToDto(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .date(user.getDate())
                .phone(user.getPhone())
                .build();
    }
}

