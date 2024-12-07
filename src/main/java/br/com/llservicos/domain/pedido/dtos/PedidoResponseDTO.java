package br.com.llservicos.domain.pedido.dtos;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.Status;

public record PedidoResponseDTO(
        Double valorTotal,
        Status status,
        String servicoNome // Nome do serviço associado ao pedido
) {

    public static PedidoResponseDTO valueOf(PedidoModel pedido) {
        return new PedidoResponseDTO(
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getServico().getNome() // Recuperando o nome do serviço
        );
    }
}
