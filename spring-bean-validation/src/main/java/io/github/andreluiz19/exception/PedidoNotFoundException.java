package io.github.andreluiz19.exception;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException() {
        super("Pedido não encontrado!");
    }
}
