package io.github.andreluiz19.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO { // DTO -> Data Transfer Object

    private Integer produto;

    private Integer quantidade;

}
