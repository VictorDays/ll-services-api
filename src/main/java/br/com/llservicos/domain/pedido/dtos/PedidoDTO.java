package br.com.llservicos.domain.pedido.dtos;

import br.com.llservicos.domain.pessoa.dtos.PessoaDTO;
import br.com.llservicos.domain.usuario.dtos.UsuarioDTO;
import jakarta.validation.constraints.NotBlank;

public record PedidoDTO(
        @NotBlank(message = "O campo pessoa não pode ser nulo.") PessoaDTO pessoa,
        @NotBlank(message = "O campo valor não pode ser nulo.") Double valorTotal,
        @NotBlank(message = "O campo status não pode ser nulo.") String status


        

        ) {
}
