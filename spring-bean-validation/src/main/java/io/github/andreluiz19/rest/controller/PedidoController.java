package io.github.andreluiz19.rest.controller;

import io.github.andreluiz19.domain.entity.ItemPedido;
import io.github.andreluiz19.domain.entity.Pedido;
import io.github.andreluiz19.domain.enums.StatusPedido;
import io.github.andreluiz19.exception.RegraNegocioException;
import io.github.andreluiz19.rest.dto.InfoItemPedidoDTO;
import io.github.andreluiz19.rest.dto.InfoPedidoDTO;
import io.github.andreluiz19.rest.dto.PedidoDTO;
import io.github.andreluiz19.rest.dto.UpdateStatusPedidoDTO;
import io.github.andreluiz19.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InfoPedidoDTO getById(@PathVariable("id") Integer id) {
        return pedidoService
                .obeterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado!"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable("id") Integer id,
                             @RequestBody UpdateStatusPedidoDTO updateStatusPedidoDTO) {
        String novoStatus = updateStatusPedidoDTO.getNovoStatus();
        try {
            StatusPedido status = StatusPedido.valueOf(novoStatus); // valueOf pega uma string e vê se ela corresponde a algum valor dentro do ENUM
            pedidoService.updateStatus(id, status);
        } catch (IllegalArgumentException ex) {
            throw new RegraNegocioException("Status do pedido inválido!");
        }

    }

    private InfoPedidoDTO converter(Pedido pedido) {
        return InfoPedidoDTO
                    .builder()
                    .codigoPedido(pedido.getId())
                    .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .cpf(pedido.getCliente().getCpf())
                    .nomeCliente(pedido.getCliente().getNome())
                    .total(pedido.getTotal())
                    .status(pedido.getStatus().name())
                    .items(converter(pedido.getItens()))
                    .build();
    }

    private List<InfoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens
                    .stream()
                    .map(item -> InfoItemPedidoDTO
                            .builder()
                            .descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco())
                            .quantidade(item.getQuantidade())
                            .build()
                    ).collect(Collectors.toList());
    }
}
