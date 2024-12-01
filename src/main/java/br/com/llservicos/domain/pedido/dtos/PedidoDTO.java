package br.com.llservicos.domain.pedido.dtos;

import jakarta.validation.constraints.NotBlank;

public record PedidoDTO(
        @NotBlank(message = "O campo valor não pode ser nulo.") Double valorTotal,
        @NotBlank(message = "O campo status não pode ser nulo.") String status
        ) {
}
