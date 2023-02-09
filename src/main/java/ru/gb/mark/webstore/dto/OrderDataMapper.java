package ru.gb.mark.webstore.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Category;
import ru.gb.mark.webstore.entity.Order;

@Log4j2
@Data
@Component //Data Mapper
public class OrderDataMapper implements EntityMapper<Order, OrderDTO> {

    @Override
    public Order mapDtoToEntity(OrderDTO dto) {

        Order order = new Order();
        order.setStatus(dto.getStatus());
        order.setTotalSum(dto.getTotalSum());
        order.setAddress(dto.getAddress());
        if (dto.getId() != null) {
            order.setId(dto.getId());
        }

        return order;

    }

    @Override
    public OrderDTO mapEntityToDto(Order product) {
        return OrderDTO.builder()
                .address(product.getAddress())
                .status(product.getStatus())
                .totalSum(product.getTotalSum())
                .build();
    }
}

