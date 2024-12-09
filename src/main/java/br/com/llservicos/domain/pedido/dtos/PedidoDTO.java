package br.com.llservicos.domain.pedido.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(
        @NotBlank(message = "O campo valor não pode ser nulo.") Double valorTotal,
        Long servicoId,
        Long pessoa
) {
}
