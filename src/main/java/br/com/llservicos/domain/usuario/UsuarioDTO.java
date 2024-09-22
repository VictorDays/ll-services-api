package br.com.llservicos.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank(message = "O campo email não pode ser nulo.") String email,
        @NotBlank(message = "O campo senha não pode ser nulo.") String senha) {
}
