package br.com.llservicos.domain.pedido.dtos;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.Status;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;

public record PedidoResponseDTO(
        Double valorTotal,
        Status status,
        String servicoNome, // Nome do serviço associado ao pedido
        PessoaResponseDTO pessoa

) {

    public static PedidoResponseDTO valueOf(PedidoModel pedido) {
        return new PedidoResponseDTO(
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getServico().getNome(), // Recuperando o nome do serviço
                PessoaResponseDTO.valueOf(pedido.getPessoa())
        );
    }
}
