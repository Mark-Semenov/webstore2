package ru.gb.mark.webstore.dto;

import lombok.Builder;
import lombok.Data;
import ru.gb.mark.webstore.entity.Cart;
import ru.gb.mark.webstore.entity.Role;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
public class UserDTO {

    private final String ROLE_PREFIX = "ROLE_";

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate date;
    private String login;
    private String password;
    private String matchingPassword;
    private String email;
    private String role;
    private String phone;
    private Cart cart;
    private List<Role> roles;


    public String buildRole() {
        return ROLE_PREFIX + role;
    }

}
