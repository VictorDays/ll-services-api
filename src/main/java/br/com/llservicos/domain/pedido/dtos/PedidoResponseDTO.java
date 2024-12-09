package br.com.llservicos.domain.pedido.dtos;

import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.Status;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;

public record PedidoResponseDTO(
        Long id,
        Double valorTotal,
        Status status,
        String servicoNome,
        PessoaResponseDTO pessoa

) {

    public static PedidoResponseDTO valueOf(PedidoModel pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getServico().getNome(),
                PessoaResponseDTO.valueOf(pedido.getPessoa())
        );
    }
}
