package br.com.llservicos.domain.pedido.dtos;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.Status;

public record PedidoResponseDTO(
        Long id,
        Double valorTotal,
        Status status
) {

    public static PedidoResponseDTO valueOf(PedidoModel pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getStatus());
    }
}
