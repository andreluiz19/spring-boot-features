package io.github.andreluiz19.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO { // DTO -> Data Transfer Object

    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;

}
