package ru.gb.mark.webstore.dto;

import lombok.Builder;
import lombok.Data;
import ru.gb.mark.webstore.entity.User;

import java.math.BigDecimal;


@Data
@Builder
public class OrderDTO {

    private Long id;
    private String address;
    private OrderStatus status;
    private BigDecimal totalSum;
    private String username;
    private User user;

}
