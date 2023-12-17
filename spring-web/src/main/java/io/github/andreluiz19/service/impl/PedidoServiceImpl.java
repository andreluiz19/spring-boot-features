package io.github.andreluiz19.service.impl;

import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.entity.ItemPedido;
import io.github.andreluiz19.domain.entity.Pedido;
import io.github.andreluiz19.domain.entity.Produto;
import io.github.andreluiz19.domain.enums.StatusPedido;
import io.github.andreluiz19.domain.repository.ClienteRepository;
import io.github.andreluiz19.domain.repository.ItemPedidoRepository;
import io.github.andreluiz19.domain.repository.PedidoRepository;
import io.github.andreluiz19.domain.repository.ProdutoRepository;
import io.github.andreluiz19.exception.PedidoNotFoundException;
import io.github.andreluiz19.exception.RegraNegocioException;
import io.github.andreluiz19.rest.dto.ItemPedidoDTO;
import io.github.andreluiz19.rest.dto.PedidoDTO;
import io.github.andreluiz19.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.andreluiz19.domain.enums.StatusPedido.*;

@Service
@RequiredArgsConstructor // Gera um construtor para todos os atributos obrigatórios. Atributos Obrigatórios = final; Para injeção de dependências
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional // Faz commit de tudo ou faz rollback
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() ->
                    new RegraNegocioException("Código de cliente inválido!")
                );

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, pedidoDTO.getItems());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obeterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, StatusPedido status) {
        pedidoRepository
                .findById(id)
                .map(p -> {
                    p.setStatus(status);
                    return pedidoRepository.save(p);
                })
                .orElseThrow(() ->
                        new PedidoNotFoundException()
                );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if(items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items!");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(() ->
                                    new RegraNegocioException("Código de produto inválido: " + idProduto + "!")
                            );

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());

    }


}
