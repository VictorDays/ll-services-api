package br.com.llservicos.domain.pedido.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(
        @NotBlank(message = "O campo valor não pode ser nulo.") Double valorTotal,
        @NotBlank(message = "O campo status não pode ser nulo.") String status,
        @NotNull(message = "O campo servicoId não pode ser nulo.") Long servicoId,
        Long pessoaFisica,
        Long pessoaJuridica
        ) {
}
