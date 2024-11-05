package br.com.llservicos.domain.pedido.dtos;



import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.Status;
import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.domain.servico.ServicoModel;


public record PedidoResponseDTO(
        Long id,
        PessoaResponseDTO pessoa,
        ServicoModel servico,
        Double valorTotal,
        Status status
        
) {

    public PedidoResponseDTO(Long id, PessoaModel pessoa, ServicoModel servico, Double valorTotal, Status status) {
        this(id, PessoaResponseDTO.valueOf(pessoa), servico, valorTotal, status);
    }
   

    public static PedidoResponseDTO valueOf(PedidoModel pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido != null ? PessoaResponseDTO.valueOf(pedido.getPessoa()) : null,
                pedido.getServico(),
                pedido.getValorTotal(),
                pedido.getStatus()
        );
    }
}


